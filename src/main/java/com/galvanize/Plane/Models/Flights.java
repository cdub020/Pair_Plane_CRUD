package com.galvanize.Plane.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Flights")
@RequestMapping
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String planeTailNumber; //Plane tied to this flight.
    Long pilotId; //Pilot tied to this flight.

    @Column(columnDefinition="date")
    @JsonFormat(pattern="MM-dd-yyyy hh:mm:ss")
    Date flightStartTime; //Take-off time.
    @Column(columnDefinition="date")
    @JsonFormat(pattern="MM-dd-yyyy hh:mm:ss")
    Date flightArrivalTime; //Destination arrival time.

    String notes; //Notes on the flight, delimited by \n

    String outboundLoc; //outbound location
    String inboundLoc; //inbound location

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getplaneTailNumber() {
        return planeTailNumber;
    }

    public void setplaneTailNumber(String planeTailNumber) {
        this.planeTailNumber = planeTailNumber;
    }

    public Long getPilotId() {
        return pilotId;
    }

    public void setPilotId(Long pilotId) {
        this.pilotId = pilotId;
    }

    public Date getFlightStartTime() {
        return flightStartTime;
    }

    public void setFlightStartTime(Date flightStartTime) {
        this.flightStartTime = flightStartTime;
    }

    public Date getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(Date flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOutboundLoc() {
        return outboundLoc;
    }

    public void setOutboundLoc(String outboundLoc) {
        this.outboundLoc = outboundLoc;
    }

    public String getInboundLoc() {
        return inboundLoc;
    }

    public void setInboundLoc(String inboundLoc) {
        this.inboundLoc = inboundLoc;
    }



}
