package com.example.studentrelief.ui.course;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentrelief.services.model.CourseModel;

import java.util.ArrayList;
import java.util.List;


public class CourseViewModel extends ViewModel {


    // TODO: Implement the ViewModel
    private MutableLiveData<List<CourseModel>> courses;


    // contains
    public MutableLiveData<List<CourseModel>> getCourses() {

        if (courses == null) {

            courses = new MutableLiveData<>();
            courses.setValue(new ArrayList<>());
        }
        return courses;
    }






}