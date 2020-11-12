package com.example.studentrelief.ui.donner_donation;

import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.DonnerDonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.DonnerModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_donner_donation_form)
public class DonnerDonationFormActivity extends AppCompatActivity {

    @RestService
    DonnerDonationClient client;
    @RestService
    DonnerClient donnerClient;
    @RestService
    DonationClient donationClient;
    @Extra
    int id;

    @ViewById
    Toolbar toolbar;

    @ViewById
    Button btnSave;

    @ViewById
    EditText etDate;

    @ViewById
    Spinner spDonner;
    @ViewById
    Spinner spDonation;
    @ViewById
    EditText etQuantity;

    private DonnerDonationModel model;
    private ArrayAdapter<DonnerModel> donnerArrayAdapter;

    private ArrayAdapter<DonationModel> donationArrayAdapter;
    private List<DonationModel> donationModels;
    private List<DonnerModel> donnerModels;
    private int donnerPos;
    private int donationPos;
    private int donnerID;
    private int donationID;

    @AfterViews
    void afterViews(){

        try{
            loadDonnerListAsync();
            loadDonationListAsync();
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                model = new DonnerDonationModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    /** UI Actions */
    @Click
    void btnSave(){
        try {
            String date = etDate.getText().toString();
            String quantity = etQuantity.getText().toString();
            model.setDonation_id(donationID);
            model.setDonner_id(donnerID);
            model.setDonation_date(date);
            model.setQuantity(Integer.valueOf(quantity));
            saveAsync(model);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    @Click
    void btnDelete(){
        try {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this record!")
                    .setConfirmText("Yes,delete it!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            delete();
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();


        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @ItemSelect(R.id.spDonner)
    void spDonnerSelect(boolean selected, DonnerModel model){
        donnerID = model.getDonner_id();
    }
    @ItemSelect(R.id.spDonation)
    void spDonationSelect(boolean selected, DonationModel model){
        donationID = model.getDonation_id();
    }

    /** Background Task **/

    @Background
    void loadDonnerListAsync() {
        donnerModels = donnerClient.getAll("").getRecords();
        UpdateDonnerSpinnerUI(donnerModels);
    }

    @Background
    void loadDonationListAsync() {
        donationModels = donationClient.getAll("").getRecords();
        UpdateDonationSpinnerUI(donationModels);
    }

    @Background
    void getFormData() {
        if (id > 0){
            model   = client.get(id);

            updateUIFormData(model);
        }
    }
    @Background
    void saveAsync(DonnerDonationModel model){
        if (id > 0){
            client.edit(id,model);
        }else{
            client.addNew(model);
        }
        updateUIAfterSave();

    }
    @Background
    void delete() {
        if (id > 0){
            client.delete(id);
        }
        updateUIAfterSave();
    }

    /** UI threads */
    @UiThread
    void updateUIAfterSave() {
        // close the form
        setResult(RESULT_OK);
        finish();
    }
    @UiThread
    void UpdateDonationSpinnerUI(List<DonationModel> models) {
        ArrayAdapter<DonationModel> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,models);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDonation.setAdapter(arrayAdapter);
        // set the default value (pos)
        spDonation.setSelection(donationPos,true);
    }
    @UiThread
    void UpdateDonnerSpinnerUI(List<DonnerModel> donnerArrayList) {
        donnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,donnerArrayList);
        donnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDonner.setAdapter(donnerArrayAdapter);
        // set the default value (pos)
        spDonner.setSelection(donnerPos,true);
    }

    @UiThread
    void updateUIFormData(DonnerDonationModel model) {
        // setting value of selected model from list
        donnerPos = getDonnerPosition(model.getDonner_id());
        donationPos = getDonationPosition(model.getDonation_id());
        etDate.setText(model.getDonation_date());
        etQuantity.setText(String.valueOf(model.getQuantity()));
    }

    /** miscellaneous */
    // use for getting position (index) of the id on the list
    private int getDonnerPosition(int donner_id) {
        int index = 0;
        for (DonnerModel model:donnerModels
             ) {
            if(model.getDonner_id() == donner_id)
                return  index;
            index++;
        }
        // no list found;
        return 0;
    }

    // use for getting position (index) of the id on the list
    private int getDonationPosition(int donation_id) {
        int index = 0;
        for (DonationModel model:donationModels
        ) {
            if(model.getDonation_id() == donation_id)
                return  index;
            index++;
        }
        // no list found;
        return 0;
    }
}