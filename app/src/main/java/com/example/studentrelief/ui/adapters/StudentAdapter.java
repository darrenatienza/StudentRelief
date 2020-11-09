package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.ui.itemviews.DonnerItemView;
import com.example.studentrelief.ui.itemviews.DonnerItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StudentAdapter extends RecyclerViewAdapterBase<StudentModel, DonnerItemView> {

    @RootContext
    Context context;

    public void setDonnerModelList(List<DonnerModel> donnerModelList) {
        items = donnerModelList;
    }


    @Override
    protected DonnerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DonnerItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DonnerItemView> viewHolder, int position) {
        DonnerItemView view = viewHolder.getView();
        DonnerModel person = items.get(position);

        view.bind(person);
    }



}