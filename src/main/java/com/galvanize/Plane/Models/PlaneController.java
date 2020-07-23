package com.galvanize.Plane.Models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@RestController
@RequestMapping("/plane")
public class PlaneController {
    public static PlaneRepo repository;

    public PlaneController(PlaneRepo repository) {
        PlaneController.repository = repository;
    }
    
    
    /*POST - input a flight to the database, show only pilot name, 
             date/time of flight, from/to where the flight is going*/

    @PostMapping("")
    public Plane createPlane(@RequestBody Plane plane) {
        return PlaneController.repository.save(plane);
    }

    
    
    @GetMapping("/all") 
    public Iterable<Plane> getPlanes(){
        return PlaneController.repository.findAll();
    }
    
    @GetMapping("/{tailNumber}") 
    public Plane getPlane(@PathVariable String tailNumber){
        return PlaneController.repository.findById(tailNumber).orElse(null);
    }

    /*
    @GetMapping("/getflight") 
    public Iterable<Flights> getFlight(){
        return FlightController.repository.findAll();
    }*/





    
}
