package com.example.studentrelief.ui.student_relief;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.StudentReliefClient;
import com.example.studentrelief.services.model.StudentReliefModel;
import com.example.studentrelief.ui.adapters.StudentReliefAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@EFragment(R.layout.fragment_student_relief_list)
public class StudentReliefListFragment extends Fragment {

    @RestService
    StudentClient studentClient;
    @RestService
    DonationClient donationClient;
    @RestService
    StudentReliefClient client;

    static  final int SHOW_FORM = 101;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView tvSearch;
    @ViewById
    CheckBox chkRelease;
    @Bean
    StudentReliefAdapter adapter;

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


    @CheckedChange
    void chkRelease(){
        loadList();
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

        StudentReliefFormActivity_.intent(this).extra("id",id).startForResult(SHOW_FORM);
    }
    @Background
    void validateItemForEdit(int id) {
        StudentReliefModel model = client.get(id);
        if(!model.isIs_release()) {
        showFormDialog(id);
        }else{
            showNotApplicableForEditMessage();
        }

    }

    @UiThread
    void showNotApplicableForEditMessage() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops!")
                .setContentText("The relief for this student was released. Your not allow to do any further.")
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
            int release = chkRelease.isChecked()? 1 : 0;
            List<StudentReliefModel> models = client.getAll(release).getRecords();
            List<StudentReliefModel> newModels = new ArrayList<>();
            for (StudentReliefModel model: models
                 ) {
                StudentReliefModel newModel = new StudentReliefModel();
                newModel = model;
                String donationName = donationClient.get(model.getDonation_id()).getName();
                String fullName = studentClient.get(model.getStudent_id()).getFull_name();
                newModel.setDonation_name(donationName);
                newModel.setStudent_fullName(fullName);
                newModels.add(newModel);
            }
            /** New models (modified model) must be pass not the original models from api*/
            updateList(newModels);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<StudentReliefModel> models) {
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