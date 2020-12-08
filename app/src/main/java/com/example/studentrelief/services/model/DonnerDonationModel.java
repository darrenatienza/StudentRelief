package com.example.studentrelief.services.model;


public class DonnerDonationModel {
    int donners_donations_id;
    String donation_date;
    int donation_id;
    int donner_id;
    String create_time_stamp;
    int quantity;
    String donner_full_name;
    String donation_name;
    boolean quantity_uploaded;
    public boolean isQuantity_uploaded() {
        return quantity_uploaded;
    }

    public void setQuantity_uploaded(boolean quantity_uploaded) {
        this.quantity_uploaded = quantity_uploaded;
    }

    public int getDonners_donations_id() {
        return donners_donations_id;
    }

    public void setDonners_donations_id(int donners_donations_id) {
        this.donners_donations_id = donners_donations_id;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public int getDonner_id() {
        return donner_id;
    }

    public void setDonner_id(int donner_id) {
        this.donner_id = donner_id;
    }

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDonner_full_name() {
        return donner_full_name;
    }

    public void setDonner_full_name(String donner_full_name) {
        this.donner_full_name = donner_full_name;
    }

    public String getDonation_name() {
        return donation_name;
    }

    public void setDonation_name(String donation_name) {
        this.donation_name = donation_name;
    }
}
