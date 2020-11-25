package com.example.studentrelief.ui.relief_task;

import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.DonnerDonationClient;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.dialogs.DatePickerFragment;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_relief_task_form)
public class ReliefTaskFormActivity extends AppCompatActivity{

    @RestService
    ReliefTaskClient client;

    @Extra
    int id;

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etCode;

    @ViewById
    EditText etTitle;

    @ViewById
    EditText etAffectedArea;

    @ViewById
    CheckBox chkActive;


    private ReliefTaskModel reliefTaskModel;


    @AfterViews
    void afterViews(){

        try{

            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                chkActive.setEnabled(false);
                reliefTaskModel = new ReliefTaskModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    /** UI Actions */
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String code = etCode.getText().toString();
            String title = etTitle.getText().toString();
            String affectedArea = etAffectedArea.getText().toString();
            boolean active = chkActive.isChecked();
            reliefTaskModel.setActive(active);
            reliefTaskModel.setAffected_areas(affectedArea);
            reliefTaskModel.setCode(code);
            reliefTaskModel.setTitle(title);
            saveAsync(reliefTaskModel);

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


    /** Background Task **/


    @Background
    void getFormData() {
        if (id > 0){
            reliefTaskModel = client.get(id);
            updateUIFormData(reliefTaskModel);
        }
    }
    @Background
    void saveAsync(ReliefTaskModel model){
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
    void updateUIFormData(ReliefTaskModel model) {
        // setting value of selected model from list
        etAffectedArea.setText(model.getAffected_areas());
        etTitle.setText(model.getTitle());
        etCode.setText(model.getCode());
        chkActive.setChecked(model.getActive());
    }

    /** miscellaneous */


}