/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;

import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.UtilisateurRepository;
import com.example.gao.service.UtilisateurService;
import com.example.gao.utils.JwtUtils;
import com.example.gao.utils.ResponseWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 *
 * @author fakaloga
 */
@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    @Autowired
    UtilisateurRepository userrepos;
    

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
       Utilisateur userSave=userrepos.save(utilisateur);
       return userSave;
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return userrepos.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Utilisateur utilisateur) {
        userrepos.delete(utilisateur);
    }

    @Override
    public void deleteUtilisateurById(Long idutilisateur) {
        userrepos.deleteById(idutilisateur);
    }

    @Override
    public Utilisateur getUtilisateurById(Long idUtilisateur) {
//        return userrepos.findById(idUtilisateur).get();
         return userrepos.findByIdAndEtat(idUtilisateur,true);
       
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return userrepos.findAll();
    }

    @Override
    public Utilisateur getUtilisateurByEtat(Boolean etat) {
        return userrepos.findOneByEtat(etat);    }
    
     @Override
    public Utilisateur findbyloginbypassword(String login,String password){
        Utilisateur user=new Utilisateur();
        user=userrepos.findOneByloginbypassword(login,password);
        return user;
    }
     @Override
    public Utilisateur findById(Long id) {
        return  userrepos.findById(id).get();
    }

    @Override
    public boolean existsById(String login) {
        return userrepos.equals(login);
    }

    @Override
    public List<Utilisateur> findAllByEtat(boolean etat) {
        return userrepos.findAllByEtat(etat);    }
    
    
    @Override
    public ResponseWrapper login(String login,String password){
         try {
              Utilisateur userLocal=userrepos.findOneByloginbypassword(login,password);
//            Utilisateur userLDAP= userLoginService.login(new ADService.LoginForm(login, password));
//           System.out.println("userLDAP++++++++"+userLDAP);
            if(userLocal==null){
                return null; 
           }
//            Utilisateur userLocal=userepos.findOneBylogin(login);
            userLocal.setId((userLocal==null?null:userLocal.getId()));
            userLocal.setProfil((userLocal==null?userLocal.getProfil():"user"));
//            userepos.save(userLDAP);
            Map<String, Object> claims = new HashMap<>();   
            claims.put("login", userLocal.getLogin());
            claims.put("email", userLocal.getEmail());
            
            GrantedAuthority authorities =new SimpleGrantedAuthority(userLocal.getProfil());
            claims.put("roles", authorities);
            Map<String, Object> ret = new HashMap<>();
            ret.put("token", JwtUtils.generateToken(userLocal.getLogin(), claims));
            ret.put("user", userLocal);
        return ResponseWrapper.success(ret);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }
}
