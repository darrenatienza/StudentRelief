package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.ReliefRequestModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_student_relief_request)
public class ReliefRequestItemView extends RelativeLayout {

    @ViewById
    TextView tvID;
    @ViewById
    TextView tvStudentFullName;
    @ViewById
    TextView tvStudentAddress;
    @ViewById
    TextView tvStudentContactNumber;
    @ViewById
    TextView tvStudentCampus;
    @ViewById
    TextView tvStudentCourse;
    @ViewById
    TextView tvStatus;
    @ViewById
    TextView tv_isFollowUp;
    public ReliefRequestItemView(Context context) {
        super(context);
    }

    public void bind(ReliefRequestModel model) {
        tvID.setText(String.valueOf(model.getRelief_request_id()));
        tvStudentFullName.setText(model.getStudent_full_name());
        tvStudentAddress.setText(model.getStudent_address());
        tvStudentContactNumber.setText(model.getStudent_contact_number());
        tvStudentCampus.setText(model.getStudent_campus());
        tvStudentCourse.setText( model.getStudent_course());
        tvStatus.setText(model.isReleased()? "Released" : "Not yet release");
        int green = getResources().getColor(R.color.main_green_color);
        int red = getResources().getColor(R.color.error);
        tvStatus.setTextColor(model.isReleased()? green : red);
        tv_isFollowUp.setText("Followed Up: " + (model.isFollowup()? "Yes" : "No"));
        tv_isFollowUp.setTextColor(red);
        tv_isFollowUp.setVisibility(model.isFollowup()? VISIBLE : GONE);

    }
}