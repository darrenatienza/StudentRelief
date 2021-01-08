package com.example.studentrelief.ui.course;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.CourseClient;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.ui.employee.EmployeeListFragment;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

@EFragment
public class CourseListFragment extends Fragment {

    @Pref
    MyPrefs_ myPrefs;

    @RestService
    CourseClient courseClient;
    private CourseListViewModel mViewModel;


    public void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        courseClient.setCookie(name,session);
    }

    public static CourseListFragment newInstance() {
        return new CourseListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @AfterViews
    void afterViews(){
        initAuthCookies();
        initObservers();
        initCoursesData();
    }

    @Background
    void initCoursesData() {
        // prevent reloading if screen configuration changed (portrait to landscape)
        int size = mViewModel.getCourses().getValue().size();
        if(size  == 0){
            List<CourseModel> modelList = courseClient.get().getRecords();
            if(modelList.size() > 0){
                updateCoursesViewModel(modelList);
            }

        }
    }
    @UiThread
    void updateCoursesViewModel(List<CourseModel> modelList) {
        mViewModel.getCourses().setValue(modelList);
    }

    private void initObservers() {
        mViewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        mViewModel.getCourses().observe(getViewLifecycleOwner(), courseModels ->{
            Toast.makeText(getActivity(),String.valueOf(courseModels.size()),Toast.LENGTH_LONG).show();
        } );
    }

    @Click
    void button(){
        Bundle bundle = new Bundle();
        bundle.putInt("courseID", 0);
        NavHostFragment.findNavController(CourseListFragment.this)
                .navigate(R.id.action_fragment_course_list_to_fragment_course_form,bundle);
    }

    @Background
    void reloadCourseData() {
        List<CourseModel> modelList = courseClient.get().getRecords();
        if(modelList.size() > 0){
            updateCoursesViewModel(modelList);
        }
    }

}