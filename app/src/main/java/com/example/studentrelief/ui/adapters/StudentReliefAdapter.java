package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.ReliefRequestDonationModel;
import com.example.studentrelief.ui.itemviews.ReliefRequestDonationItemView;
import com.example.studentrelief.ui.itemviews.ReliefRequestDonationItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StudentReliefAdapter extends RecyclerViewAdapterBase<ReliefRequestDonationModel, ReliefRequestDonationItemView> {




    @RootContext
    Context context;

    public void setList(List<ReliefRequestDonationModel> list) {
        items = list;
    }


    @Override
    protected ReliefRequestDonationItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ReliefRequestDonationItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ReliefRequestDonationItemView> viewHolder, int position) {
        ReliefRequestDonationItemView view = viewHolder.getView();
        ReliefRequestDonationModel model = items.get(position);

        view.bind(model);
    }



}