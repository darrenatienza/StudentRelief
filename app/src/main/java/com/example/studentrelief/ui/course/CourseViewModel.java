package com.example.studentrelief.ui.course;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentrelief.services.model.CourseModel;

import java.util.ArrayList;
import java.util.List;


public class CourseViewModel extends ViewModel {

    private MutableLiveData<Boolean> reloadCourses;
    // TODO: Implement the ViewModel
    private MutableLiveData<List<CourseModel>> courses;

    // handles loading decisions
    public MutableLiveData<Boolean> willReloadCourses() {

        if (reloadCourses == null) {

            reloadCourses = new MutableLiveData<>();
            reloadCourses.setValue(true);
        }
        return reloadCourses;
    }
    // contains
    public MutableLiveData<List<CourseModel>> getCourses() {

        if (courses == null) {

            courses = new MutableLiveData<>();

        }
        return courses;
    }
    public void setCourses(List<CourseModel> courseList){
        if(reloadCourses.getValue()){
            courses.setValue(courseList);
            reloadCourses.setValue(false);
        }
    }





}