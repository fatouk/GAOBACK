/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author fakaloga
 */
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDebutReservation;
    private Date dateFinReservation;
    private Date dateCreate;
    private Date dateUpdate;
//    status:encours ou termine
     private String status;
//    etat:0 pour supprimer ou 1 pour initiliser
     private boolean etat;
    @ManyToOne
    private Ordinateur ordinateur;
    @ManyToOne
    private Utilisateur utilisateur;

    public Reservation() {
    }

    public Reservation(Date dateDebutReservation, Date dateFinReservation, Date dateCreate, Date dateUpdate, String status, boolean etat, Ordinateur ordinateur, Utilisateur utilisateur) {
        this.dateDebutReservation = dateDebutReservation;
        this.dateFinReservation = dateFinReservation;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.status = status;
        this.etat = etat;
        this.ordinateur = ordinateur;
        this.utilisateur = utilisateur;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

 
       

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ordinateur getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateDebutReservation() {
        return dateDebutReservation;
    }

    public void setDateDebutReservation(Date dateDebutReservation) {
        this.dateDebutReservation = dateDebutReservation;
    }

    public Date getDateFinReservation() {
        return dateFinReservation;
    }

    public void setDateFinReservation(Date dateFinReservation) {
        this.dateFinReservation = dateFinReservation;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

      
    
}
