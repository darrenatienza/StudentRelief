package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.EmployeeModel;
import com.example.studentrelief.ui.itemviews.DonationItemView;
import com.example.studentrelief.ui.itemviews.DonationItemView_;
import com.example.studentrelief.ui.itemviews.EmployeeItemView;
import com.example.studentrelief.ui.itemviews.EmployeeItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class EmployeeAdapter extends RecyclerViewAdapterBase<EmployeeModel, EmployeeItemView> {

    @RootContext
    Context context;

    public void setList(List<EmployeeModel> list) {
        items = list;
    }


    @Override
    protected EmployeeItemView onCreateItemView(ViewGroup parent, int viewType) {
        return EmployeeItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<EmployeeItemView> viewHolder, int position) {
        EmployeeItemView view = viewHolder.getView();
        EmployeeModel model = items.get(position);
        view.bind(model);
    }



}