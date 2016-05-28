package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "study_plans")
public class StudyPlan implements Serializable{
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @OneToMany(mappedBy = "studyPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Semester> semesters;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    public StudyPlan() {
        this.semesters = new HashSet<>();
    }

    public long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public StudyPlan setUser(User user) {
        this.user = user;

        return this;


    }

    public StudyPlan addSemester(Semester semester){
        this.semesters.add(semester);
        semester.setStudyPlan(this);

        return this;
    }

    public String getName() {
        return name;
    }

    public StudyPlan setName(String name) {
        this.name = name;

        return this;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public StudyPlan setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;

        return this;
    }

    public Option getOption() {
        return option;
    }

    public StudyPlan setOption(Option option) {
        this.option = option;

        return this;
    }

    @Override
    public String toString() {
        return "StudyPlan{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", semesters=" + semesters +
                ", option=" + option +
                '}';
    }
}
