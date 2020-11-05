package com.example.studentrelief.services.model;



import java.util.List;

public class DonnerContainer {
    public List<DonnerModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonnerModel> records) {
        this.records = records;
    }

    List<DonnerModel> records;
}
