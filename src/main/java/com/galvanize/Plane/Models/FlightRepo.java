package com.galvanize.Plane.Models;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepo extends CrudRepository<Flights,Long> {
    List<Flights> findByflightStartTimeBetween(Date date,Date date2);
    List<Flights> findBypilotId(Long pilotId);
    Flights findById(Long flightId);
}
