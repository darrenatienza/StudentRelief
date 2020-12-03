package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.EmployeeModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_employee)
public class EmployeeItemView extends RelativeLayout {

    @ViewById(R.id.tv_id)
    TextView tvID;
    @ViewById(R.id.tv_fullname)
    TextView tvFullName;
    @ViewById(R.id.tv_address)
    TextView tvAddress;

    @ViewById(R.id.tv_contactNumber)
    TextView tvContactNumber;

    @ViewById(R.id.tv_position)
    TextView tvPosition;
    @ViewById(R.id.img_alert)
    ImageView imgAlert;

    public EmployeeItemView(Context context) {
        super(context);
    }

    public void bind(EmployeeModel model) {
        tvID.setText(String.valueOf(model.getEmployee_id()));
        tvFullName.setText(model.getFull_name());
        tvContactNumber.setText( model.getContact_number());
        tvPosition.setText(model.getPosition());
        imgAlert.setVisibility(model.isActive() ?INVISIBLE: VISIBLE);
    }
}