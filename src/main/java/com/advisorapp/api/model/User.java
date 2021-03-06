package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints={@UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = true)
    private Date birthday;

    @Column(name = "remote_id", unique = true)
    private String remoteId;

    @Column(name = "email", nullable = false, unique = true)
    @Email()
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("name ASC")
    private Set<StudyPlan> studyPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("uv ASC")
    private Set<UvUser> uvUsers;

    public User()
    {
        this.studyPlans = new HashSet<StudyPlan>();
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
        studyPlans.forEach(e -> e.setUser(this));

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
