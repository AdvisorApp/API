package com.advisorapp.api.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "uvs")
public class Uv {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "remote_id")
    private String remoteId;

    @Column()
    private String name;

    @Column()
    private String description;

    @Column(nullable = false)
    @Range(min = 1)
    private int minSemester;

    @Column(name = "is_available_for_card",nullable = false)
    private boolean isAvailableForCard;

    @Column(nullable = false)
    @Range(min = 1)
    private double chs;

    private Location location;

    @ManyToOne
    @JoinColumn(name = "uv_type_id",nullable = false)
    private UvType uvType;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "uv_semester", joinColumns = {
            @JoinColumn(name = "uv_id",nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "semester_id", nullable = false)})
    private Set<Semester> semesters;


    @ManyToMany
    @JoinTable(name = "corequisite_uv",
            joinColumns = @JoinColumn(name = "corequisite2"),
            inverseJoinColumns = @JoinColumn(name = "corequisite1"))
    private Set<Uv> corequisitesUv;

    @ManyToMany
    @JoinTable(name = "corequisite_uv",
            joinColumns = @JoinColumn(name = "corequisite1"),
            inverseJoinColumns = @JoinColumn(name = "corequisite2"))
    private Set<Uv> corequisitesUvOf;

    @ManyToMany
    @JoinTable(name = "prerequisite_uv",
            joinColumns = @JoinColumn(name = "prerequisite"))
    private Set<Uv> prerequisitesUv;

    @Enumerated(EnumType.STRING)
    public Location getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinSemester() {
        return minSemester;
    }

    public void setMinSemester(int minSemester) {
        this.minSemester = minSemester;
    }

    public boolean isAvailableForCard() {
        return isAvailableForCard;
    }

    public void setIsAvailableForCard(boolean isAvailableForCard) {
        this.isAvailableForCard = isAvailableForCard;
    }

    public double getChs() {
        return chs;
    }

    public void setChs(double chs) {
        this.chs = chs;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UvType getUvType() {
        return uvType;
    }

    public void setUvType(UvType uvType) {
        this.uvType = uvType;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public Set<Uv> getCorequisitesUv() {
        return corequisitesUv;
    }

    public void setCorequisitesUv(Set<Uv> corequisitesUv) {
        this.corequisitesUv = corequisitesUv;
    }

    public Set<Uv> getCorequisitesUvOf() {
        return corequisitesUvOf;
    }

    public void setCorequisitesUvOf(Set<Uv> corequisitesUvOf) {
        this.corequisitesUvOf = corequisitesUvOf;
    }

    public Set<Uv> getPrerequisitesUv() {
        return prerequisitesUv;
    }

    public void setPrerequisitesUv(Set<Uv> prerequisitesUv) {
        this.prerequisitesUv = prerequisitesUv;
    }
}
