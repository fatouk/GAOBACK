/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.tasks;

import com.example.gao.entities.Ordinateur;
import com.example.gao.entities.Reservation;
import com.example.gao.entities.Utilisateur;
import com.example.gao.service.OrdinateurService;
import com.example.gao.service.ReservationService;
import com.example.gao.service.UtilisateurService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author fakaloga
 */
@Component
public class TaskChangeStatutReservation {
    @Autowired
    ReservationService reservationService;
    @Autowired
    OrdinateurService ordinateurService;
    @Autowired
    UtilisateurService userService;             
    
    @Scheduled(fixedRate = 300000)
    public void changeStatut(){
        Date now=new Date();    
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
        Date now1 = new Date();         
        String nowString = df.format(now1);
        List<Reservation> listeReservation=new ArrayList<>();
        listeReservation=reservationService.getAllReservation();
        System.out.println("Now is: "+ nowString);
        for(Reservation r:listeReservation ){            
            Ordinateur ordi=new Ordinateur();
            Utilisateur user=new Utilisateur();
            if(r.getDateFinReservation()==now || r.getDateFinReservation().before(now)){
                r.setStatus("TERMINE");
                ordi=r.getOrdinateur();
                ordi.setStatus("LIBRE");
                ordinateurService.saveOrdinateur(ordi);
                user=r.getUtilisateur();
                user.setStatus("LIBRE");
                userService.saveUtilisateur(user);
                reservationService.saveReservation(r);
            }
        }
    }
    
}
