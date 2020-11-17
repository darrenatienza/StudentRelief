package com.example.studentrelief.ui.student_relief_request;

import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.StudentReliefClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.StudentReliefModel;
import com.example.studentrelief.ui.dialogs.DatePickerFragment;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_student_relief_form)
public class StudentReliefFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @RestService
    StudentReliefClient client;
    @RestService
    StudentClient donnerClient;
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
    Spinner spStudent;
    @ViewById
    Spinner spDonation;
    @ViewById
    EditText etQuantity;
    @ViewById
    CheckBox chkIsRelease;
    @ViewById
    TextView tvCode;

    private StudentReliefModel studentReliefModel;
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
            etDate.setEnabled(false);
            loadStudentListAsync();
            loadDonationListAsync();
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                studentReliefModel = new StudentReliefModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    /** UI Actions */
    @Click
    void btnSave(){
        try {
            String quantity = etQuantity.getText().toString();
            boolean isRelease = chkIsRelease.isChecked();
            studentReliefModel.setStudent_id(studentID);
            studentReliefModel.setDonation_id(donationID);
            studentReliefModel.setRequest_date(requestDate);
            studentReliefModel.setIs_release(isRelease);
            studentReliefModel.setQuantity(Integer.valueOf(quantity));

            saveAsync(studentReliefModel);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }




    @Click
    void imgCalendar(){
        DatePickerFragment mDatePickerDialogFragment;
        mDatePickerDialogFragment = new DatePickerFragment();
        mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
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

    @ItemSelect(R.id.spStudent)
    void spStudentSelect(boolean selected, StudentModel model){
        studentID = model.getStudent_id();
    }
    @ItemSelect(R.id.spDonation)
    void spDonationSelect(boolean selected, DonationModel model){
        donationID = model.getDonation_id();
    }

    /** Background Task **/

    @Background
    void loadStudentListAsync() {
        studentModels = donnerClient.getAll("").getRecords();
        UpdateStudentSpinnerUI(studentModels);
    }

    @Background
    void loadDonationListAsync() {
        donationModels = donationClient.getAll("").getRecords();
        UpdateDonationSpinnerUI(donationModels);
    }

    @Background
    void getFormData() {
        if (id > 0){
            studentReliefModel = client.get(id);
            updateUIFormData(studentReliefModel);
        }
    }
    @Background
    void saveAsync(StudentReliefModel model){
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
                android.R.layout.simple_spinner_item,models);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDonation.setAdapter(arrayAdapter);
        // set the default value (pos)
        spDonation.setSelection(donationPos,true);
    }
    @UiThread
    void UpdateStudentSpinnerUI(List<StudentModel> studentModels) {
        studentModelArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,studentModels);
        studentModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStudent.setAdapter(studentModelArrayAdapter);
        // set the default value (pos)
        spStudent.setSelection(studentPos,true);
    }

    @UiThread
    void updateUIFormData(StudentReliefModel model) {
        /**TODO
         * Student Name must be text view only
         * Request date must be text view only
         * data for those fields will pull from student table
         * */
        // setting value of selected model from list
        studentPos = getStudentPosition(model.getStudent_id());
        donationPos = getDonationPosition(model.getDonation_id());
        requestDate =  DateTimeUtils.formatWithPattern(model.getRequest_date(),"YYYY-M-d hh:mm:ss", Locale.ENGLISH);
        etDate.setText(DateTimeUtils.formatWithStyle(model.getRequest_date(), DateTimeStyle.MEDIUM));
        etQuantity.setText(String.valueOf(model.getQuantity()));
        tvCode.setText("Request Code " + model.getCode());
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