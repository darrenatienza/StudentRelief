package com.example.studentrelief.services.model;



import java.util.List;

public class VolunteerContainer {
    public List<VolunteerModel> getRecords() {
        return records;
    }

    public void setRecords(List<VolunteerModel> records) {
        this.records = records;
    }

    List<VolunteerModel> records;
}
