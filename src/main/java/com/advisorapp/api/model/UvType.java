package com.advisorapp.api.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "uv_types")
public class UvType {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String type;

    @Column(name = "hours_by_credit", nullable = false)
    private double hoursByCredit;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHoursByCredit() {
        return hoursByCredit;
    }

    public void setHoursByCredit(double hoursByCredit) {
        this.hoursByCredit = hoursByCredit;
    }
}
