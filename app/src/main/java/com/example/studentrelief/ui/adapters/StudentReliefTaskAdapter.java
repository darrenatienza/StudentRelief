package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.itemviews.StudentReliefTaskItemView;
import com.example.studentrelief.ui.itemviews.StudentReliefTaskItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.RecyclerViewClickListener;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StudentReliefTaskAdapter extends RecyclerViewAdapterBase<ReliefTaskModel, StudentReliefTaskItemView> {

    @RootContext
    Context context;
    private RecyclerViewClickListener<ReliefTaskModel> mClickListener;

    public void setList(List<ReliefTaskModel> list, RecyclerViewClickListener<ReliefTaskModel> clickListener) {
        mClickListener = clickListener;
        items = list;
    }


    @Override
    protected StudentReliefTaskItemView onCreateItemView(ViewGroup parent, int viewType) {
        return StudentReliefTaskItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StudentReliefTaskItemView> viewHolder, final int position) {
        final StudentReliefTaskItemView view = viewHolder.getView();
        final ReliefTaskModel model = items.get(position);
        Button b = view.findViewById(R.id.btnRequest);
        b.setOnClickListener(_view -> mClickListener.onClick(model));
        view.bind(model);
    }



}