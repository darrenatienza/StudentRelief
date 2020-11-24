package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.VolunteerModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.donation_item)
public class DonationItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView nameView;
    @ViewById
    TextView quantityView;




    public DonationItemView(Context context) {
        super(context);
    }

    public void bind(DonationModel model) {
        idView.setText(String.valueOf(model.getDonation_id()));
        nameView.setText(model.getName());
        quantityView.setText("Quantity: " + model.getQuantity());
    }
}