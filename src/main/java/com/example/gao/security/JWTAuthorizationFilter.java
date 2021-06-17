package com.example.gao.security;



import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

    public JWTAuthorizationFilter() {
        super();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Expose-Headers", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        System.out.println(request.getHeader("__login__"));
        
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING);
            System.out.println("jwt: " + jwtToken);
            if (jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes()))
                    .parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims, null, new ArrayList<GrantedAuthority>());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }

    }

}
