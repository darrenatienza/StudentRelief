package com.example.studentrelief.ui.donner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.ui.adapters.DonnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import static android.widget.Toast.makeText;


@EFragment(R.layout.fragment_donner_list)
public class DonnerListFragment extends Fragment {

    @RestService
    DonnerClient donnerClient;
    @ViewById
    RecyclerView recyclerView;

    @Bean
    DonnerAdapter adapter;

    @AfterViews
    void bindAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @ItemClick(R.id.recyclerView)
    void adapterItemClicked(DonnerModel person) {

    }

}