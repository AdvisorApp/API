package com.advisorapp.api.model;


import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @C


}
