package com.example.studentrelief.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.CourseClient;
import com.example.studentrelief.services.model.CourseModel;
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

@EFragment(R.layout.fragment_course_parent)
public class CourseParentFragment extends Fragment {
    private CourseViewModel mViewModel;
// just empty parent activity


    @AfterViews
    void afterViews(){
        mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
    }
}