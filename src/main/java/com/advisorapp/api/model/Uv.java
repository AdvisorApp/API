package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.HashSet;
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

    @Column(name = "is_available_for_cart",nullable = false)
    private boolean isAvailableForCart;

    @Column(nullable = false)
    @Range(min = 0)
    private int chs;

    @Column(name = "location")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = true)
    private Option option;

    @ManyToOne
    @JoinColumn(name = "uv_type_id",nullable = false)
    private UvType uvType;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "uvs")
    @JsonIgnore
    private Set<Semester> semesters;

    @ManyToMany
    @JoinTable(name = "corequisite_uv",
            joinColumns = @JoinColumn(name = "corequisite2"),
            inverseJoinColumns = @JoinColumn(name = "corequisite1"))
    @JsonIgnore
    private Set<Uv> corequisitesUv;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "corequisitesUv")
    @JsonIgnore
    private Set<Uv> corequisitesUvOf;

    @ManyToMany
    @JoinTable(name = "prerequisite_uv",
            joinColumns = @JoinColumn(name = "prerequisite"))
    private Set<Uv> prerequisitesUv;

    @Enumerated(EnumType.STRING)
    public Location getLocation() {
        return location;
    }

    public Uv()
    {
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

    public int getMinSemester() {
        return minSemester;
    }

    public Uv setMinSemester(int minSemester) {
        this.minSemester = minSemester;

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

    public Uv addCorequisiteUv(Uv corequisite)
    {
        this.corequisitesUv.add(corequisite);
        corequisite.addCorequisiteOf(this);

        return this;
    }

    public Uv setCorequisitesUv(Set<Uv> corequisitesUv) {
        this.corequisitesUv = corequisitesUv;
        for (Uv coUv : corequisitesUv)
        {
            coUv.addCorequisiteOf(this);
        }

        return this;
    }

    public Uv addCorequisiteOf(Uv uv)
    {
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

    /**
    public Set<Uv> getRealCorequisites(Set<Uv> managedCorequisites, Set<Uv> realCorequisites)
    {
        return this.getRealCorequisites(this, managedCorequisites, realCorequisites);
    }

    protected Set<Uv> getRealCorequisites(Uv concernedUv, Set<Uv> managedCorequisites, Set<Uv> realCorequisites)
    {
        concernedUv.corequisitesUv.stream().filter(uv -> !managedCorequisites.contains(uv)).forEach(uv -> {
            realCorequisites.add(uv);
            managedCorequisites.add(uv);
            this.getRealCorequisites(uv, managedCorequisites, realCorequisites);
        });

        concernedUv.getCorequisitesUvOf().stream().filter(uv -> !managedCorequisites.contains(uv)).forEach(uv -> {
            realCorequisites.add(uv);
            managedCorequisites.add(uv);
            this.getRealCorequisites(uv, managedCorequisites, realCorequisites);
        });

        return realCorequisites;
    }
     */

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

    public Uv addPrerequisite(Uv prerequisite)
    {
        this.prerequisitesUv.add(prerequisite);

        return this;
    }

    public Option getOption() {
        return option;
    }

    public Uv setOption(Option option) {
        this.option = option;

        return this;
    }
}
