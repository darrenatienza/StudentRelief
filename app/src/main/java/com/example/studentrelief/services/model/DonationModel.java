package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

public class DonationModel {
    int donation_id;
    String name;
    int quantity;
    String create_time_stamp;
    int priority_index;

    public int getPriority_index() {
        return priority_index;
    }

    public void setPriority_index(int priority_index) {
        this.priority_index = priority_index;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
