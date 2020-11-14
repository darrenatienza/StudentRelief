package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

public class DonationTaskModel {
    int donation_task_id;
    String code;
    String title;
    String affected_areas;
    String create_time_stamp;
    boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDonation_task_id() {
        return donation_task_id;
    }

    public void setDonation_task_id(int donation_task_id) {
        this.donation_task_id = donation_task_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAffected_areas() {
        return affected_areas;
    }

    public void setAffected_areas(String affected_areas) {
        this.affected_areas = affected_areas;
    }

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    /**Use to view on array adapters*/
    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
