package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.ui.itemviews.CourseItemView;
import com.example.studentrelief.ui.itemviews.CourseItemView_;
import com.example.studentrelief.ui.itemviews.DonationItemView;
import com.example.studentrelief.ui.itemviews.DonationItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class CourseAdapter extends RecyclerViewAdapterBase<CourseModel, CourseItemView> {

    @RootContext
    Context context;

    public void setList(List<CourseModel> list) {
        items = list;
    }


    @Override
    protected CourseItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CourseItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<CourseItemView> viewHolder, int position) {
        CourseItemView view = viewHolder.getView();
        CourseModel model = items.get(position);

        view.bind(model);
    }
}