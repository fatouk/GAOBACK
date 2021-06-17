/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;

import com.example.gao.utils.Http;
import com.example.gao.utils.ResponseWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author fakaloga
 */
@Service
public class ADService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ADService.class);
    @Autowired

    @Value("${orange.internals.ldap.url}")
    private String ldapUrl;

    private ResponseWrapper getRequest(String url) {
        System.out.println("URL: " + url);
        try {
            ResponseEntity<String> resp = new Http(url).doHttpGet(null);
            if (resp == null) {

                return null;
            }
            String ret = resp.getBody();
            if (ret == null) {
                return null;
            }

            JsonNode retJson = Http.toJsonNode(ret);
            if (retJson == null) {
                return null;
            }
            System.out.println("ret: " + retJson);
            return new ResponseWrapper(null, retJson);

        } catch (HttpClientErrorException e) {

            System.out.println(e.getMessage());
            return ResponseWrapper.error(e.getResponseBodyAsString(), e.getRawStatusCode());
        }
    }

    private ResponseWrapper postRequest(String url, String req) {
        if (!req.contains("password")) {
            System.out.println("URL: " + url + "; " + req);
        } else {
            System.out.println("URL: " + url);
        }
        try {
            ResponseEntity<String> resp = new Http(url).doHttpPost(null, req);
            if (resp == null) {
                return null;
            }
            String ret = resp.getBody();
            if (ret == null) {
                return null;
            }
            JsonNode retJson = Http.toJsonNode(ret);
            if (retJson == null) {
                return null;
            }
            return new ResponseWrapper(null, 200, retJson);

        } catch (HttpClientErrorException e) {

            System.out.println(e.getMessage() + "; " + e.getResponseBodyAsString() + "; " + e.getStatusText());
            //return ResponseWrapper.error(e.getStatusText(), e.getRawStatusCode());
            return ResponseWrapper.error(e.getStatusText(), e.getRawStatusCode(), e.getMessage());
        }
    }

    public ResponseWrapper callHttpsPostRequest(String url, Map<String, String> headers, String parametre) {
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    System.out.println("getAcceptedIssuers =============");
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                        String authType) {
                    System.out.println("checkClientTrusted =============");
                }

                public void checkServerTrusted(X509Certificate[] certs,
                        String authType) {
                    System.out.println("checkServerTrusted =============");
                }
            }}, new SecureRandom());

            X509HostnameVerifier verifier = new AllowAllHostnameVerifier();
            httpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();

            StringEntity params = new StringEntity(parametre);
            HttpPost request = new HttpPost(url);
            request.addHeader("content-type", "application/json; charset=utf-8");
            request.addHeader("Accept", "application/json; charset=utf-8");

            request.setEntity(params);
//            System.out.println("url : " + url);
            HttpResponse response = httpClient.execute(request);

            ResponseEntity<String> re = new ResponseEntity<>(readFromJson(String.class, response.getEntity().getContent(), url), HttpStatus.resolve(response.getStatusLine().getStatusCode()));
            return new ResponseWrapper(null, re.getStatusCodeValue(), Http.toJsonNode(re.getBody()));
            //return re;
        } catch (HttpClientErrorException e) {

            //return ResponseWrapper.error(e.getStatusText(), e.getRawStatusCode());
            return ResponseWrapper.error(e.getStatusText(), e.getRawStatusCode(), e.getMessage());
        } catch (Exception e) {

            return ResponseWrapper.error(e.getMessage(), 400, null);
        }
    }

    private <T> T readFromJson(Class<? extends T> t, InputStream is, String msg) {
        try {
            StringBuilder responseString = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseString.append(inputLine);
            }
            //print result

            Gson gson = new Gson();

            if ("java.lang.String".equalsIgnoreCase(t.getName())) {
                return (T) responseString.toString();
            }

            return gson.fromJson(responseString.toString(), t);

        } catch (IOException | JsonSyntaxException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    public ResponseWrapper users() {
        return getRequest(ldapUrl + "/users");
    }

    public ResponseWrapper usernames() {
        return getRequest(ldapUrl + "/usernames");
    }

    public ResponseWrapper departements() {
        return getRequest(ldapUrl + "/departements");
    }

    public ResponseWrapper findManager(String matricule, int level) {
        return getRequest(ldapUrl + "/findManager?matricule=" + matricule + "&level=" + level);
    }
    public ResponseWrapper getAllUsers(int limit) {
        return getRequest(ldapUrl + "/users?limit=" + limit);
    }

    public ResponseWrapper sites() {
        return getRequest(ldapUrl + "/sites");
    }

    public ResponseWrapper uidlookup(String uid) {
        return getRequest(ldapUrl + "/uidlookup?uid=" + uid);
    }

    public ResponseWrapper getByMatricule(String matricule) {
        return getRequest(ldapUrl + "/getByMatricule?matricule=" + matricule);
    }

    public ResponseWrapper getByLogin(String login) {
        return getRequest(ldapUrl + "/getByLogin?login=" + login);
    }

    public ResponseWrapper filter(@Nullable String nom,
            @Nullable String prenom,
            @Nullable String site,
            @Nullable String direction) {
        String query = (nom == null ? "" : "nom=" + nom) + (prenom == null ? "" : "&prenom=" + prenom) + (site == null ? "" : "&site=" + site) + (direction == null ? "" : "&direction=" + direction);
        return getRequest(ldapUrl + "/filter?" + query);
    }

//    public ResponseWrapper auth(LoginForm loginForm) {
//        //System.out.println(ldapUrl + "/auth"+" ---- "+Http.stringfy(loginForm));
//        return postRequest(ldapUrl + "/auth", Http.stringfy(loginForm));
//    }
    public ResponseWrapper auth(LoginForm loginForm) {
        //System.out.println(ldapUrl + "/auth"+" ---- "+Http.stringfy(loginForm));
        return postRequest(ldapUrl + "/auth", Http.stringfy(loginForm));
    }

    public ResponseWrapper hierarchie(String hierarchie) {
        return getRequest(ldapUrl + "/hierarchie?cn=" + hierarchie);
    }

    public ResponseWrapper find(String filter) {
        return getRequest(ldapUrl + "/fullFilter?searching=" + filter);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginForm {
        private String login;
        private String password;
    }

}
