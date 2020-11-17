package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.StudentModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.student_item)
public class StudentItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView tvStudentFullName;
    @ViewById
    TextView tvCourse;
    @ViewById
    TextView tvStudentCampus;
    @ViewById
    TextView addressView;
    @ViewById
    TextView contactNumberView;

    @ViewById
    TextView tvSrCode;

    public StudentItemView(Context context) {
        super(context);
    }

    public void bind(StudentModel model) {
        idView.setText(String.valueOf(model.getStudent_id()));
        tvSrCode.setText(model.getSr_code());
        tvStudentFullName.setText(model.getFull_name());
        tvCourse.setText(model.getCourse());
        addressView.setText(model.getAddress());
        contactNumberView.setText(model.getContact_number());
        tvStudentCampus.setText(model.getCampus());
    }
}