package com.galvanize.Plane.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/flight")
public class FlightController {
    public static FlightRepo repository;
    
    public FlightController(FlightRepo repository) {
        FlightController.repository = repository;
    }

    @PostMapping("")
    public Flights createFlight(@RequestBody Flights flights) {
        return FlightController.repository.save(flights);
    }

    @GetMapping("/{id}") 
    public Flights getFlight(@PathVariable Long id){
        return FlightController.repository.findById(id).orElse(null);
    }

    //DELETE - a flight
    @DeleteMapping("/{id}")
    public void deleteFlightByID(@PathVariable long id){
        FlightController.repository.deleteById(id);
    }

    

    //GET - /search/date/{date}
    //  Specify a date in MM-dd-yyyy format to check for flights on that date.
    @GetMapping("/search/date/{d}")
    public List<Flights> searchByDate(
        @PathVariable String d
    ) {
        // @DateTimeFormat(pattern="yyyy-MM-dd")
        Date date = new Date();
		try {
			date = new SimpleDateFormat("MM-dd-yyyy").parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        //Convuluted Java way to find out what the end of the day is.
		Calendar calendar = Calendar.getInstance();
        Date date2 = (Date)date.clone();
	    calendar.setTime(date2);
        calendar.add(Calendar.DATE,1);
        // cal.set(2017, 03, 21, 07, 34);
        System.out.println(date+"/"+calendar.getTime());
        return FlightController.repository.findByflightStartTimeBetween(date,calendar.getTime());
    }

    //PATCH - /time/{flightId}
    //Update the start time and/or arrival time of a flight.
    //Format is MM-dd-yyyy hh:mm:ss.
    //body:
    //  {startTime:"01-13-1993 17:14:12",
    //      arrivalTime: "01-14-1993 19:52:18"} 
    //
    @PatchMapping("/time/{flightId}")
    public Flights updateTime(
        @PathVariable Long flightId,
        @RequestBody Map<String,String> dates
    ) {
        if (FlightController.repository.existsById(flightId)) {
            Flights flight = FlightController.repository.findById(flightId).get();
            if (dates.containsKey("startTime")) {
                try {
                    flight.setFlightStartTime(new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(dates.get("startTime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (dates.containsKey("arrivalTime")) {
                try {
                    flight.setFlightArrivalTime(new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(dates.get("arrivalTime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            FlightController.repository.save(flight);
            return flight;
        } else {
            return null;
        }
    }

    //PATCH - /notes/{flightId}
    // Add to the current flight ID's notes.
    //body:
    //  {notes:"Note updates here."}
    //
    @PatchMapping("/notes/{flightId}")
    public String updateNotes(
        @PathVariable Long flightId,
        @RequestBody Note notes
    ) {
        if (FlightController.repository.existsById(flightId)) {
            Flights flight = FlightController.repository.findById(flightId).get();
            StringBuilder sb = new StringBuilder();
            if (flight.getNotes()!=null) {
                sb.append(flight.getNotes());
            }
            sb.append("\n").append(notes.getNotes());
            flight.setNotes(sb.toString());
            FlightController.repository.save(flight);
            return "Updated notes for flight "+flightId+" to: "+flight.getNotes();
        } else {
            return "Could not find flight "+flightId+"!";
        }
    }
    
    @RequestMapping
    static class Note{
        String notes;

        public String getNotes() {
            return notes;
        }
        public void setNotes(String notes) {
            this.notes=notes;
        }
    }

    //GET - /search?pilot=3&pilot=6
    // Gets all flights that these pilot ids are assigned to. 
    @GetMapping("/search")
    public List<Flights> getFlights(@RequestParam("pilot") Long[] pilots) {
        List<Flights> flights= new ArrayList<Flights>();
        for (int i=0;i<pilots.length;i++) {
            //Only include unique flights between all pilots.
            List<Flights> flightList = FlightController.repository.findBypilotId(pilots[i]);
            for (Flights f : flightList) {
                boolean found = false;
                for (Flights f2:flights) {
                    if (f.getId()==f2.getId()) {
                        found=true;
                        break;
                    }
                }
                if (!found) {
                    flights.add(f);
                }
            }
        }
        return flights;
    }

        //PATCH - Remove pilot from a flight given the flight ID
        @PatchMapping("/removePilot/{flightnumber}")
        public Flights removePilot(@PathVariable long flightnumber){
            Optional<Flights> tmpflight = FlightController.repository.findById(flightnumber);
            Optional<Pilot> thepilot = PilotController.repository.findById(tmpflight.get().getPilotId());
            thepilot.get().setAvailable(true);
            PilotController.repository.save(thepilot.get());
            tmpflight.get().setPilotId(null);
            return FlightController.repository.save(tmpflight.get());
        }

        //PATCH - Update who the pilot will be on the flight
        @PatchMapping("/changePilot/{flightnumber}/{newpilot}")
        public Flights removePilot(@PathVariable long flightnumber, @PathVariable long newpilot){
            Flights tmpflight = FlightController.repository.findById(flightnumber).get();
            tmpflight.setPilotId(newpilot);
            return FlightController.repository.save(tmpflight);
        }

        //PATCH - which plane assigned to flight. flight unique ID/ Plane tail number -- Return updated flight
        @PatchMapping("/changePlane/{flightnumber}/{newplane}")
            public Flights changePlane(@PathVariable long flightnumber, @PathVariable String newplane) {
            Flights tmpflight = FlightController.repository.findById(flightnumber).get();
            Plane theplane = PlaneController.repository.findBytailNumber(newplane);
            if (theplane != null){
                tmpflight.setplaneTailNumber(newplane);
            }
            return FlightController.repository.save(tmpflight);
            }
}
