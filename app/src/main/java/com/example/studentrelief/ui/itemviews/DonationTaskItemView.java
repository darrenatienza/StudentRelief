package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonationTaskModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_donation_task)
public class DonationTaskItemView extends RelativeLayout {

    @ViewById
    TextView tvID;
    @ViewById
    TextView tvCode;
    @ViewById
    TextView tvTitle;
    @ViewById
    TextView tvAffectedAreas;
    @ViewById
    TextView tvStatus;


    public DonationTaskItemView(Context context) {
        super(context);
    }

    public void bind(DonationTaskModel model) {
        tvID.setText(String.valueOf(model.getDonation_task_id()));
        tvCode.setText(model.getCode());
        tvTitle.setText("Title: " + model.getTitle());
        tvAffectedAreas.setText(model.getAffected_areas());
        String status =  model.getActive() ?"Active" : "Not Active";
        int green = getResources().getColor(R.color.main_green_color);
        int red = getResources().getColor(R.color.colorPrimary);
        // change color depends on status
        tvStatus.setTextColor(model.getActive() ?  red: green);
        tvStatus.setText("Status: " + status);
    }
}