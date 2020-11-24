package com.example.studentrelief.ui.relief_request;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;
import com.example.studentrelief.ui.adapters.StudentReliefAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**Shows the list of donations for every relief request*/
@EActivity(R.layout.fragment_student_relief_list)
public class ReliefRequestDonationListActivity extends AppCompatActivity {

    @Extra
    int reliefRequestID;

    @RestService
    StudentClient studentClient;
    @RestService
    DonationClient donationClient;
    @RestService
    ReliefRequestDonationClient reliefRequestDonationClient;

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
                TextView t = v.findViewById(R.id.idView);
                int id = Integer.parseInt(t.getText().toString());
               validateItemForEdit(id);
            }
        });
    }
    @UiThread
    void showForm(int id) {
        ReliefRequestDonationFormActivity_.intent(this).reliefRequestDonationID(id).reliefRequestID(reliefRequestID).startForResult(SHOW_FORM);
    }
    @Background
    void validateItemForEdit(int id) {
        ReliefRequestDonationModel model = reliefRequestDonationClient.get(id);
        showForm(id);
    }

    @Background
    void loadList(){
        try {
            /** Model is modified to provide values on other fields*/
            List<ReliefRequestDonationModel> models = reliefRequestDonationClient.getAllByID(reliefRequestID).getRecords();
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefRequestDonationModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }
    @Click(R.id.fab)
    void click(View view){
        showForm(0);
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }

}