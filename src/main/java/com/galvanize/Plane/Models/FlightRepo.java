package com.galvanize.Plane.Models;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepo extends CrudRepository<Flights,Long> {
    List<Flights> findByflightStartTimeBetween(Date date, Date date2);
    List<Flights> findBypilotId(Long pilotId);
    List<Flights> findBypilotIdIsNull();
}
