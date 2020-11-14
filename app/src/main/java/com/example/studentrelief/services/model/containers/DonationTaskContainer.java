package com.example.studentrelief.services.model.containers;

import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonationTaskModel;

import java.util.List;

public class DonationTaskContainer {
    List<DonationTaskModel> records;

    public List<DonationTaskModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonationTaskModel> records) {
        this.records = records;
    }
}
