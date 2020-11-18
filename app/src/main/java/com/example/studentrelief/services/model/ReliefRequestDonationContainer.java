package com.example.studentrelief.services.model;



import java.util.List;

public class ReliefRequestDonationContainer {
    public List<ReliefRequestDonationModel> getRecords() {
        return records;
    }

    public void setRecords(List<ReliefRequestDonationModel> records) {
        this.records = records;
    }

    List<ReliefRequestDonationModel> records;
}
