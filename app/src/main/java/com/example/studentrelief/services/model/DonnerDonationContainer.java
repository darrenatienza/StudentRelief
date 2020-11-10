package com.example.studentrelief.services.model;



import java.util.List;

public class DonnerDonationContainer {
    public List<DonnerDonationModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonnerDonationModel> records) {
        this.records = records;
    }

    List<DonnerDonationModel> records;
}
