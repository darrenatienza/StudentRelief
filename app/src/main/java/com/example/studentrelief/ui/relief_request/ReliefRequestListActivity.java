package com.example.studentrelief.ui.relief_request;

import com.example.studentrelief.services.interfaces.ReliefRequestClient;
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.ui.adapters.ReliefRequestAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

@EActivity(R.layout.activity_relief_request_list)
public class ReliefRequestListActivity extends AppCompatActivity {

    @RestService
    ReliefRequestClient reliefRequestClient;

    @ViewById
    Toolbar toolbar;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    ReliefRequestAdapter adapter;

    @Extra
    int reliefTaskID;


    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
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
                showReliefRequestList(id);
            }
        });
    }

    private void showReliefRequestList(int id) {
        ReliefRequestDonationListActivity_.intent(this).reliefRequestID(id).start();
    }

    @Background
    void loadList(){
        try {
            /** Model is modified to provide values on other fields*/
            List<ReliefRequestModel> models = reliefRequestClient.getAllByID(reliefTaskID).getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefRequestModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }

}