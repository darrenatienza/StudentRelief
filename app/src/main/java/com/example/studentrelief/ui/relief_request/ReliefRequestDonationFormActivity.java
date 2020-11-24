package com.example.studentrelief.ui.relief_request;

import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.ui.dialogs.DatePickerFragment;
import com.github.thunder413.datetimeutils.DateTimeUtils;

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
/**shows the form for updating or adding the donations for every relief request list */
@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_relief_request_donation_form)
public class ReliefRequestDonationFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @RestService
    ReliefRequestDonationClient client;
    @RestService
    StudentClient donnerClient;
    @RestService
    DonationClient donationClient;
    @Extra
    int reliefRequestDonationID;
    @Extra
    int reliefRequestID;

    @ViewById
    Toolbar toolbar;


    @ViewById
    EditText etDate;

    @ViewById
    Spinner spDonation;
    @ViewById
    EditText etQuantity;

    @ViewById
    TextView tvCode;

    private ReliefRequestDonationModel reliefRequestDonationModel;
    private ArrayAdapter<StudentModel> studentModelArrayAdapter;
    private ArrayAdapter<DonationModel> donationArrayAdapter;

    private List<DonationModel> donationModels;
    private List<StudentModel> studentModels;
    private int studentPos;
    private int donationPos;
    private int studentID;
    private int donationID;
    private String requestDate;

    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            loadDonationListAsync();
            if(reliefRequestDonationID > 0){
                getFormData();

            }else{
                reliefRequestDonationModel = new ReliefRequestDonationModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    /** UI Actions */
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String quantity = etQuantity.getText().toString();
            reliefRequestDonationModel.setRelief_request_id(reliefRequestID);
            reliefRequestDonationModel.setDonation_id(donationID);

            reliefRequestDonationModel.setQuantity(Integer.valueOf(quantity));

            saveAsync(reliefRequestDonationModel);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

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
            final int currentQuantity = Integer.parseInt( etQuantity.getText().toString());
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this record!")
                    .setConfirmText("Yes,delete it!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            delete(currentQuantity);
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

    @ItemSelect(R.id.spDonation)
    void spDonationSelect(boolean selected, DonationModel model){
        donationID = model.getDonation_id();
    }

    /** Background Task **/


    @Background
    void loadDonationListAsync() {
        donationModels = donationClient.getAll("").getRecords();
        UpdateDonationSpinnerUI(donationModels);
    }

    @Background
    void getFormData() {
        try {
            if (reliefRequestDonationID > 0) {
                reliefRequestDonationModel = client.get(reliefRequestDonationID);
                updateUIFormData(reliefRequestDonationModel);
            }
        }
                catch(RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
    @Background
    void saveAsync(ReliefRequestDonationModel model){
        int donationID = model.getDonation_id();
        DonationModel donationModel = donationClient.get(donationID);
        int quantity = donationModel.getQuantity() - model.getQuantity();
        donationModel.setQuantity(quantity);
        donationClient.edit(donationID,donationModel);
        if (reliefRequestDonationID > 0){

            client.edit(reliefRequestDonationID,model);

        }else{
            client.addNew(model);
        }
        updateUIAfterSave();

    }
    @Background
    void delete(int currentQuantity) {
        if (reliefRequestDonationID > 0){
            // add the quantity to delete to current donation quantity
            DonationModel donationModel = donationClient.get(donationID);
            int quantity = donationModel.getQuantity() + currentQuantity;
            donationModel.setQuantity(quantity);
            donationClient.edit(donationID,donationModel);
            // delete the donation on relief
            client.delete(reliefRequestDonationID);
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
                android.R.layout.simple_spinner_item,models);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDonation.setAdapter(arrayAdapter);
        // set the default value (pos)
        donationPos = getDonationPosition(donationID);
        spDonation.setSelection(donationPos,true);
    }


    @UiThread
    void updateUIFormData(ReliefRequestDonationModel model) {
        /**TODO
         * Student Name must be text view only
         * Request date must be text view only
         * data for those fields will pull from student table
         * */
        // setting value of selected model from list
        donationID = model.getDonation_id();

        //requestDate =  DateTimeUtils.formatWithPattern(model.getRequest_date(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        //etDate.setText(DateTimeUtils.formatWithStyle(model.getRequest_date(), DateTimeStyle.MEDIUM));
        etQuantity.setText(String.valueOf(model.getQuantity()));
    }

    /** miscellaneous */
    // use for getting position (index) of the id on the list
    private int getStudentPosition(int donner_id) {
        int index = 0;
        for (StudentModel model:studentModels
             ) {
            if(model.getStudent_id() == donner_id)
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
        // required pattern for mysql database
        requestDate = DateTimeUtils.formatWithPattern(mCalender.getTime(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        etDate.setText(selectedDate);
    }
}