package com.advisorapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "uv_user")
public class UvUser {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "uv_id", nullable = false)
    @JsonIgnore
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

    public UvUser setUser(User user) {
        this.user = user;

        return this;
    }

    public Uv getUv() {
        return uv;
    }

    public UvUser setUv(Uv uv) {
        this.uv = uv;

        return this;
    }

    public double getUserAverage() {
        return userAverage;
    }

    public UvUser setUserAverage(double userAverage) {
        this.userAverage = userAverage;

        return this;
    }

    public double getUvMark() {
        return uvMark;
    }

    public UvUser setUvMark(double uvMark) {
        this.uvMark = uvMark;

        return this;
    }

    public String getUvComment() {
        return uvComment;
    }

    public UvUser setUvComment(String uvComment) {
        this.uvComment = uvComment;

        return this;
    }
}
