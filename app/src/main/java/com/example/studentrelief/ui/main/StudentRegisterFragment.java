package com.example.studentrelief.ui.main;

import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_student_register)
public class StudentRegisterFragment extends Fragment {


    @ViewById(R.id.section_label)
    TextView sectionLabel;

    @AfterViews
    void afterViews(){

    }

}