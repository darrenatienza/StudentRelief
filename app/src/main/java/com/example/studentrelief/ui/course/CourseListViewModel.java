package com.example.studentrelief.ui.course;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentrelief.services.interfaces.CourseClient;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;


public class CourseListViewModel extends ViewModel {

    CourseDataHelper_ courseDataHelper_;
    // TODO: Implement the ViewModel
    private MutableLiveData<List<CourseModel>> courses;

    public MutableLiveData<List<CourseModel>> getCourses() {

        if (courses == null) {

            courses = new MutableLiveData<>();
            courses.setValue(new ArrayList<>());
        }
        return courses;
    }



}