package com.example.studentrelief.services.model;

import java.util.List;

/** Response structure of json object*/
public class StudentContainer {
    public List<StudentModel> getRecords() {
        return records;
    }

    public void setRecords(List<StudentModel> records) {
        this.records = records;
    }

    List<StudentModel> records;
}
