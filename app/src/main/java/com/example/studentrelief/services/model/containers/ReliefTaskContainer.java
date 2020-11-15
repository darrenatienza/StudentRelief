package com.example.studentrelief.services.model.containers;

import com.example.studentrelief.services.model.ReliefTaskModel;

import java.util.List;

public class ReliefTaskContainer {
    List<ReliefTaskModel> records;

    public List<ReliefTaskModel> getRecords() {
        return records;
    }

    public void setRecords(List<ReliefTaskModel> records) {
        this.records = records;
    }
}
