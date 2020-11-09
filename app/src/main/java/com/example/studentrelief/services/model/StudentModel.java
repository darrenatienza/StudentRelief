package com.example.studentrelief.services.model;

public class StudentModel {

    int student_id;
    String sr_code;
    String password;
    Boolean is_requesting_relief;
    String create_time_stamp;

    public String getSr_code() {
        return sr_code;
    }

    public void setSr_code(String sr_code) {
        this.sr_code = sr_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_requesting_relief() {
        return is_requesting_relief;
    }

    public void setIs_requesting_relief(Boolean is_requesting_relief) {
        this.is_requesting_relief = is_requesting_relief;
    }

    public String getCreate_time_stamp() {
        return create_time_stamp;
    }

    public void setCreate_time_stamp(String create_time_stamp) {
        this.create_time_stamp = create_time_stamp;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
