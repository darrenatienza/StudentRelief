package com.example.studentrelief.ui.volunteer;

import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.AddEditDonnerModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrelief.R;
import com.example.studentrelief.services.model.VolunteerModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_volunteer_form)
public class VolunteerFormActivity extends AppCompatActivity {

    @RestService
    VolunteerClient client;

    @Extra
    int id;

    @ViewById
    Toolbar toolbar;


    @ViewById
    TextView etFullName;

    @ViewById
    EditText etContactNumber;
    @ViewById
    EditText etAddress;
    private VolunteerModel model;

    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();

            model.setFull_name(fullName);
            model.setAddress(address);
            model.setContact_number(contactNumber);
            save(model);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    @OptionsItem(R.id.action_delete)
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
    void save(VolunteerModel model){
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
                model = new VolunteerModel();
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
    void updateUIFormData(VolunteerModel model) {
        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());

    }
}