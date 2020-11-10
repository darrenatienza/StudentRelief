package com.example.studentrelief.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VolunteerModel {

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    int volunteer_id;
    String  full_name;
    String  address;
    String contact_number;
    String create_time_stamp;



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
    
    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }
}
