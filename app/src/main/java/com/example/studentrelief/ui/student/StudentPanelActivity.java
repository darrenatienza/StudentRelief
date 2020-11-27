package com.example.studentrelief.ui.student;


import android.util.Log;
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
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.ui.adapters.StudentReliefTaskAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.RecyclerViewClickListener;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
    int id;
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

    @Bean
    StudentReliefTaskAdapter adapter;
    @ViewById
    RecyclerView recyclerView;
    @Pref
    MyPrefs_ myPrefs;
    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            if(id > 0){
                initAuthCookies();
                getFormData();
                loadList();
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
                recyclerView.addItemDecoration((new SimpleDividerItemDecoration(this)));
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                //initItemClick();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


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
       StudentFormActivity_.intent(this).id(id).userID(userID).start();
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

    @Background
    void getFormData() {
        if (id > 0){
            String session = myPrefs.session().get();
            String name = Constants.SESSION_NAME;
            studentClient.setCookie(name,session);
            StudentModel model   = studentClient.get(id);
            updateUIFormData(model);
        }
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
                        _model.setStudent_id(id);
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
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops!")
                .setContentText("An error occured! \n" + message)
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

    @Background
    void loadList(){
        try {

            List<ReliefTaskModel> models = reliefTaskClient.getAllActive().getRecords();
            if(models.toArray().length > 0){
                /** New models (modified model) must be pass not the original models*/
                updateList(models);
            }

        }catch (Exception e){
            Log.e("Error",e.getMessage());
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
        validateItemForReliefRequest(model);
    }
}