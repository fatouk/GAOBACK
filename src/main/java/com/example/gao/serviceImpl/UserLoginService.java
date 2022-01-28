/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;


import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.UtilisateurRepository;
import com.example.gao.service.UtilisateurService;
import com.example.gao.utils.Constant;
import com.example.gao.utils.ResponseWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fakaloga
 */
@Service
public class UserLoginService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ADService.class);
    @Autowired
    ADService ads;
    @Autowired
    UtilisateurService userSrv;
    @Autowired
    UtilisateurRepository userRepo;

    public Utilisateur findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public Utilisateur login(ADService.LoginForm data) {
        Utilisateur user = null;
        int level = 1;
        ResponseWrapper res = ads.auth(data);
        // ResponseWrapper res = ads.uidlookup("KAMATE0803");
//        if (res == null || res.hasError()) {
        if (res.getCode() != 200) {
            return null;
        }
        // generate token
//       System.out.println("---------------------  res.getData:"+ res.getData());
        JsonNode userData = (JsonNode) res.getData();
        System.out.println("--------------------- userData:" + userData);
        user = new Utilisateur();       
        user.setLogin(userData.get("login").asText());
        user.setEmail(userData.get("email").asText());
        user.setDatenais(new Date());
        user.setNom(userData.get("lastname").asText());
        user.setPrenom(userData.get("firstname").asText());
        user.setProfil(Constant.USER);
        user.setStatus("LIBRE");
        user.setEtat(true);
        user.setDateCreate(new Date());
        user.setDateUpdate(new Date()); 

        return user;

    }

}
