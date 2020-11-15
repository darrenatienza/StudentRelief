package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
public class StudentReliefTaskAdapter extends RecyclerViewAdapterBase<ReliefTaskModel, ReliefTaskItemView> {

    @RootContext
    Context context;
    private RecyclerViewClickListener mClickListener;

    public void setList(List<ReliefTaskModel> list, RecyclerViewClickListener clickListener) {
        mClickListener = clickListener;
        items = list;
    }


    @Override
    protected ReliefTaskItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ReliefTaskItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ReliefTaskItemView> viewHolder, final int position) {
        final ReliefTaskItemView view = viewHolder.getView();
        final ReliefTaskModel model = items.get(position);
        Button b = view.findViewById(R.id.btnRequest);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                mClickListener.onClick(view,model.getRelief_task_id());
            }
        });
        view.bind(model);
    }



}