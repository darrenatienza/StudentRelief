package com.example.studentrelief.services.model;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.Date;

public class StudentReliefModel {
    int student_relief_id;
    int donation_id;
    String donation_name;
    int student_id;
    String student_fullName;

    boolean is_release;
    int quantity;
    String date_release;
    String request_date;


    public int getStudent_relief_id() {
        return student_relief_id;
    }

    public void setStudent_relief_id(int student_relief_id) {
        this.student_relief_id = student_relief_id;
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

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_fullName() {
        return student_fullName;
    }

    public void setStudent_fullName(String student_fullName) {
        this.student_fullName = student_fullName;
    }

    public boolean isIs_release() {
        return is_release;
    }

    public void setIs_release(boolean is_release) {
        this.is_release = is_release;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate_release() {
        return date_release;
    }

    public void setDate_release(String date_release) {
        this.date_release = date_release;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }
}
