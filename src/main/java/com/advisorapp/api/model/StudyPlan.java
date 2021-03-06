package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "study_plans")
public class StudyPlan implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @OneToMany(mappedBy = "studyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("number ASC ")
    private Set<Semester> semesters;

    public StudyPlan() {
        this.semesters = new HashSet<>();
    }

    public StudyPlan setId(Long id)
    {
        this.id = id;

        return this;
    }

    public Long getId() {
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
        if (!this.semesters.contains(semester)) {
            this.semesters.add(semester);
        }
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
        if (semesters == null)
        {
            return this;
        }

        this.semesters = semesters;

        return this;
    }

    public boolean containUv(Uv concernedUv) {
        return this.getUvs().stream().anyMatch(uv -> uv.getId() == concernedUv.getId());
    }

    public boolean containCorequisite(Uv concernedUv)
    {
        Set<Uv> corequisites = concernedUv.getCorequisitesUv();
        if (corequisites.size() == 0)
        {
            return true;
        }

        for (Uv corequisite : corequisites)
        {
            if (!this.containUv(corequisite))
                return false;
        }

        return true;
    }

    public boolean containPrerequisite(Uv concernedUv)
    {
        Set<Uv> prerequisites = concernedUv.getPrerequisitesUv();
        if (prerequisites.size() == 0)
        {
            return true;
        }

        for (Uv prerequisite : prerequisites)
        {
            if (!this.containUv(prerequisite))
                return false;
        }

        return true;
    }

    protected Set<Uv> getUvs() {
        Set<Uv> uvs = new HashSet<>();
        semesters.stream().forEach(e -> uvs.addAll(e.getUvs()));
        return uvs;
    }

    @JsonIgnore
    public Set<Uv> getCartUvs() {
        Set<Uv> cartUvs = new HashSet<>();
        for(Semester semester : this.getSemesters()){
            for(Uv uv : semester.getUvs()){
                if(uv.isAvailableForCart()){
                    cartUvs.add(uv);
                }
            }
        }
        return cartUvs;
    }

    @Override
    public String toString() {
        return "StudyPlan{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", semesters=" + semesters +
                '}';
    }
}
