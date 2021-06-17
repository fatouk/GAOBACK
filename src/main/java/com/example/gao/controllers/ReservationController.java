/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.controllers;

import com.example.gao.entities.Ordinateur;
import com.example.gao.entities.Reservation;
import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.ReservationRepository;
import com.example.gao.service.OrdinateurService;
import com.example.gao.service.ReservationService;
import com.example.gao.service.UtilisateurService;
import java.util.Date;
import java.util.List;
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
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepos;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    OrdinateurService ordinateurService;
    
    @GetMapping("/listReservation")
    @ResponseBody
    public List<Reservation> getlistReservation(){
        return reservationService.getAllReservation();
    }
    
    @PostMapping("/saveReservation")
    @ResponseBody
    public Reservation saveReservation(@RequestBody Reservation r){
        Utilisateur user=utilisateurService.getUtilisateurById(r.getUtilisateur().getId());
        Ordinateur ordi=ordinateurService.getOrdinateurById(r.getOrdinateur().getId());
        if(ordi!=null){
            ordi.setStatus("OCCUPE");
            ordinateurService.saveOrdinateur(ordi);
        }
        if(user!=null){
            
            user.setStatus("OCCUPE");
            utilisateurService.saveUtilisateur(user);
        }
        r.setStatus("ENCOURS");
        r.setEtat(true);
        r.setDateCreate(new Date());
        r.setDateUpdate(new Date());
        return reservationService.saveReservation(r);
        
    }
    @PutMapping(value="/updateReservation/{id}")
     public  ResponseEntity<Reservation> updateQuestion(@RequestBody Reservation q,@PathVariable Long id){
        Reservation reserv=reservationService.getReservationById(id);
        q.setId(reserv.getId());
    if(q.getStatus().equals("TERMINE") ||!q.isEtat()){
        Utilisateur user=utilisateurService.getUtilisateurById(q.getUtilisateur().getId());
        Ordinateur ordi=ordinateurService.getOrdinateurById(q.getOrdinateur().getId());
        if(ordi!=null){
            ordi.setStatus("LIBRE");
            ordinateurService.saveOrdinateur(ordi);
        }
        if(user!=null){            
            user.setStatus("LIBRE");
            utilisateurService.saveUtilisateur(user);
        }
            
}
    q.setDateUpdate(new Date());
        Reservation reservsave=reservationService.saveReservation(q);
        return new ResponseEntity<Reservation>(reservsave, HttpStatus.OK);
        
    }
    @DeleteMapping(value="/deleteReservation/{id}")
     public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        Reservation reserv=reservationService.getReservationById(id);
        
        Utilisateur user=utilisateurService.getUtilisateurById(reserv.getUtilisateur().getId());
        Ordinateur ordi=ordinateurService.getOrdinateurById(reserv.getOrdinateur().getId());
        if(ordi!=null){
            ordi.setStatus("LIBRE");
            ordinateurService.saveOrdinateur(ordi);
        }
        if(user!=null){
            
            user.setStatus("LIBRE");
            utilisateurService.saveUtilisateur(user);
        }
        reserv.setEtat(false);
        reserv.setStatus("TERMINE");
        reserv.setDateUpdate(new Date());
        reservationService.saveReservation(reserv);
        return new ResponseEntity<Void>(HttpStatus.OK);
     }
    @GetMapping("/listReservation/{statut}")
    @ResponseBody
    public List<Reservation> getlistByStatus(@PathVariable String statut){
        return reservationService.getAllReservationByStatus(statut);
    }
    @GetMapping("/oneReservation/{id}")
    @ResponseBody
    public Reservation getReservationById(@PathVariable Long id){
        return reservationService.getReservationById(id);
    }
    @GetMapping("/getReservationbyUser/{idreservation}/{iduser}")
    @ResponseBody
    public Reservation getReservationById(@PathVariable Long idreservation,@PathVariable Long iduser){
        return reservationService.getReservationByIdandByUtilisateur_Id(idreservation,iduser);
    }
    
}
