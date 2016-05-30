package com.advisorapp.api.model;

import javax.persistence.*;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Option setName(String name) {
        this.name = name;

        return this;
    }
}