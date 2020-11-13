package com.example.studentrelief.services.model;



import java.util.List;

public class StudentReliefContainer {
    public List<StudentReliefModel> getRecords() {
        return records;
    }

    public void setRecords(List<StudentReliefModel> records) {
        this.records = records;
    }

    List<StudentReliefModel> records;
}
