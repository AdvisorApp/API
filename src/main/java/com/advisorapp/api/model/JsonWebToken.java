package com.advisorapp.api.model;

/**
 * Created by damien on 20/05/2016.
 */
public class JsonWebToken {

    private String token;

    public JsonWebToken() {
    }

    public JsonWebToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
