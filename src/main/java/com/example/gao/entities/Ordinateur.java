/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.entities;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



/**
 *
 * @author fakaloga
 */
@Entity
public class Ordinateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String ram;
    private String capacite_disque;
    private String marque ;
    private Boolean etat;
    private String status ;
    
    
    public Ordinateur() {
    }

    public Ordinateur(String code, String ram, String capacite_disque, String marque, Boolean etat, String status) {
        this.code = code;
        this.ram = ram;
        this.capacite_disque = capacite_disque;
        this.marque = marque;
        this.etat = etat;
        this.status = status;
       
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCapacite_disque() {
        return capacite_disque;
    }

    public void setCapacite_disque(String capacite_disque) {
        this.capacite_disque = capacite_disque;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

   

   

   }
