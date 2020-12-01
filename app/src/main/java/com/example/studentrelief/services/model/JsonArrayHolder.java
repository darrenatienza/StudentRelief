package com.example.studentrelief.services.model;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayHolder<T> {
    protected List<T> records = new ArrayList<T>();

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
