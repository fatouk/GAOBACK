/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.security;

/**
 *
 * @author fakaloga
 */
public class SecurityConstants {
    private SecurityConstants() {
    }
    public static final String SECRET = "Paiment-generique_ofmm@Orangemali_togola080857";
    public static final long EXPIRATION_TIME = 86_400_000; //1 jour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
  public static final String[] WHITE_URL= new String[] {
    "/api/login",
    "/api/listuser",
    "/api/onedelete/{id}",
    "/api/saveuser",
    "/api/one/{id}",
    "/api/updateuser/{id}",
    "/api/listAllOrdinateur",
    "/api/saveOrdinateur",
    "/api/oneOrdinateur/{id}",
    "/api/deleteOrdinateur/{id}",
    "/api/updateOrdinateur/{id}",
    "/api/listReservation",
    "/api/saveReservation",
    "/api/updateReservation/{id}",
    "/api/oneReservation/{id}",
    "/api/deleteReservation/{id}", 
    "/api/listOrdinateur/{status}",
    "/api/upload",
    "/register"
  };
}
