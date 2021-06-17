package com.example.gao.security;


import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.UtilisateurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;




import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UtilisateurRepository utilisateurRepository;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager, UtilisateurRepository utilisatRepository) {
        super();
        this.authenticationManager = authenticationManager;
        this.utilisateurRepository=utilisatRepository;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        Utilisateur user = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //System.out.println("**********************");
        System.out.println("************************* Attempting authentication by " + user.getLogin() );
        //System.out.println("Password: "+user.getPassword());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), ""));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        User springUser = (User) authResult.getPrincipal();
        String jwtToken = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .claim("roles", springUser.getAuthorities())
                .compact();
        
        System.out.println("Successful authentication: new token generated for user " + springUser.getUsername());
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + jwtToken);
//        response.addHeader(SecurityConstants.FIRST_CONNEXION,this.utilisateurRepository.findOneBylogin(springUser.getUsername()).isFirstConnexion()?"true":"false");
     //   response.addHeader(SecurityConstants.PROFIL,this.utilisateurRepository.findByLogin(springUser.getUsername()).getProfil().getNom());
        //response.addHeader("Roles",springUser.getAuthorities().toString());

        //System.out.println(jwtToken);
    }

}
