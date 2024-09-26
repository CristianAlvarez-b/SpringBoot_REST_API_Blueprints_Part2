/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
@Autowired
private BlueprintsServices blueprintsServices;
@GetMapping
public ResponseEntity<?> getBlueprints(){
    try{
        return new ResponseEntity<>(blueprintsServices.getAllBlueprints(), HttpStatus.OK);
    }catch (Exception e){
        System.out.println(e.getMessage());
        return new ResponseEntity<>("Resource Not Found " , HttpStatus.NOT_FOUND);
    }
}
    
    
    
}

