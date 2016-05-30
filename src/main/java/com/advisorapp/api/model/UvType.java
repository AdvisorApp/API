package com.advisorapp.api.model;


import javax.persistence.*;

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

    public UvType setType(String type) {
        this.type = type;

        return this;
    }

    public double getHoursByCredit() {
        return hoursByCredit;
    }

    public UvType setHoursByCredit(double hoursByCredit) {
        this.hoursByCredit = hoursByCredit;

        return this;
    }
}
