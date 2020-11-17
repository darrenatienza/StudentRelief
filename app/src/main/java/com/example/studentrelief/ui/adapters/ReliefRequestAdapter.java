package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.itemviews.ReliefRequestItemView;
import com.example.studentrelief.ui.itemviews.ReliefRequestItemView_;
import com.example.studentrelief.ui.itemviews.ReliefTaskItemView;
import com.example.studentrelief.ui.itemviews.ReliefTaskItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class ReliefRequestAdapter extends RecyclerViewAdapterBase<ReliefRequestModel, ReliefRequestItemView> {

    @RootContext
    Context context;


    public void setList(List<ReliefRequestModel> list) {
        items = list;
    }


    @Override
    protected ReliefRequestItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ReliefRequestItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ReliefRequestItemView> viewHolder, int position) {
        ReliefRequestItemView view = viewHolder.getView();
        ReliefRequestModel model = items.get(position);
        view.bind(model);
    }


}