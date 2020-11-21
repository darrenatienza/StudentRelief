package com.example.studentrelief.services.model.containers;



import com.example.studentrelief.services.model.UserModel;

import java.util.List;

public class UserContainer {
    public List<UserModel> getRecords() {
        return records;
    }

    public void setRecords(List<UserModel> records) {
        this.records = records;
    }

    List<UserModel> records;
}
