package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonnerModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.donner_item)
public class DonnerItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView fullNameView;
    @ViewById
    TextView addressView;
    @ViewById
    TextView contactNumberView;



    public DonnerItemView(Context context) {
        super(context);
    }

    public void bind(DonnerModel model) {
        idView.setText(String.valueOf(model.getDonner_id()));
        fullNameView.setText(model.getFull_name());
        addressView.setText(model.getAddress());
        contactNumberView.setText(model.getContact_number());
    }
}