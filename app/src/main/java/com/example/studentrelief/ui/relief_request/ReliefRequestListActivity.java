package com.example.studentrelief.ui.relief_request;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefRequestClient;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.adapters.ReliefRequestAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EActivity(R.layout.activity_relief_request_list)
public class ReliefRequestListActivity extends AppCompatActivity {

    @RestService
    ReliefRequestClient reliefRequestClient;
    @RestService
    ReliefTaskClient reliefTaskClient;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.ti_l_search)
    TextInputLayout textInputLayoutSearch;

    @ViewById(R.id.tvSearch)
    TextInputEditText textInputEditTextSearch;

    @ViewById(R.id.textview_relief_task_name)
    TextView textViewReliefTaskName;

    @ViewById(R.id.textview_location)
    TextView textViewLocation;

    @ViewById(R.id.textview_status)
    TextView textViewStatus;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    ReliefRequestAdapter adapter;

    @Extra
    int reliefTaskID;

    @Pref
    MyPrefs_ myPrefs;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        reliefRequestClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){
        initAuthCookies();
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList();
        getReliefRequestData();
        initItemClick();
        initSearch();

    }
    @Background
    void getReliefRequestData() {
        try{
            ReliefTaskModel reliefRequestModel = reliefTaskClient.get(reliefTaskID);
            if(reliefRequestModel != null){
                updateUIReliefTaskPanel(reliefRequestModel);
            }
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }catch (Exception ex){
            showError(ex.getMessage());
        }
    }
    @CheckedChange(R.id.materialRadioButton_active)
    void materialRadioButtonActive(CompoundButton radio, boolean isChecked){
        if(isChecked){
            String search = textInputEditTextSearch.getText().toString();
            loadList(true,search);
        }
    }
    @CheckedChange(R.id.materialRadioButton_not_active)
    void materialRadioButtonNotActive(CompoundButton radio, boolean isChecked){
        if(isChecked){
            String search = textInputEditTextSearch.getText().toString();
            loadList(false,search);
        }
    }
    @CheckedChange(R.id.materialRadioButton_all)
    void materialRadioButtonAll(CompoundButton radio, boolean isChecked){
        if(isChecked){
            loadList();
        }
    }
    @Background
    void loadList(boolean active, String search) {
        try {
            /** Model is modified to provide values on other fields*/
            int activeValue = active ? 1 : 0;
            List<ReliefRequestModel> models = reliefRequestClient.getAll(reliefTaskID,search,activeValue).getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (RestClientException e){
            Log.e("Error",e.getMessage());
        }
    }

    @UiThread
    void updateUIReliefTaskPanel(ReliefTaskModel reliefTaskModel) {

        textViewReliefTaskName.setText(reliefTaskModel.getTitle());
        textViewLocation.setText(reliefTaskModel.getAffected_areas());
        textViewStatus.setText(reliefTaskModel.getStatus());

        int activeColor = getResources().getColor(R.color.status_active);
        int notActiveColor = getResources().getColor(R.color.status_not_active);
        textViewStatus.setTextColor(reliefTaskModel.getActive() ?activeColor:notActiveColor );
    }

    @UiThread
    void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void initSearch() {
        textInputLayoutSearch.setEndIconOnClickListener(v -> {
            loadList();
        });
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
            String search = textInputEditTextSearch.getText().toString();
            List<ReliefRequestModel> models = reliefRequestClient.getAllByID(reliefTaskID,search).getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (RestClientException e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefRequestModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }

}