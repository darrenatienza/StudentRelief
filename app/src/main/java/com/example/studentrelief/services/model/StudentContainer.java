package com.example.studentrelief.services.model;

import java.util.List;

/** Response structure of json object*/
public class StudentContainer {
    public List<DonnerModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonnerModel> records) {
        this.records = records;
    }

    List<DonnerModel> records;
}
