/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.utils;

/**
 *
 * @author fakaloga
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
public class Http {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Http.class);
   
    private String baseUrl;
    private String basicAuth;
    private RestTemplate restTemplate;
    public Http(String baseUrl) {
        this.baseUrl = baseUrl;
        restTemplate = newSecuredRestTemplate();
    }
    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public ResponseEntity<String> doHttpPost(String endPoint, String req) throws  HttpClientErrorException {
        return doHttpPost(endPoint, req, null);
    }
    public ResponseEntity<String> doHttpPut(String endPoint, String req) throws  HttpClientErrorException {
        return doHttpPut(endPoint, req, null);
    }
    public ResponseEntity<String> doHttpDelete(String endPoint) throws  HttpClientErrorException {
        return doHttpDelete(endPoint, null);
    }
    public ResponseEntity<String> doHttpPost(String endPoint, String req, String token) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }
        if (basicAuth != null) {
            headers.add("Authorization", "Basic " + basicAuth);
        }
        HttpEntity<String> request = new HttpEntity<>(req, headers);
        return perform(url, HttpMethod.POST, request);
    }
    public ResponseEntity<String> doHttpPut(String endPoint, String req, String token) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }
        if (basicAuth != null) {
            headers.add("Authorization", "Basic " + basicAuth);
        }
        HttpEntity<String> request = new HttpEntity<>(req, headers);
        return perform(url, HttpMethod.PUT, request);
    }
    public ResponseEntity<String> doHttpDelete(String endPoint, String token) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }
        if (basicAuth != null) {
            headers.add("Authorization", "Basic " + basicAuth);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        return perform(url, HttpMethod.DELETE, request);
    }
    public ResponseEntity<String> doHttpPostWithHeader(String endPoint, String req, HttpHeaders headers) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpEntity<String> request = new HttpEntity<>(req, headers);
        return perform(url, HttpMethod.POST, request);
    }
    public ResponseEntity<String> doHttpGet(String endPoint) throws  HttpClientErrorException {
        return doHttpGet(endPoint, null);
    }
    public ResponseEntity<String> doHttpGet(String endPoint, String token) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }
        if (basicAuth != null) {
            headers.add("Authorization", "Basic " + basicAuth);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        return perform(url, HttpMethod.GET, request);
    }
    public ResponseEntity<String> doHttpGetWithHeader(String endPoint, HttpHeaders headers) throws  HttpClientErrorException {
        String url = endPoint != null ? baseUrl + "/" + endPoint : baseUrl;
        HttpEntity<String> request = new HttpEntity<>(headers);
        return perform(url, HttpMethod.GET, request);
    }
    public ResponseEntity<String> perform(String url, HttpMethod method, HttpEntity<?> request) throws  HttpClientErrorException {
            return restTemplate.exchange(url, method, request, String.class);
       /* try {
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            LOGUTIL.error(LOGGER, e.getMessage());
            return null;
        }*/
    }
    private RestTemplate newSecuredRestTemplate() {
        char[] password = "test".toCharArray();
        SSLContext sslContext = null;
        try {
            KeyStore ks = keyStore("", password);
            sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(ks, password)
                    .loadTrustMaterial(ks, new TrustSelfSignedStrategy()).build();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return new RestTemplateBuilder()
                .requestFactory(new ClientHttpRequestFactorySupplier() {
                    @Override
                    public ClientHttpRequestFactory get() {
                        return new HttpComponentsClientHttpRequestFactory(client);
                    }
                })
                .build();
    }
    private KeyStore keyStore(String file, char[] password) throws Exception {
        System.out.println("FILE: " + file);
        KeyStore keyStore = KeyStore.getInstance("JKS");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }
    public static Map<String, Object> toMap(String str) {
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
        };
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, typeRef);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new HashMap<>();
        }
    }
    /**
     * Decodes the passed UTF-8 String using an algorithm that's compatible with
     * JavaScript's <code>decodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The UTF-8 encoded String to be decoded
     * @return the decoded String
     */
    public static String decodeURIComponent(String s) {
        if (s == null) {
            return null;
        }
        String result = null;
        try {
            result = URLDecoder.decode(s, "UTF-8");
        }
        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }
        return result;
    }
    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's <code>encodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The String to be encoded
     * @return the encoded String
     */
    public static String encodeURIComponent(String s) {
        String result = null;
        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("%21", "!")
                    .replaceAll("%27", "'")
                    .replaceAll("%28", "(")
                    .replaceAll("%29", ")")
                    .replaceAll("%7E", "~");
        }
        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }
        return result;
    }
    public static List<Object> toArrayList(String str) {
        TypeReference<ArrayList<Object>> typeRef = new TypeReference<ArrayList<Object>>() {
        };
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, typeRef);
        } catch (IOException e) {
           System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    public static JsonNode toJsonNode(String str) {
        if (str == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            /*ObjectNode ret = new ObjectMapper().createObjectNode();
            String tmp;
            while(node.fieldNames().hasNext()) {
                tmp = node.fieldNames().next();
                ret.putPOJO(tmp.toLowerCase(),ret.get(tmp));
            }*/
            return mapper.readTree(str);
        } catch (IOException e) {
           System.out.println(e.getMessage());
            return mapper.createObjectNode();
        }
    }
    public static List<Map<String, Object>> toMapList(String str) {
        TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<List<Map<String, Object>>>() {
        };
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, typeRef);
        } catch (IOException e) {
             e.getMessage();
            return new ArrayList<>();
        }
    }
    public void setBasicAuth(String token) {
        basicAuth = token;
    }
    public void setBasicAuth(String user, String password) {
        String plainCreds = user + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        this.basicAuth = new String(base64CredsBytes);
    }
    public static String stringfy(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
