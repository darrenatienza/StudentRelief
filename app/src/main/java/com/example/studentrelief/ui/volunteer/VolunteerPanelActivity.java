package com.example.studentrelief.ui.volunteer;

import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.adapters.ReliefTaskAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
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
import com.example.studentrelief.ui.relief_request.ReliefRequestListActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;
@OptionsMenu(R.menu.menu_panel)
@EActivity(R.layout.activity_volunteer_panel)
public class VolunteerPanelActivity extends AppCompatActivity {
    @RestService
    ReliefTaskClient reliefTaskClient;

    @ViewById
    Toolbar toolbar;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    ReliefTaskAdapter adapter;
    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
               showReliefRequestList(id);
            }
        });
    }

    private void showReliefRequestList(int id) {
        ReliefRequestListActivity_.intent(this).reliefTaskID(id).start();
    }

    @Background
    void loadList(){
        try {
            /** Model is modified to provide values on other fields*/
            List<ReliefTaskModel> models = reliefTaskClient.getAllActive().getRecords();

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

}