package com.galvanize.Plane.Models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PilotRepo extends CrudRepository<Pilot,Long> {
    List<Pilot> findByavailable(boolean available);
}
