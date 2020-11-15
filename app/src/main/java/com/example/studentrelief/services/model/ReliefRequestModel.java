package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

public class ReliefRequestModel {


    int relief_request_id;
   int student_id;
   int relief_task_id;

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    boolean released;
    String date_release;
    String create_time_stamp;

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
