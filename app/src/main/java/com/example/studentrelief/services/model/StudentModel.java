package com.example.studentrelief.services.model;

public class StudentModel {

    int student_id;
    String sr_code;
    String full_name;
    String course;
    String address;
    String contact_number;

    public String getIs_requesting_releif() {
        return is_requesting_releif;
    }

    public void setIs_requesting_releif(String is_requesting_releif) {
        this.is_requesting_releif = is_requesting_releif;
    }

    String is_requesting_releif;
    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    String campus;
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