package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_relief_request_donation)
public class ReliefRequestDonationItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView donationNameView;

    @ViewById
    TextView quantityView;

    public ReliefRequestDonationItemView(Context context) {
        super(context);
    }

    public void bind(ReliefRequestDonationModel model) {
        idView.setText(String.valueOf(model.getRelief_request_donation_id()));
        donationNameView.setText("Donation: " + model.getDonation_name());
        quantityView.setText("Quantity: " + model.getQuantity());
    }
}