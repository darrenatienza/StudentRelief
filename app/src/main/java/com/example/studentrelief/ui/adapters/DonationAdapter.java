package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.itemviews.DonationItemView;
import com.example.studentrelief.ui.itemviews.DonationItemView_;
import com.example.studentrelief.ui.itemviews.VolunteerItemView;
import com.example.studentrelief.ui.itemviews.VolunteerItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class DonationAdapter extends RecyclerViewAdapterBase<DonationModel, DonationItemView> {

    @RootContext
    Context context;

    public void setList(List<DonationModel> list) {
        items = list;
    }


    @Override
    protected DonationItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DonationItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DonationItemView> viewHolder, int position) {
        DonationItemView view = viewHolder.getView();
        DonationModel model = items.get(position);

        view.bind(model);
    }



}