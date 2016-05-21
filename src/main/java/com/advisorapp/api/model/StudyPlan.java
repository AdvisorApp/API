package com.advisorapp.api.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "study_plans")
public class StudyPlan {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    @Column()
    private String name;

    @OneToMany(mappedBy = "studyPlan", cascade = CascadeType.ALL)
    private Set<Semester> semesters;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addSemester(Semester semester){
        this.semesters.add(semester);
        semester.setStudyPlan(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
