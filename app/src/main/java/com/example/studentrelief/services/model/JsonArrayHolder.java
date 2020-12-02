package com.example.studentrelief.services.model;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayHolder<T> {
    protected List<T> records = new ArrayList<T>();

    public List<T> getRecords() {
        return records;
    }

    public T getSingleRecord(){
        return records.get(0);
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
