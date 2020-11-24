package com.example.studentrelief.ui.donner_donation;

import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.DonnerDonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.DonnerModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentrelief.R;

import com.example.studentrelief.ui.dialogs.DatePickerFragment;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.android.material.textfield.TextInputLayout;


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
@OptionsMenu(R.menu.menu_donner_donation_form)
@EActivity(R.layout.activity_donner_donation_form)
public class DonnerDonationFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @RestService
    DonnerDonationClient client;
    @RestService
    DonnerClient donnerClient;
    @RestService
    DonationClient donationClient;
    @Extra
    int id;

    @ViewById
    TextInputLayout tiDonner;
    @ViewById
    Toolbar toolbar;


    @ViewById
    EditText etDate;

    //@ViewById
    //Spinner spDonner;
    @ViewById
    AutoCompleteTextView etDonation;
    @ViewById
    EditText etQuantity;

    private DonnerDonationModel donnerDonationModel;
    private ArrayAdapter<DonnerModel> donnerArrayAdapter;

    private ArrayAdapter<DonationModel> donationArrayAdapter;
    private List<DonationModel> donationModels;
    private List<DonnerModel> donnerModels;
    private int donnerPos;
    private int donationPos;
    private int donnerID;
    private int donationID;
    private String donationDate;

    @AfterViews
    void afterViews(){


        try{
            etDate.setEnabled(false);

            //loadDonnerListAsync();
            loadDonationListAsync();
            loadTest();
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                donnerDonationModel = new DonnerDonationModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    private void loadTest() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        etDonation.setAdapter(adapter);

    }
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
    /** UI Actions */
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String quantity = etQuantity.getText().toString();
            donnerDonationModel.setDonation_id(donationID);
            donnerDonationModel.setDonner_id(donnerID);
            donnerDonationModel.setDonation_date(donationDate);
            donnerDonationModel.setQuantity_uploaded(true);
            donnerDonationModel.setQuantity(Integer.valueOf(quantity));
            saveAsync(donnerDonationModel);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @OptionsItem(R.id.action_upload)
    void uploadQuantity(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to modify or remove this record after uploading the quantity.")
                .setConfirmText("Yes,upload it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        uploadQuantityAsync();
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
    }



    @Click(R.id.etDate)
    void imgCalendar(){
        DatePickerFragment mDatePickerDialogFragment;
        mDatePickerDialogFragment = new DatePickerFragment();
        mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
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

    /**@ItemSelect(R.id.spDonner)
    void spDonnerSelect(boolean selected, DonnerModel model){
        donnerID = model.getDonner_id();
    }
    @ItemSelect(R.id.spDonation)
    void spDonationSelect(boolean selected, DonationModel model){
        donationID = model.getDonation_id();
    }*/

    /** Background Task **/
    @Background
    void uploadQuantityAsync() {
        // updates the quantity of the donation
        DonationModel donationModel = donationClient.get(donnerDonationModel.getDonation_id());
        donationModel.setQuantity(donationModel.getQuantity() + donnerDonationModel.getQuantity());
        // updates the quantity uploaded property of donners donations;
        donnerDonationModel.setQuantity_uploaded(true);
        // commit changes
        client.edit(id, donnerDonationModel);
        donationClient.edit(donationModel.getDonation_id(),donationModel);

        updateUIAfterUploadQuantity();
    }
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
            donnerDonationModel = client.get(id);
            updateUIFormData(donnerDonationModel);
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
    void updateUIAfterUploadQuantity() {
        // close the form
        setResult(RESULT_OK);
        finish();
    }
    @UiThread
    void updateUIAfterSave() {
        // close the form
        setResult(RESULT_OK);
        finish();
    }
    @UiThread
    void UpdateDonationSpinnerUI(List<DonationModel> models) {
        ArrayAdapter<DonationModel> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,models);
        etDonation.setAdapter(arrayAdapter);
        // set the default value (pos)
        etDonation.setSelection(donationPos);
    }
    @UiThread
    void UpdateDonnerSpinnerUI(List<DonnerModel> donnerArrayList) {
        donnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,donnerArrayList);
        donnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spDonner.setAdapter(donnerArrayAdapter);
        // set the default value (pos)
        //spDonner.setSelection(donnerPos,true);
    }

    @UiThread
    void updateUIFormData(DonnerDonationModel model) {
        // setting value of selected model from list
        donnerPos = getDonnerPosition(model.getDonner_id());
        donationPos = getDonationPosition(model.getDonation_id());
        donationDate =  DateTimeUtils.formatWithPattern(model.getDonation_date(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        etDate.setText(DateTimeUtils.formatWithStyle(model.getDonation_date(), DateTimeStyle.MEDIUM));
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


    /** method from DatePickerDialog */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalender = Calendar.getInstance();
        mCalender.set(Calendar.YEAR, year);
        mCalender.set(Calendar.MONTH, month);
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(mCalender.getTime());
        donationDate = DateTimeUtils.formatWithPattern(mCalender.getTime(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        etDate.setText(selectedDate);
    }
}