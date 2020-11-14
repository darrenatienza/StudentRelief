package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonationTaskModel;
import com.example.studentrelief.ui.itemviews.DonationItemView;
import com.example.studentrelief.ui.itemviews.DonationItemView_;
import com.example.studentrelief.ui.itemviews.DonationTaskItemView;
import com.example.studentrelief.ui.itemviews.DonationTaskItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class DonationTaskAdapter extends RecyclerViewAdapterBase<DonationTaskModel, DonationTaskItemView> {

    @RootContext
    Context context;

    public void setList(List<DonationTaskModel> list) {
        items = list;
    }


    @Override
    protected DonationTaskItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DonationTaskItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DonationTaskItemView> viewHolder, int position) {
        DonationTaskItemView view = viewHolder.getView();
        DonationTaskModel model = items.get(position);

        view.bind(model);
    }



}