package com.example.studentrelief.ui.donner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.ui.adapters.DonnerAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import static android.widget.Toast.makeText;


@EFragment(R.layout.fragment_donner_list)
public class DonnerListFragment extends Fragment implements DonnerFormFragment.DialogFragmentListener {

    @RestService
    DonnerClient donnerClient;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView tvSearch;
    @Bean
    DonnerAdapter adapter;

    @AfterViews
    void bindAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList();
        initItemClick();
    }

    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                TextView t = v.findViewById(R.id.idView);
                int id = Integer.parseInt(t.getText().toString());
                showFormDialog(id);
            }
        });
    }

    private void showFormDialog(int id) {
        DonnerFormFragment_ formDialog = new DonnerFormFragment_();
        // use to load new list after save or delete on dialog
        formDialog.setParentFragment(this);
        formDialog.setId(id);
        formDialog.show(getFragmentManager(),"dialog");
    }

    @Background
    void loadList(){
        try {
            String criteria = tvSearch.getText().toString();
            List<DonnerModel> donners = donnerClient.getAll(criteria).getRecords();
            updateList(donners);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<DonnerModel> donners) {
        adapter.setDonnerModelList(donners);
        adapter.notifyDataSetChanged();
    }
    @Click(R.id.btnSearch)
    void search(){
        loadList();
    }
    @Click(R.id.fab)
    void click(View view){
            DonnerFormFragment_ formDialog = new DonnerFormFragment_();
            // use to load new list after save or delete on dialog
            formDialog.setParentFragment(this);

            formDialog.show(getFragmentManager(),"dialog");
    }

    // action after save or delete click on dialog form
    @Override
    public void onFinishAction() {
       loadList();
    }
}