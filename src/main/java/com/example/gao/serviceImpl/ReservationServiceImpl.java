/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;

import com.example.gao.entities.Reservation;
import com.example.gao.repositories.ReservationRepository;
import com.example.gao.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fakaloga
 */
@Service
public class ReservationServiceImpl implements ReservationService{
@Autowired
ReservationRepository reserrepos;
    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reserrepos.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reserrepos.save(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        reserrepos.delete(reservation);
    }

    @Override
    public void deleteReservationById(Long idReservation) {
        reserrepos.deleteById(idReservation);
    }

    @Override
    public Reservation getReservationById(Long idreserv) {
        return reserrepos.findById(idreserv).get();
    }

    @Override
    public List<Reservation> getAllReservation() {
       return reserrepos.findListByEtat();
    }


    @Override
    public List<Reservation> getAllReservationByStatus(String status) {
        return reserrepos.findAllByStatus(status);
    }

    @Override
    public Reservation getReservationByIdandByUtilisateur_Id(Long idreservation, Long iduser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
}
