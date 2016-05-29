package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "remote_id")
    private String remoteId;

    @Column(name = "email", nullable = false)
    @Email()
    private String email;

    @Column(name = "password",nullable = false, length = 60)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StudyPlan> studyPlans;

    public User()
    {
        this.studyPlans = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;

        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;

        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;

        return this;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public User setRemoteId(String remoteId) {
        this.remoteId = remoteId;

        return this;
    }

    public Set<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public User addStudyPlan(StudyPlan studyPlan){
        this.studyPlans.add(studyPlan);
        studyPlan.setUser(this);

        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;

        return this;
    }

    public User setStudyPlans(Set<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;

        for (StudyPlan studyPlan :  studyPlans)
        {
            studyPlan.setUser(this);
        }

        return this;
    }
}
