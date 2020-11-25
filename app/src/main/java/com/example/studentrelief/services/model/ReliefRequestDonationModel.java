package com.example.studentrelief.services.model;


import java.util.Date;

public class ReliefRequestDonationModel {
    int relief_request_donation_id;
    int relief_request_id;

    public int getRelief_request_id() {
        return relief_request_id;
    }

    public void setRelief_request_id(int relief_request_id) {
        this.relief_request_id = relief_request_id;
    }

    int donation_id;
    String donation_name;
    int quantity;
    String create_time_stamp;

    public int getRelief_request_donation_id() {
        return relief_request_donation_id;
    }

    public void setRelief_request_donation_id(int relief_request_donation_id) {
        this.relief_request_donation_id = relief_request_donation_id;
    }

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public String getDonation_name() {
        return donation_name;
    }

    public void setDonation_name(String donation_name) {
        this.donation_name = donation_name;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
