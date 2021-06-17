/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;

import com.example.gao.entities.Ordinateur;
import com.example.gao.repositories.OrdinateurRepository;
import com.example.gao.service.OrdinateurService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fakaloga
 */
@Service
public class OrdinateurServiceImpl implements OrdinateurService{
@Autowired
OrdinateurRepository ordirepos;
    @Override
    public Ordinateur saveOrdinateur(Ordinateur ordinateur) {
        Ordinateur ordisave=ordirepos.save(ordinateur);
        return ordisave;
    }

    @Override
    public Ordinateur updateOrdinateur(Ordinateur ordinateur) {
        return ordirepos.save(ordinateur);
    }

    @Override
    public void deleteOrdinateur(Ordinateur ordinateur) {
        ordirepos.delete(ordinateur);
    }

    @Override
    public void deleteOrdinateurById(Long idOrdinateur) {
        ordirepos.deleteById(idOrdinateur);
    }

    @Override
    public Ordinateur getOrdinateurById(Long idOrdinateur) {
        return ordirepos.findById(idOrdinateur).get();
    }

    @Override
    public List<Ordinateur> getAllOrdinateur() {
        return ordirepos.findAll();
    }

    @Override
    public Ordinateur getOrdinateurByStatus(String status) {
        return ordirepos.findOneByStatus(status);
    }
     @Override
    public List<Ordinateur> getAllOrdinateurByStatus(String status) {
        return ordirepos.findAllByStatus(status);
    }
    
}
