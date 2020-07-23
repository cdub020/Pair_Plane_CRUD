package com.galvanize.Plane.Models;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pilot")
public class PilotController {
        public static PilotRepo repository;
        
    public PilotController(PilotRepo repository) {
        PilotController.repository = repository;
    }

    //POST - new pilot
    @PostMapping("")
    public Pilot createPilot(@RequestBody Pilot pilot) {
        return PilotController.repository.save(pilot);
    }

    @GetMapping("/{id}")
    public Pilot getPilot(@PathVariable Long id){
        return PilotController.repository.findById(id).orElse(null);
    }

    //DELETE a pilot from the pilots database
    @DeleteMapping("/{id}")
    public void deletePilotByID(@PathVariable long id){
        this.repository.deleteById(id);
    }
}
