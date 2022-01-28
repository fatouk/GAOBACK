/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.repositories;

import com.example.gao.entities.Utilisateur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author fakaloga
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
     public Utilisateur findOneByEtat(Boolean etat);
     @Query("SELECT o FROM Utilisateur o where o.login=:login and o.password=:password and o.etat=1")
    public Utilisateur findOneByloginbypassword(@Param("login") String login,@Param("password") String password);
     @Query("SELECT o FROM Utilisateur o where o.login=:login and o.etat=1")
    public Utilisateur findOneBylogin(@Param("login") String login);
public List<Utilisateur> findAllByEtat(boolean etat);
public Utilisateur findByIdAndEtat(Long id,boolean etat);
public Utilisateur findByLogin(String login);
}
