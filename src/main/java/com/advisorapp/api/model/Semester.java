package com.advisorapp.api.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Entity
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue
    private long id;

    @Column()
    @Range(min = 1)
    private int number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "study_plan_id", nullable = false)
    private StudyPlan studyPlan;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "uv_semester",
        joinColumns = {@JoinColumn(name = "semester_id",nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "uv_id", nullable = false)}
    )
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

    private Semester setNumber(int number) {
        this.number = number;

        return this;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public Semester setStudyPlan(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
        if (!this.studyPlan.getSemesters().contains(this)) {
            this.studyPlan.addSemester(this);
        }
        this.setNumber(studyPlan.getSemesters().size());

        return this;
    }

    public int getTotalChs()
    {
        return this.uvs.stream().collect(Collectors.summingInt( Uv::getChs ));
    }


    public Semester addUv(Uv uv){
        this.uvs.add(uv);
        uv.addSemester(this);

        //TODO add its corequisites and prerequisites

        return this;
    }

    public Semester setUvs(Set<Uv> uvs) {
        this.uvs = uvs;

        for (Uv uv : uvs)
        {
            uv.addSemester(this);
        }

        return this;
    }

    public String toString()
    {
        return "Semester " + number;
    }
}