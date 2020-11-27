package com.example.studentrelief.services.model;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import org.androidannotations.rest.spring.annotations.Rest;

public class UserModel {
    int user_id;
    String username;
    String password;
    String user_type;
    String full_name;
    int identity_id;
    boolean active;
    String create_time_stamp;

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(int identity_id) {
        this.identity_id = identity_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
