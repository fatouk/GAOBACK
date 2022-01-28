/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.controllers;

import com.example.gao.entities.Ordinateur;
import com.example.gao.repositories.OrdinateurRepository;
import com.example.gao.service.OrdinateurService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api")
public class OrdinateurController {
    @Autowired
    OrdinateurService ordiService;
    @Autowired
    OrdinateurRepository ordiRepos;
    
    @GetMapping("/listOrdinateur")
    @ResponseBody
    public List<Ordinateur> getlistOrdinateur(){
        return ordiService.getAllOrdinateur();
    }
    @GetMapping("/listAllOrdinateur")
    @ResponseBody
    public List<Ordinateur> findAllOrdinateurByEtat(){
        return ordiRepos.findListByEtat();
    }
    @PostMapping("/saveOrdinateur")
    @ResponseBody
    public Ordinateur saveQuestion(@RequestBody Ordinateur ordi){
        ordi.setStatus("LIBRE");
        return ordiService.saveOrdinateur(ordi);
        
    }
    @PutMapping(value="/updateOrdinateur/{id}")
     public  ResponseEntity<Ordinateur> updateQuestion(@RequestBody Ordinateur q,@PathVariable Long id){
        Ordinateur ordi=ordiService.getOrdinateurById(id);
        q.setId(ordi.getId());
        Ordinateur qsave=ordiService.saveOrdinateur(q);
        return new ResponseEntity<Ordinateur>(qsave, HttpStatus.OK);
        
    }
    @DeleteMapping(value="/deleteOrdinateur/{id}")
     public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        Ordinateur ordi=ordiService.getOrdinateurById(id);
        ordi.setEtat(false);
        ordiService.saveOrdinateur(ordi);
        return new ResponseEntity<Void>(HttpStatus.OK);
     }
    @GetMapping("/listOrdinateur/{status}")
    @ResponseBody
    public List<Ordinateur> getlistByStatus(@PathVariable String status){
        return ordiService.getAllOrdinateurByStatus(status);
    }
    @GetMapping("/oneOrdinateur/{id}")
    @ResponseBody
    public Ordinateur getOrdinateurById(@PathVariable Long id){
        return ordiService.getOrdinateurById(id);
    }
    
}
