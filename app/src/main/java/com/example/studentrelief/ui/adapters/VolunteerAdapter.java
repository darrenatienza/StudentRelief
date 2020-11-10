package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.itemviews.StudentItemView;
import com.example.studentrelief.ui.itemviews.StudentItemView_;
import com.example.studentrelief.ui.itemviews.VolunteerItemView;
import com.example.studentrelief.ui.itemviews.VolunteerItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class VolunteerAdapter extends RecyclerViewAdapterBase<VolunteerModel, VolunteerItemView> {

    @RootContext
    Context context;

    public void setList(List<VolunteerModel> list) {
        items = list;
    }


    @Override
    protected VolunteerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return VolunteerItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<VolunteerItemView> viewHolder, int position) {
        VolunteerItemView view = viewHolder.getView();
        VolunteerModel model = items.get(position);

        view.bind(model);
    }



}