package com.galvanize.Plane.Models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    

    //GET - search/date/{date}
    //  Specify a date in MM-dd-yyyy format to check for flights on that date.
    @GetMapping("search/date/{date}")
    public List<Flights> searchByDate(
        @JsonFormat(pattern="MM-dd-yyyy")
        @PathVariable Date date
    ) {
        //Convuluted Java way to find out what the end of the day is.
		Calendar calendar = Calendar.getInstance();
        Date date2 = (Date)date.clone();
	    calendar.setTime(date2);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
        return FlightController.repository.findByflightStartTimeBetween(date,cal.getTime());
    }

    //GET - search?pilot=3&pilot=6
    // Gets all flights that these pilot ids are assigned to. 
    @GetMapping("/search")
    public List<Flights> getFlights(@RequestParam("pilot") List<Pilot> pilots) {
        List<Flights> flights= new ArrayList<Flights>();
        for (Pilot p : pilots) {
            //Only include unique flights between all pilots.
            List<Flights> flightList = FlightController.repository.findByPilotId(p.getId());
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
            Optional <Flights> tmpflight = FlightController.repository.findById(flightnumber);
            tmpflight.get().setPilotId(null);
            return FlightController.repository.save(tmpflight.get());
        }

        //PATCH - Update who the pilot will be on the flight
        @PatchMapping("/changePilot/{flightnumber}/{newpilot}")
        public Flights removePilot(@PathVariable long flightnumber, PathVariable){
            Optional <Flights> tmpflight = FlightController.repository.findById(flightnumber);
            tmpflight.get().setPilotId(thepilot.)
            tmpflight.get().setPilotId(null);
            return FlightController.repository.save(tmpflight.get());
        }
        
}
