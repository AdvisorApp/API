package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.Semaphore;

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
    private String teacherName;

    @Column()
    private String description;

    @Column(name = "is_available_for_cart", nullable = false)
    private boolean isAvailableForCart;

    @Column(nullable = false)
    @Range(min = 0)
    private int chs;

    @Column(name = "location")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "uv_type_id", nullable = false)
    private UvType uvType;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "uvs", cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy(" number ASC ")
    private Set<Semester> semesters;

    @ManyToMany
    @JoinTable(name = "corequisite_uv",
            joinColumns = @JoinColumn(name = "corequisite2"),
            inverseJoinColumns = @JoinColumn(name = "corequisite1"))
    @JsonIgnore
    @OrderBy("name ASC")
    private Set<Uv> corequisitesUv;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "corequisitesUv")
    @JsonIgnore
    @OrderBy("name ASC")
    private Set<Uv> corequisitesUvOf;

    @ManyToMany
    @JoinTable(name = "prerequisite_uv",
            joinColumns = @JoinColumn(name = "prerequisite"))
    @OrderBy("name ASC ")
    private Set<Uv> prerequisitesUv;

    @Enumerated(EnumType.STRING)
    public Location getLocation() {
        return location;
    }

    public Uv() {
        this.semesters = new HashSet<>();
        this.corequisitesUv = new HashSet<>();
        this.corequisitesUvOf = new HashSet<>();
        this.prerequisitesUv = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public Uv setRemoteId(String remoteId) {
        this.remoteId = remoteId;

        return this;
    }

    public String getName() {
        return name;
    }

    public Uv setName(String name) {
        this.name = name;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public Uv setDescription(String description) {
        this.description = description;

        return this;
    }

    public boolean isAvailableForCart() {
        return isAvailableForCart;
    }

    public Uv setIsAvailableForCard(boolean isAvailableForCard) {
        this.isAvailableForCart = isAvailableForCard;

        return this;
    }

    public int getChs() {
        return chs;
    }

    public Uv setChs(int chs) {
        this.chs = chs;

        return this;
    }

    public Uv setLocation(Location location) {
        this.location = location;

        return this;
    }

    public UvType getUvType() {
        return uvType;
    }

    public Uv setUvType(UvType uvType) {
        this.uvType = uvType;

        return this;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }


    public Uv addSemester(Semester semester) {
        this.semesters.add(semester);

        return this;
    }

    public Set<Uv> getCorequisitesUv() {
        return corequisitesUv;
    }

    public Uv addCorequisiteUv(Uv corequisite) {
        this.corequisitesUv.add(corequisite);
        corequisite.addCorequisiteOf(this);

        return this;
    }

    public Uv setCorequisitesUv(Set<Uv> corequisitesUv) {
        this.corequisitesUv = corequisitesUv;
        this.corequisitesUv.forEach(e -> e.addCorequisiteOf(this));
        return this;
    }

    public Uv addCorequisiteOf(Uv uv) {
        this.corequisitesUvOf.add(uv);

        return this;
    }
    
    @JsonIgnore
    public Set<Uv> getRealCorequisites()
    {
        Set<Uv> corequisitesUVs = new HashSet<>();
        corequisitesUVs.addAll(this.getCorequisitesUv());
        corequisitesUVs.addAll(this.getCorequisitesUvOf());
        return corequisitesUVs;
    }

    public Set<Uv> getCorequisitesUvOf() {
        return corequisitesUvOf;
    }

    public Set<Uv> getPrerequisitesUv() {
        return prerequisitesUv;
    }

    public Uv setPrerequisitesUv(Set<Uv> prerequisitesUv) {
        this.prerequisitesUv = prerequisitesUv;

        return this;
    }

    public Uv addPrerequisite(Uv prerequisite) {
        this.prerequisitesUv.add(prerequisite);

        return this;
    }
}
