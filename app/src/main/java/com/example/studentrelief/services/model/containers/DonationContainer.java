package com.example.studentrelief.services.model.containers;



import com.example.studentrelief.services.model.DonationModel;

import java.util.List;

public class DonationContainer {
    public List<DonationModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonationModel> records) {
        this.records = records;
    }

    List<DonationModel> records;
}
