/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Service("inMemoryBlueprintPersistence")
@Primary
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        try{
            setUpBlueprints();
        } catch (BlueprintPersistenceException e){
            System.out.println(e.getMessage());
        }
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if(blueprint == null){
            throw new BlueprintNotFoundException("Blueprint Not Found.");
        }
        return blueprint;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String name) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsByAuthor = new HashSet<>();

        // Iterar sobre el mapa de blueprints para encontrar aquellos que coincidan con el autor
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            Blueprint bp = entry.getValue();
            if (bp.getAuthor().equals(name)) {
                blueprintsByAuthor.add(bp);
            }
        }

        // Si no se encuentran planos, lanzar excepción
        if (blueprintsByAuthor.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + name);
        }

        return blueprintsByAuthor;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    private void setUpBlueprints() throws BlueprintPersistenceException {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] points1=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] points = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        Blueprint newBlueprint = new Blueprint("John Doe", "NewPlan", points);
        Blueprint newBlueprint2 = new Blueprint("John Doe", "AnotherPlan", points);
        Blueprint newBlueprint3 = new Blueprint("esteban", "WhatAPlan", points1);
        saveBlueprint(newBlueprint);
        saveBlueprint(newBlueprint2);
        saveBlueprint(bp);
        saveBlueprint(newBlueprint3);
    }
}
