package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "uv_user")
public class UvUser {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "uv_id", nullable = false)
    @JsonBackReference
    private Uv uv;

    @Column(name = "user_average")
    private double userAverage;

    @Column(name = "uv_mark")
    private double uvMark;

    @Column(name = "uv_comment")
    private String uvComment;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Uv getUv() {
        return uv;
    }

    public void setUv(Uv uv) {
        this.uv = uv;
    }

    public double getUserAverage() {
        return userAverage;
    }

    public void setUserAverage(double userAverage) {
        this.userAverage = userAverage;
    }

    public double getUvMark() {
        return uvMark;
    }

    public void setUvMark(double uvMark) {
        this.uvMark = uvMark;
    }

    public String getUvComment() {
        return uvComment;
    }

    public void setUvComment(String uvComment) {
        this.uvComment = uvComment;
    }
}
