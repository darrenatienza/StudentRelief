package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

public class ReliefTaskModel {
    int relief_task_id;
    String code;
    String title;
    String affected_areas;
    String create_time_stamp;
    int not_released;
    boolean active;

    public int getNot_released() {
        return not_released;
    }

    public void setNot_released(int not_released) {
        this.not_released = not_released;
    }

    public boolean isActive() {
        return active;
    }

    public String getStatus() {

        return active ? "Active":" Not Active";
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRelief_task_id() {
        return relief_task_id;
    }

    public void setRelief_task_id(int relief_task_id) {
        this.relief_task_id = relief_task_id;
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
