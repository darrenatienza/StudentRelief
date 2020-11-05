package com.example.studentrelief.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.ui.donner.DonnerListFragment_;
import com.example.studentrelief.ui.itemviews.DonnerItemView;
import com.example.studentrelief.ui.itemviews.DonnerItemView_;
import com.example.studentrelief.ui.misc.RecyclerViewAdapterBase;
import com.example.studentrelief.ui.misc.ViewWrapper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class DonnerAdapter extends RecyclerViewAdapterBase<DonnerModel, DonnerItemView> {

    @RootContext
    Context context;
    public List<DonnerModel> donnerModelList = new ArrayList<>();
    @Override
    protected DonnerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DonnerItemView_.build(context);
    }
    OnClickI
    @Override
    public void onBindViewHolder(ViewWrapper<DonnerItemView> viewHolder, int position) {
        DonnerItemView view = viewHolder.getView();
        DonnerModel person = items.get(position);

        view.bind(person);
    }
    @AfterInject
    void initAdapter() {
        DonnerModel d = new DonnerModel();
        d.setFull_name("asdf");
        items.add(d);
        items.add(d);
        items.add(d);
        items.add(d);
        items.add(d);
        items.add(d);
        items.add(d);
        items.add(d);
    }


}