package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

public class ReliefRequestModel {


    int relief_request_id;
   int student_id;
   int relief_task_id;
    boolean released;
    String date_release;
    String create_time_stamp;
    String student_full_name;
    String student_address;
    String student_contact_number;
    String student_campus;
    String student_course;
    String request_task_title;
    String donation_requests;
    boolean followup;

    public boolean isFollowup() {
        return followup;
    }

    public void setFollowup(boolean followup) {
        this.followup = followup;
    }

    public String getNext_follow_up_date() {
        return next_follow_up_date;
    }

    public void setNext_follow_up_date(String next_follow_up_date) {
        this.next_follow_up_date = next_follow_up_date;
    }

    String next_follow_up_date;


    public String getDonation_requests() {
        return donation_requests == null ? "" : donation_requests;
    }

    public void setDonation_requests(String donation_requests) {

        this.donation_requests = donation_requests;
    }

    public String getRequest_task_title() {
        return request_task_title;
    }

    public void setRequest_task_title(String request_task_title) {
        this.request_task_title = request_task_title;
    }

    public String getStudent_full_name() {
        return student_full_name;
    }

    public void setStudent_full_name(String student_full_name) {
        this.student_full_name = student_full_name;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getStudent_contact_number() {
        return student_contact_number;
    }

    public void setStudent_contact_number(String student_contact_number) {
        this.student_contact_number = student_contact_number;
    }

    public String getStudent_campus() {
        return student_campus;
    }

    public void setStudent_campus(String student_campus) {
        this.student_campus = student_campus;
    }

    public String getStudent_course() {
        return student_course;
    }

    public void setStudent_course(String student_course) {
        this.student_course = student_course;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }



    public int getRelief_request_id() {
        return relief_request_id;
    }

    public void setRelief_request_id(int relief_request_id) {
        this.relief_request_id = relief_request_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getRelief_task_id() {
        return relief_task_id;
    }

    public void setRelief_task_id(int relief_task_id) {
        this.relief_task_id = relief_task_id;
    }


    public String getDate_release() {
        return date_release;
    }

    public void setDate_release(String date_release) {
        this.date_release = date_release;
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
        return "";
    }
}
