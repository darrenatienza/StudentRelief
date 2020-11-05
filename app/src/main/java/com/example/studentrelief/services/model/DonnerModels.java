package com.example.studentrelief.services.model;

import com.example.studentrelief.ui.student.MyStudentRecyclerViewAdapter;

import java.util.List;

public class DonnerModels {
    public List<DonnerModel> getRecords() {
        return records;
    }

    public void setRecords(List<DonnerModel> records) {
        this.records = records;
    }

    List<DonnerModel> records;
}
