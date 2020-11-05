package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonnerModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.donner_item)
public class DonnerItemView extends LinearLayout {

    @ViewById
    TextView fullNameView;



    public DonnerItemView(Context context) {
        super(context);
    }

    public void bind(DonnerModel model) {
        fullNameView.setText(model.getFull_name());

    }
}