package com.galvanize.Plane.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepo extends CrudRepository<Flights,Long> {
    List<Flights> findByflightStartTimeBetween(
        @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")Date date,
        @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")Date date2);
    List<Flights> findBypilotId(Long pilotId);
    List<Flights> findBypilotIdIsNull();
}
