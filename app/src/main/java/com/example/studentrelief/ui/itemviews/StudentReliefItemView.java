package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.StudentReliefModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.student_relief_item)
public class StudentReliefItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView fullNameView;
    @ViewById
    TextView donationNameView;
    @ViewById
    TextView requestDateView;
    @ViewById
    TextView quantityView;
    @ViewById
    TextView requestCodeView;
    @ViewById
    TextView releaseDateView;

    public StudentReliefItemView(Context context) {
        super(context);
    }

    public void bind(StudentReliefModel model) {
        idView.setText(String.valueOf(model.getStudent_relief_id()));
        requestCodeView.setText("Request Code: " + model.getCode());
        fullNameView.setText(model.getStudent_fullName());
        donationNameView.setText("Donation: " + model.getDonation_name());
        requestDateView.setText("Request Date: " + model.getRequest_medium_dateFormat());
        releaseDateView.setText("Release Date: " + model.getRelease_medium_dateFormat());
        quantityView.setText("Quantity: " + model.getQuantity());
        if(model.isIs_release()){
            quantityView.setTextColor(getResources().getColor(R.color.main_green_color));
        }else{
            quantityView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

    }
}