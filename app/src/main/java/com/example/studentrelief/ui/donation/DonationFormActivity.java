package com.example.studentrelief.ui.donation;

import android.os.Bundle;

import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_donation_form)
public class DonationFormActivity extends AppCompatActivity {


    @RestService
    DonationClient client;

    @Extra
    int id;

    @ViewById
    Toolbar toolbar;

    @ViewById
    Button btnSave;

    @ViewById
    TextView etName;

    @ViewById
    EditText etQuantity;

    private DonationModel model;

    @Click
    void btnSave(){
        try {
            String fullName = etName.getText().toString();
            String quantity = etQuantity.getText().toString();

            model.setName(fullName);
            model.setQuantity(Integer.valueOf(quantity));
            save(model);

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
    @Background
    void delete() {
        if (id > 0){
            client.delete(id);

        }
        updateUIAfterSave();
    }

    @Background
    void save(DonationModel model){
        if (id > 0){
            client.edit(id,model);
        }else{
            client.addNew(model);
        }
        updateUIAfterSave();

    }
    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();
    }


    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                model = new DonationModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    @Background
    void getFormData() {
        if (id > 0){
            model   = client.get(id);
            updateUIFormData(model);
        }
    }

    @UiThread
    void updateUIFormData(DonationModel model) {
        etName.setText(model.getName());
        etQuantity.setText(String.valueOf(model.getQuantity()));
    }
}