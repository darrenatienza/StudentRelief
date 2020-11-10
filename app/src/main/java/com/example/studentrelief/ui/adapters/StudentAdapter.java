package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.ui.itemviews.DonnerItemView;
import com.example.studentrelief.ui.itemviews.DonnerItemView_;
import com.example.studentrelief.ui.itemviews.StudentItemView;
import com.example.studentrelief.ui.itemviews.StudentItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StudentAdapter extends RecyclerViewAdapterBase<StudentModel, StudentItemView> {

    @RootContext
    Context context;

    public void setList(List<StudentModel> list) {
        items = list;
    }


    @Override
    protected StudentItemView onCreateItemView(ViewGroup parent, int viewType) {
        return StudentItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StudentItemView> viewHolder, int position) {
        StudentItemView view = viewHolder.getView();
        StudentModel person = items.get(position);

        view.bind(person);
    }



}