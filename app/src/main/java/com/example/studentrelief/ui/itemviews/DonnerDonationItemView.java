package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.donner_donation_item)
public class DonnerDonationItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView donnerFullNameView;
    @ViewById
    TextView donationNameView;
    @ViewById
    TextView donationDateView;
    @ViewById
    TextView quantityView;




    public DonnerDonationItemView(Context context) {
        super(context);
    }

    public void bind(DonnerDonationModel model) {
        idView.setText(String.valueOf(model.getDonation_id()));
        donnerFullNameView.setText(model.getDonner_full_name());
        donationNameView.setText("Donation: " + model.getDonation_name());
        donationDateView.setText("Date: " +model.getDonation_date().substring(0,10));
        quantityView.setText("Quantity: " + model.getQuantity());
    }
}