package com.example.studentrelief.ui.student;


import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefRequestClient;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.JsonArrayHolder;
import com.example.studentrelief.services.model.ReliefRequestCountModel;
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.ui.adapters.StudentReliefTaskAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.RecyclerViewClickListener;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.studentrelief.ui.student.StudentListFragment.SHOW_FORM;

@OptionsMenu(R.menu.menu_panel)
@EActivity(R.layout.activity_student_panel)
public class StudentPanelActivity extends AppCompatActivity implements RecyclerViewClickListener<ReliefTaskModel> {

    @RestService
    StudentClient studentClient;
    @RestService
    ReliefTaskClient reliefTaskClient;
    @RestService
    ReliefRequestClient reliefRequestClient;
    @RestService
    UserClient userClient;

    @Extra
    int userID;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView tvSrCode;

    @ViewById
    TextView tvStudentFullName;
    @ViewById
    TextView tvAddress;
    @ViewById
    TextView tvContactNumber;
    @ViewById
    TextView tvStudentCampus;
    @ViewById(R.id.textView_pending_relief_request)
    TextView textViewPendingReliefRequest;
    @Bean
    StudentReliefTaskAdapter adapter;
    @ViewById
    RecyclerView recyclerView;
    @Pref
    MyPrefs_ myPrefs;
    private int studentID;
    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            initAuthCookies();

            textViewPendingReliefRequest.setVisibility(View.INVISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
            recyclerView.addItemDecoration((new SimpleDividerItemDecoration(this)));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            getFormData();
            checkForPendingReliefRequest();

            //initItemClick();
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }
    @Background(serial = "sequence1")
    void checkForPendingReliefRequest() {
        try{

            JsonArrayHolder<ReliefRequestCountModel> result = reliefRequestClient.getReliefRequestCount(studentID, 0);
            if(result.size() > 0){

                updateUIAfterCheckingPendingReliefRequest();
            }
            else {
                // no pending relief request
                loadList();
            }


        }catch (RestClientException ex){
            showErrorAlert(ex.getMessage());
        }
    }
    @UiThread
    void updateUIAfterCheckingPendingReliefRequest() {
        textViewPendingReliefRequest.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        reliefTaskClient.setCookie(name,session);
        studentClient.setCookie(name,session);
        reliefRequestClient.setCookie(name,session);
        userClient.setCookie(name,session);
    }

    //handles click menu
    @OptionsItem(R.id.action_edit)
    void menuPanel(){
       StudentFormActivity_.intent(this).id(studentID).userID(userID).startForResult(SHOW_FORM);
    }
    @OptionsItem(R.id.action_logout)
    void menuLogout(){
        logOutCurrentUser();
    }
    @Background
    void logOutCurrentUser() {
        try {
            userClient.logout();
            updateUIAfterLogout();
        }catch (RestClientException ex){
            showErrorAlert(ex.getMessage());
        }catch (Exception ex) {
            showErrorAlert(ex.toString());
        }

    }
    @UiThread
     void updateUIAfterLogout() {
        finish();
    }

    @Background(serial = "sequence1")
    void getFormData() {
        try{
            if (userID > 0){

                StudentModel model   = studentClient.getByUserID(userID).getSingleRecord();
                studentID = model.getStudent_id();
                updateUIFormData(model);
            }
        }catch (RestClientException e){
            showErrorAlert(e.getMessage());
        }catch (Exception ex){
            showErrorAlert(ex.getMessage());
        }

    }
    @OptionsItem(R.id.action_change_password)
    void changePassword(){
        final View layout = getLayoutInflater().inflate(R.layout.dialog_password,null);
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.dialog_title_password_reset))
                .setMessage(getString(R.string.dialog_message_password_reset))
                .setView(layout)
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    TextInputEditText pass = layout.findViewById(R.id.passworView);
                    String newPass = pass.getText().toString();
                    updatePasswordAsync(newPass);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }
    @Background
    void updatePasswordAsync(String newPass) {
        try {
            UserModel userModel = userClient.get(userID);
            userModel.setPassword(newPass);
            userClient.edit(userID,userModel);
            updateUIAfterPasswordUpdate();
        }catch (RestClientException ex){
            showErrorAlert(ex.getMessage());
        }catch (Exception ex){
            showErrorAlert(ex.getMessage());
        }
    }
    @UiThread
    void updateUIAfterPasswordUpdate() {
        Toast.makeText(this,"Password has been changed!",Toast.LENGTH_SHORT).show();
    }

    void validateItemForReliefRequest(final ReliefTaskModel model) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You must be a resident of the "+ model.getAffected_areas() +
                        " to be qualify for receiving relief.")
                .setConfirmText("Yes,I am qualify.")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        ReliefRequestModel _model = new ReliefRequestModel();
                        _model.setStudent_id(studentID);
                        _model.setRelief_task_id(model.getRelief_task_id());
                        _model.setReleased(false);
                        addNewReliefRequest(_model);
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
    void showAddressConfirmation(final ReliefTaskModel model){
        int reliefTaskID = model.getRelief_task_id();
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.dialog_title_address_confirmation))
                .setMessage("You must be a resident one of the following affected areas. \n \n"
                        + model.getAffected_areas()).
                setPositiveButton("Yes", (dialog, which) -> {
                    validateItemForReliefRequest2(reliefTaskID);
        })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    void validateItemForReliefRequest2(int reliefTaskID) {
        ArrayList<String> donationRequestList = new ArrayList<>();

        String[] multiItems = {"Food","Dress","Temporary Shelter"};
        boolean[] checkedItems = {false,false,false};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Please indicate the things you" +
                        "mostly need.")
                .setMultiChoiceItems(multiItems, checkedItems, (dialog, which, isChecked) -> {
                    String item = multiItems[which];
                    if(isChecked){
                        donationRequestList.add(item);
                    }else{
                        donationRequestList.remove(item);
                    }
                }).setPositiveButton("Ok", (dialog, which) -> {
            String donationRequests = "";
            for (String item : donationRequestList
            ) {
                if(donationRequests.isEmpty()){
                    donationRequests = item;
                }else{
                    donationRequests += "," + item;
                }
            }
            ReliefRequestModel _model = new ReliefRequestModel();
            _model.setStudent_id(studentID);
            _model.setRelief_task_id(reliefTaskID);
            _model.setReleased(false);
            _model.setDonation_requests(donationRequests);
            addNewReliefRequest(_model);
            dialog.dismiss();
        })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Background
    void addNewReliefRequest(ReliefRequestModel model) {
        try {
            reliefRequestClient.addNew(model);
            showReliefRequestSaveConfirmation();
        }catch (RestClientException ex){
            showErrorAlert(ex.getMessage());
        }

    }
    @UiThread
    void showErrorAlert(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }


    @UiThread
    void showReliefRequestSaveConfirmation() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success")
                .setContentText("Your new relief request has been submitted!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })

                .show();
    }


    @UiThread
    void updateUIFormData(StudentModel model) {

        tvSrCode.setText(model.getSr_code());
        tvStudentFullName.setText(model.getFull_name());
        tvAddress.setText(model.getAddress());
        tvContactNumber.setText(model.getContact_number());
        tvStudentCampus.setText(model.getCampus());

    }

    @Background(serial = "sequence1")
    void loadList(){
        try {

            List<ReliefTaskModel> models = reliefTaskClient.getAllActive(1).getRecords();
            if(models.toArray().length > 0){
                /** New models (modified model) must be pass not the original models*/
                updateList(models);
            }

        }catch (RestClientException e){
           showErrorAlert(e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefTaskModel> models) {
        adapter.setList(models,this);
        adapter.notifyDataSetChanged();
    }


    /**Use to get on click event of button from recycler view*/
    @Override
    public void onClick(ReliefTaskModel model) {
        showAddressConfirmation(model);
    }



    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        getFormData();
        Log.d("Result",String.valueOf(resultCode));
    }
}