package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.services.model.VolunteerModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.volunteer_item)
public class VolunteerItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView fullNameView;
    @ViewById
    TextView addressView;
    @ViewById
    TextView contactNumberView;

    @ViewById(R.id.img_alert)
    ImageView alertImage;


    public VolunteerItemView(Context context) {
        super(context);
    }

    public void bind(VolunteerModel model) {

            idView.setText(String.valueOf(model.getVolunteer_id()));
            fullNameView.setText(model.getFull_name());
            addressView.setText(model.getAddress());
            contactNumberView.setText(model.getContact_number());
            alertImage.setVisibility(model.isActive() ?INVISIBLE: VISIBLE);
    }
}