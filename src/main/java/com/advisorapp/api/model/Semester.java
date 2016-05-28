package com.advisorapp.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue
    private long id;

    @Column()
    @Range(min = 1)
    private int number;

    @ManyToOne
    @JoinColumn(name = "study_plan_id", nullable = false)
    private StudyPlan studyPlan;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "semesters")
    @JsonBackReference
    private Set<Uv> uvs;

    public Set<Uv> getUvs() {
        return this.uvs;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
    }

    public void addUv(Uv uv){
        this.uvs.add(uv);
        uv.addSemester(this);
    }

    public void setUvs(Set<Uv> uvs) {
        this.uvs = uvs;
    }
}