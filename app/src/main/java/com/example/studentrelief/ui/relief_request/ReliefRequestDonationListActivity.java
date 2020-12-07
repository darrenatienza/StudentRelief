package com.example.studentrelief.ui.relief_request;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.ReliefRequestClient;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.adapters.StudentReliefAdapter;
import com.example.studentrelief.ui.dialogs.DonationQuantityDialogFragment;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
import org.springframework.web.client.RestClientException;

import java.util.List;

/**Shows the list of donations for every relief request*/
@EActivity(R.layout.activity_relief_request_donation_list_2)
public class ReliefRequestDonationListActivity extends AppCompatActivity implements DonationQuantityDialogFragment.DonationQuantityDialogFragmentListener {

    @Extra
    int reliefRequestID;
    @Extra
    int studentID;
    @RestService
    ReliefRequestClient reliefRequestClient;
    @RestService
    StudentClient studentClient;
    @RestService
    VolunteerClient volunteerClient;
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

    @ViewById(R.id.textview_donation_request)
    TextView textViewDonationRequest;

    @ViewById(R.id.checkBox_release)
    CheckBox checkBoxReleased;

    @ViewById
    TextView tvFullName;

    @ViewById
    TextView tvAddress;
    @ViewById
    TextView tvContactNumber;

    @Bean
    StudentReliefAdapter adapter;
    @Pref
    MyPrefs_ myPrefs;
    @ViewById
    Toolbar toolbar;
    private VolunteerModel mVolunteerModel;
    private StudentModel mStudentModel;
    private ReliefRequestModel mReliefRequestModel;
    private int reliefRequestDonationID;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        reliefRequestDonationClient.setCookie(name,session);
        donationClient.setCookie(name,session);
        studentClient.setCookie(name,session);
        volunteerClient.setCookie(name,session);
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
        getPanelStudentData();
        initItemClick();
        initSearch();
    }
    @Background(serial = "sequence1")
    void getPanelStudentData() {
        try {
            mReliefRequestModel = reliefRequestClient.getByReliefRequestID(reliefRequestID).getSingleRecord();
            updateUIPanel();
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }
    }
    @UiThread
    void updateUIPanel() {
        tvFullName.setText(mReliefRequestModel.getStudent_full_name());
        tvAddress.setText(mReliefRequestModel.getStudent_address());
        tvContactNumber.setText(mReliefRequestModel.getStudent_contact_number());
        checkBoxReleased.setChecked(mReliefRequestModel.isReleased());
        textViewDonationRequest.setText(mReliefRequestModel.getDonation_requests());
    }
    @Click(R.id.checkBox_release)
    void materialCheckboxRelease(){
        boolean isChecked = checkBoxReleased.isChecked();
        if(isChecked){
            setReliefRequestActive(true);
        }else setReliefRequestActive(false);
        getPanelStudentData();
    }
    @Background(serial = "sequence1")
    void setReliefRequestActive(boolean released) {
        try{
            mReliefRequestModel.setReleased(released);
            reliefRequestClient.edit(reliefRequestID,mReliefRequestModel);
            String releaseValue = released ? "released" : "not released";
            showSuccess("Relief Request has been " + releaseValue );
        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }
    @UiThread
    void showSuccess(String releaseValue) {
        Toast.makeText(this,releaseValue, Toast.LENGTH_SHORT).show();
    }

    @UiThread()
    void showError(String message) {
        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }


    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            // do it
            TextView t = v.findViewById(R.id.idView);
            reliefRequestDonationID = Integer.parseInt(t.getText().toString());
            openActionDialog();
        });
    }

    private void openActionDialog() {
        String[] actions = {"Delete"};
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title_select_your_action)
                .setItems(actions, (dialogInterface, i) -> {
                    switch (i){
                        case  0:
                            deleteReliefRequestDonation();
                            break;
                    }
                })
                .show();
    }
    @Background
    void deleteReliefRequestDonation() {
        try {
            //use relief request donation id to get the selected record
            ReliefRequestDonationModel reliefRequestDonationModel
                    = reliefRequestDonationClient.get(reliefRequestDonationID);
            // get the donation id
            int donationID = reliefRequestDonationModel.getDonation_id();
            // get the donation from donation id
            DonationModel donationModel = donationClient.get(donationID);
            // get the quantity from relief request donation
            int quantityToAdd = reliefRequestDonationModel.getQuantity();
            // get the quantity from the donation
            int quantityExists = donationModel.getQuantity();
            // delete the record from the relief request donation
            reliefRequestDonationClient.delete(reliefRequestDonationID);
            // set the value of the quantity
            donationModel.setQuantity(quantityToAdd + quantityExists);
            // update the donation
            donationClient.edit(donationID,donationModel);
            updateUIAfterDelete();
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }
    }
    @UiThread
    void updateUIAfterDelete() {
        loadList();
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
            showError(e.getMessage());
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