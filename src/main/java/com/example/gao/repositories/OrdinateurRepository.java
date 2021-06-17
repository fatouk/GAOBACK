/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.repositories;

import com.example.gao.entities.Ordinateur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author fakaloga
 */
public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long>{
     public Ordinateur findOneByStatus(String status);
     public List<Ordinateur> findAllByStatus(String status);
     @Query("SELECT o FROM Ordinateur o where o.etat=1")
    public List<Ordinateur> findListByEtat();
}
