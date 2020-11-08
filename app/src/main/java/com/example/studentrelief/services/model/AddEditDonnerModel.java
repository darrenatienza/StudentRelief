package com.example.studentrelief.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class AddEditDonnerModel {

    public int getDonner_id() {
        return donner_id;
    }

    @JsonIgnore
    public void setDonner_id(int donner_id) {
        this.donner_id = donner_id;
    }


    int donner_id;
    String  full_name;
    String  address;
    String contact_number;

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }
    @JsonIgnore
    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

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

}
