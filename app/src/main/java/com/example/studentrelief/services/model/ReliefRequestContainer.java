package com.example.studentrelief.services.model;

import androidx.annotation.NonNull;

import java.util.List;

public class ReliefRequestContainer {
    public List<ReliefRequestModel> getRecords() {
        return records;
    }

    public void setRecords(List<ReliefRequestModel> records) {
        this.records = records;
    }

    List<ReliefRequestModel> records;
}
