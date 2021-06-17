/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.service;


import com.example.gao.entities.Utilisateur;
import com.example.gao.utils.ResponseWrapper;
import java.util.List;

/**
 *
 * @author fakaloga
 */
public interface UtilisateurService {
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateurById(Long idutilisateur);
    Utilisateur getUtilisateurById(Long idUtilisateur);
    List<Utilisateur> getAllUtilisateur();
    Utilisateur getUtilisateurByEtat(Boolean etat);
    public Utilisateur findbyloginbypassword(String login,String password); 
    Utilisateur findById(Long id);
    boolean existsById(String login) ;
    List<Utilisateur> findAllByEtat(boolean etat) ;
    ResponseWrapper login(String login,String password);
    

}
