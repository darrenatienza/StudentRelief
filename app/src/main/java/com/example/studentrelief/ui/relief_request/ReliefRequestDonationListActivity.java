package com.example.studentrelief.ui.relief_request;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;
import com.example.studentrelief.ui.adapters.StudentReliefAdapter;
import com.example.studentrelief.ui.dialogs.DonationQuantityDialogFragment;
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
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**Shows the list of donations for every relief request*/
@EActivity(R.layout.activity_relief_request_donation_list_2)
public class ReliefRequestDonationListActivity extends AppCompatActivity implements DonationQuantityDialogFragment.DonationQuantityDialogFragmentListener {

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
    @ViewById(R.id.tvSearch)
    TextInputEditText tvSearch;
    @ViewById(R.id.ti_l_search)
    TextInputLayout textInputLayoutSearch;
    @Bean
    StudentReliefAdapter adapter;
    @Pref
    MyPrefs_ myPrefs;
    @ViewById
    Toolbar toolbar;
    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        reliefRequestDonationClient.setCookie(name,session);
        donationClient.setCookie(name,session);
        studentClient.setCookie(name,session);
    }
    @AfterViews
    void afterViews() {
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
        initItemClick();
        initSearch();
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
    private void initSearch() {
        textInputLayoutSearch.setEndIconOnClickListener(v ->{
            loadList();
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
            String search = tvSearch.getText().toString();
            List<ReliefRequestDonationModel> models = reliefRequestDonationClient.getAllByID(reliefRequestID,search).getRecords();
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
        DialogFragment d = DonationQuantityDialogFragment.newInstance(reliefRequestID);
        d.show(getSupportFragmentManager(),"donationquanitity");
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }

    @Override
    public void onDialogSaveClick(DialogFragment dialog) {
        loadList();
    }
}