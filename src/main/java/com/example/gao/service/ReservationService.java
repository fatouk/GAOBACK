/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.service;

import com.example.gao.entities.Reservation;
import java.util.List;

/**
 *
 * @author fakaloga
 */
public interface ReservationService {
    Reservation saveReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Reservation reservation);
    void deleteReservationById(Long idReservation);
    Reservation getReservationById(Long idReservation);
    List<Reservation> getAllReservation();
    public List<Reservation> getAllReservationByStatus(String status);
    Reservation getReservationByIdandByUtilisateur_Id(Long idreservation,Long iduser);
}
