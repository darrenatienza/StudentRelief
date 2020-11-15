package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.itemviews.ReliefTaskItemView;
import com.example.studentrelief.ui.itemviews.ReliefTaskItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.RecyclerViewClickListener;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class ReliefTaskAdapter extends RecyclerViewAdapterBase<ReliefTaskModel, ReliefTaskItemView> {

    @RootContext
    Context context;


    public void setList(List<ReliefTaskModel> list) {
        items = list;
    }


    @Override
    protected ReliefTaskItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ReliefTaskItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ReliefTaskItemView> viewHolder, int position) {
        ReliefTaskItemView view = viewHolder.getView();
        ReliefTaskModel model = items.get(position);

        view.bind(model);
    }


}