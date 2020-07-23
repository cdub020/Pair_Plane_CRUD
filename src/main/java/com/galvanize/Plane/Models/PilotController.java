package com.galvanize.Plane.Models;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        PilotController.repository.deleteById(id);
    }


    //POST - /assign
    //Assigns unassigned pilots to all flights that have no pilots.
    @PostMapping("/assign")
    public List<Assignment> assignAllFlights() {
        List<Flights> flights = FlightController.repository.findBypilotIdIsNull();
        List<Pilot> pilots = PilotController.repository.findByavailable(true);
        List<Assignment> log = new ArrayList<Assignment>();
        System.out.println(flights);
        System.out.println(pilots);
        for (Flights f : flights) {
            if (pilots.size()>0) {
                Pilot p = pilots.remove(0);
                //Assign.
                f.setPilotId(p.getId());
                //Make the pilot unavailable for more.
                p.setAvailable(false);
                FlightController.repository.save(f);
                PilotController.repository.save(p);
                //log.add("Assigned pilot "+p.id+" to flight "+f.id);
                Assignment a = new Assignment();
                a.pilotId=p.getId();
                a.flightId=f.getId();
                log.add(a);
            }
        }
        return log;
    }

    @RequestMapping
    static class Assignment{
        Long pilotId = -1l;
        Long flightId = -1l;

        public Long getpilotId() {
            return pilotId;
        }
        public void setpilotId(Long pilotId) {
            this.pilotId=pilotId;
        }
        public Long getflightId() {
            return flightId;
        }
        public void setflightId(Long flightId) {
            this.flightId=flightId;
        }
    }
}
