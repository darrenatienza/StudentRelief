package com.example.studentrelief.ui.donner_donation;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.DonnerDonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.DonnerModel;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_donner_donation_form)
@EActivity(R.layout.activity_donner_donation_form)
public class DonnerDonationFormActivity extends AppCompatActivity{

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
    TextInputEditText etDate;

    //@ViewById
    //Spinner spDonner;
    @ViewById
    AutoCompleteTextView etDonation;
    @ViewById
    AutoCompleteTextView etDonner;
    @ViewById
    TextInputEditText etQuantity;
    @ViewById
    TextInputLayout tiDate;
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
    private MaterialDatePicker<Long> datePicker;
    private long today;
    private long nextMonth;
    private long janThisYear;
    private Pair nextMonthPair;
    private long decThisYear;
    private long oneYearForward;
    private Pair<Long, Long> todayPair;
    private Calendar calendar;
    private String dbDate;

    @AfterViews
    void afterViews(){


        try{
            initSettings();
            initDatePicker();

            loadDonnerListAsync();
            loadDonationListAsync();

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
    private static Calendar getClearedUtc() {
        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC+8"));
        utc.clear();
        return utc;
    }
    private void initSettings() {
        today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar = getClearedUtc();
        calendar.setTimeInMillis(today);
        calendar.roll(Calendar.MONTH, 1);
        nextMonth = calendar.getTimeInMillis();

        calendar.setTimeInMillis(today);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        janThisYear = calendar.getTimeInMillis();
        calendar.setTimeInMillis(today);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        decThisYear = calendar.getTimeInMillis();

        calendar.setTimeInMillis(today);
        calendar.roll(Calendar.YEAR, 1);
        oneYearForward = calendar.getTimeInMillis();

        todayPair = new Pair<>(today, today);
        nextMonthPair = new Pair<>(nextMonth, nextMonth);
    }
    private void initDatePicker() {

        MaterialDatePicker.Builder<Long> builderRange =
                MaterialDatePicker.Builder.datePicker();
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();
        //.....
        builderRange.setCalendarConstraints(constraintsBuilderRange.build());
        datePicker = builderRange.build();
        addDatePickerListener(datePicker);
        addDateEditListener();

    }

    private void addDateEditListener() {
        tiDate.setEndIconOnClickListener(v -> {
            datePicker.show(getSupportFragmentManager(), datePicker.toString());
        });
    }

    private void addDatePickerListener(MaterialDatePicker<Long> datePicker) {
        datePicker.addOnPositiveButtonClickListener(v ->{
            Long date = datePicker.getSelection();
            String dateForView = datePicker.getHeaderText();
            DateFormat df = new SimpleDateFormat("YYYY-M-d hh:mm:ss");
            dbDate = df.format(date);
            etDate.setText(dateForView);
        });
    }


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
                android.R.layout.simple_dropdown_item_1line,donnerArrayList);

        etDonner.setAdapter(donnerArrayAdapter);
        // set the default value (pos)
        etDonner.setSelection(donnerPos);

    }

    @UiThread
    void updateUIFormData(DonnerDonationModel model) {
        // setting value of selected model from list
        donationDate =  DateTimeUtils.formatWithPattern(model.getDonation_date(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        etDate.setText(DateTimeUtils.formatWithStyle(model.getDonation_date(), DateTimeStyle.MEDIUM));
        etQuantity.setText(String.valueOf(model.getQuantity()));
    }

    /** miscellaneous */

}