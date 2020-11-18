package com.example.studentrelief.ui.relief_task;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.adapters.ReliefTaskAdapter;
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

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


@EFragment(R.layout.fragment_relief_task_list)
public class ReliefTaskListFragment extends Fragment {

    @RestService
    ReliefTaskClient reliefTaskClient;

    static  final int SHOW_FORM = 101;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView tvSearch;

    @Bean
    ReliefTaskAdapter adapter;

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
                TextView t = v.findViewById(R.id.tvID);
                int id = Integer.parseInt(t.getText().toString());
               validateItemForEdit(id);
            }
        });
    }
    @UiThread
    void showFormDialog(int id) {
        /**TODO update this*/
        ReliefTaskFormActivity_.intent(this).extra("id",id).startForResult(SHOW_FORM);
    }
    @Background
    void validateItemForEdit(int id) {
        ReliefTaskModel reliefTaskModel = reliefTaskClient.get(id);
        if(reliefTaskModel.getActive()) {
        showFormDialog(id);
        }else{
            showNotApplicableForEditMessage();
        }

    }

    @UiThread
    void showNotApplicableForEditMessage() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops!")
                .setContentText("This record is not active. Your not allow to do any further.")
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
            List<ReliefTaskModel> models = reliefTaskClient.getAll(criteria).getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefTaskModel> models) {
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