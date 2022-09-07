/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.service;

import com.example.gao.entities.Ordinateur;
import java.util.List;

/**
 *
 * @author fakaloga
 */
public interface OrdinateurService {
    
    /**
    *Permet d'enregistrer un oridinateur.
    * 
    *@param  ordinateur:objet ordinateur doit etre un parametre.
    */
    Ordinateur saveOrdinateur(Ordinateur ordinateur);
    Ordinateur updateOrdinateur(Ordinateur ordinateur);
    void deleteOrdinateur(Ordinateur ordinateur);
    void deleteOrdinateurById(Long idOrdinateur);
    Ordinateur getOrdinateurById(Long idOrdinateur);
    List<Ordinateur> getAllOrdinateur();
    Ordinateur getOrdinateurByStatus(String status);
    List<Ordinateur> getAllOrdinateurByStatus(String status);
}
