/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.controllers;

import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.UtilisateurRepository;
import com.example.gao.service.UtilisateurService;
import com.example.gao.serviceImpl.ADService;
import com.example.gao.utils.ResponseWrapper;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author fakaloga
 */
@Controller
@RequestMapping("/api")
public class UtilisateurController {
    
    @Autowired
   private UtilisateurService userService;
    @Autowired
    private UtilisateurRepository userRepository;
    
    @GetMapping("/one/{login}/{password}")
    @ResponseBody
    public Utilisateur findbylogin(@PathVariable("login") String loginx,@PathVariable String password){
        return userService.findbyloginbypassword(loginx,password);
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseWrapper login(@RequestBody ADService.LoginForm loginForm ) {
        return userService.login(loginForm.getLogin(),loginForm.getPassword());
    }
   
    @GetMapping("/listuser")
    @ResponseBody
    public List<Utilisateur> findAll(){
        return userService.findAllByEtat(true);
    }
    @PostMapping("/saveuser")
    @ResponseBody
    public Utilisateur save(@RequestBody Utilisateur user){
        user.setDateCreate(new Date());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setDateUpdate(new Date());
        user.setEtat(true);
        if(user.getProfil()==null || user.getProfil()==""){
            user.setProfil("USER");
        }
        return userService.saveUtilisateur(user);
    }
  
    @PutMapping(value="/updateuser/{id}")
        public ResponseEntity<Utilisateur> update(@RequestBody Utilisateur user,@PathVariable Long id){
        Utilisateur userUpdate=userService.findById(id);
        user.setId(userUpdate.getId());
        user.setDateUpdate(new Date());
        Utilisateur savedUsers=userService.saveUtilisateur(user);
        return new ResponseEntity<Utilisateur>(savedUsers, HttpStatus.OK);
    }
   @RequestMapping(value="/one/{id}")
   @ResponseBody
    public Utilisateur getUsersById(@PathVariable Long id) {
	       Utilisateur user = userService.findById(id);
               return user;
        
}
   @DeleteMapping("/onedelete/{id}")
       public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
         Utilisateur user = userService.findById(id);
         user.setEtat(false);
          user.setDateUpdate(new Date());
         userService.saveUtilisateur(user);
//	 userSrv.deleteById(id);
        return  new ResponseEntity<Void>( HttpStatus.OK);
    }
    
}
