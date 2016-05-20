package com.advisorapp.api.model;


import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column()
    private Date birthday;

    @Column(name = "remote_id")
    private String remoteId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<StudyPlan> studyPlans;


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public Set<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void addStudyPlan(StudyPlan studyPlan){
        this.studyPlans.add(studyPlan);
        studyPlan.setUser(this);
    }
}
