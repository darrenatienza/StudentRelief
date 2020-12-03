package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.StudentListModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_student)
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
    @ViewById(R.id.img_alert)
    ImageView alert;
    public StudentItemView(Context context) {
        super(context);
    }

    public void bind(StudentListModel model) {
        idView.setText(String.valueOf(model.getStudent_id()));
        tvSrCode.setText(model.getSr_code());
        tvStudentFullName.setText(model.getFull_name());
        tvCourse.setText(model.getCourse());
        addressView.setText(model.getAddress());
        contactNumberView.setText(model.getContact_number());
        tvStudentCampus.setText(model.getCampus());
        alert.setVisibility(model.isActive() ? INVISIBLE : VISIBLE);
    }
}