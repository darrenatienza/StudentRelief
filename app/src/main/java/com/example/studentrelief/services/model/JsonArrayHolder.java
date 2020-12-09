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
    public int size(){
        return records.size();
    }

    public boolean isEmpty(){
        return records.size() == 0 ? true : false;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
