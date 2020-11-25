package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.ui.misc.DateFormatter;


import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void bind(DonnerDonationModel model) throws ParseException {
        idView.setText(String.valueOf(model.getDonners_donations_id()));
        donnerFullNameView.setText(model.getDonner_full_name());
        donationNameView.setText("Donation: " + model.getDonation_name());
        donationDateView.setText("Date: " + DateFormatter.convertToSimpleDateString(model.getDonation_date()));
        quantityView.setText("Quantity: " + model.getQuantity());
        quantityView.setTextColor(getResources().getColor(model.isQuantity_uploaded()? R.color.main_green_color :R.color.error));


    }
}