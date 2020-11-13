package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.StudentReliefModel;
import com.example.studentrelief.ui.itemviews.DonnerDonationItemView;
import com.example.studentrelief.ui.itemviews.DonnerDonationItemView_;
import com.example.studentrelief.ui.itemviews.StudentReliefItemView;
import com.example.studentrelief.ui.itemviews.StudentReliefItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StudentReliefAdapter extends RecyclerViewAdapterBase<StudentReliefModel, StudentReliefItemView> {




    @RootContext
    Context context;

    public void setList(List<StudentReliefModel> list) {
        items = list;
    }


    @Override
    protected StudentReliefItemView onCreateItemView(ViewGroup parent, int viewType) {
        return StudentReliefItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StudentReliefItemView> viewHolder, int position) {
        StudentReliefItemView view = viewHolder.getView();
        StudentReliefModel model = items.get(position);

        view.bind(model);
    }



}