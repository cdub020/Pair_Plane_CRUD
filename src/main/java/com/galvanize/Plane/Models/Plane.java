package com.galvanize.Plane.Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;

@Entity
@Table(name="Planes")
@RequestMapping
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plane {
    @Id
    private String tailNumber;
    String model;

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }





}
