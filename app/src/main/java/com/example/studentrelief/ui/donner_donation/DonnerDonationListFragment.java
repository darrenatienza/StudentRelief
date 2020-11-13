package com.example.studentrelief.ui.donner_donation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.DonnerDonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.ui.adapters.DonationAdapter;
import com.example.studentrelief.ui.adapters.DonnerDonationAdapter;
import com.example.studentrelief.ui.donation.DonationFormActivity_;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@EFragment(R.layout.fragment_donner_donation_list)
public class DonnerDonationListFragment extends Fragment {

    @RestService
    DonnerClient donnerClient;
    @RestService
    DonationClient donationClient;
    @RestService
    DonnerDonationClient client;

    static  final int SHOW_FORM = 101;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView tvSearch;

    @Bean
    DonnerDonationAdapter adapter;

    @AfterViews
    void afterViews() {
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
               validateItemForEdit(id);
            }
        });
    }
    @UiThread
    void showFormDialog(int id) {

        DonnerDonationFormActivity_.intent(this).extra("id",id).startForResult(SHOW_FORM);
    }
    @Background
    void validateItemForEdit(int id) {
        DonnerDonationModel donnerDonationModel = client.get(id);
        if(!donnerDonationModel.isQuantity_uploaded()) {
        showFormDialog(id);
        }else{
            showNotApplicableForEditMessage();
        }

    }

    @UiThread
    void showNotApplicableForEditMessage() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops!")
                .setContentText("The quantity of this donation was uploaded to donation's record. Your not allow to do any further.")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })

                .show();
    }

    @Background
    void loadList(){
        try {
            /** Model is modified to provide values on other fields*/
            String criteria = tvSearch.getText().toString();
            List<DonnerDonationModel> models = client.getAll(criteria).getRecords();
            List<DonnerDonationModel> newModels = new ArrayList<>();
            for (DonnerDonationModel model: models
                 ) {
                DonnerDonationModel newModel = new DonnerDonationModel();
                newModel = model;
                String donationName = donationClient.get(model.getDonation_id()).getName();
                String donnerFullName = donnerClient.getDonner(model.getDonner_id()).getFull_name();
                newModel.setDonation_name(donationName);
                newModel.setDonner_full_name(donnerFullName);
                newModels.add(newModel);
            }
            /** New models (modified model) must be pass not the original models*/
            updateList(newModels);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<DonnerDonationModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }
    @Click(R.id.btnSearch)
    void search(){
        loadList();
    }
    @Click(R.id.fab)
    void click(View view){
        showFormDialog(0);
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }

}