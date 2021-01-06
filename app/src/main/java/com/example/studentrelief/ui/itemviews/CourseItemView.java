package com.example.studentrelief.ui.itemviews;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.services.model.DonationModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.donation_item)
public class CourseItemView extends RelativeLayout {

    @ViewById
    TextView idView;
    @ViewById
    TextView title;





    public CourseItemView(Context context) {
        super(context);
    }

    public void bind(CourseModel model) {
        idView.setText(String.valueOf(model.getCourse_id()));
        title.setText(model.getTitle());
    }
}