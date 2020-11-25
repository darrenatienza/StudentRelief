package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.ui.itemviews.DonationItemView;
import com.example.studentrelief.ui.itemviews.DonationItemView_;
import com.example.studentrelief.ui.itemviews.DonnerDonationItemView;
import com.example.studentrelief.ui.itemviews.DonnerDonationItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.rest.spring.annotations.RestService;

import java.text.ParseException;
import java.util.List;

@EBean
public class DonnerDonationAdapter extends RecyclerViewAdapterBase<DonnerDonationModel, DonnerDonationItemView> {




    @RootContext
    Context context;

    public void setList(List<DonnerDonationModel> list) {
        items = list;
    }


    @Override
    protected DonnerDonationItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DonnerDonationItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DonnerDonationItemView> viewHolder, int position) {
        DonnerDonationItemView view = viewHolder.getView();
        DonnerDonationModel model = items.get(position);


        try {
            view.bind(model);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



}