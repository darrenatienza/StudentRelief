package com.example.studentrelief.services.model;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

public class EmployeeModel {
    int employee_id;
    String full_name;
    String address;
    String contact_number;
    String position;
    boolean active;
    int user_id;
    String create_time_stamp;

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    private void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    private void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
