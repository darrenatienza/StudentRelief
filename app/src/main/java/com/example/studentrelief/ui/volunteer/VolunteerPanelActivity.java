package com.example.studentrelief.ui.volunteer;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.adapters.ReliefTaskAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.example.studentrelief.ui.relief_request.ReliefRequestListActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
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

import java.util.List;

@OptionsMenu(R.menu.menu_panel)
@EActivity(R.layout.activity_volunteer_panel)
public class VolunteerPanelActivity extends AppCompatActivity {
    private static final int RELOAD_LIST = 101;
    @RestService
    ReliefTaskClient reliefTaskClient;
    @RestService
    UserClient userClient;
    @RestService
    VolunteerClient volunteerClient;

    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView tvFullName;
    @ViewById
    TextView tvAddress;
    @ViewById
    TextView tvContactNumber;

    @Extra
    int id = 1;

    @Extra
    int userID = 3;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    ReliefTaskAdapter adapter;

    @Pref
    MyPrefs_ myPrefs;
    private String mUserType;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        reliefTaskClient.setCookie(name,session);
        volunteerClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){


        /**Todo: Add password change and search filter */
        if(userID > 0){
            initAuthCookies();
            setSupportActionBar(toolbar);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
            recyclerView.addItemDecoration((new SimpleDividerItemDecoration(this)));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            getUserData();
            getVolunteerFormData();
            loadAllList();
            initItemClick();
        }else{
            //Todo: show message that no volunteer has been selected
            // then close the panel activity
        }
    }

    @OptionsItem(R.id.action_change_password)
    void changePassword(){
        
    }
    @Background
    void getUserData() {
        try{
            UserModel userModel = userClient.get(userID);
            mUserType = userModel.getUser_type();
        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }

    @Background
    void loadAllList() {
        try {
            /** Model is modified to provide values on other fields*/
            List<ReliefTaskModel> models = reliefTaskClient.getAll("").getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }

    @CheckedChange(R.id.materialRadioButton_active)
    void materialRadioButtonActive(CompoundButton radio, boolean isChecked){
        if(isChecked){

            loadList(true);
        }
    }
    @CheckedChange(R.id.materialRadioButton_not_active)
    void materialRadioButtonNotActive(CompoundButton radio, boolean isChecked){
        if(isChecked){
            loadList(false);
        }
    }
    @CheckedChange(R.id.materialRadioButton_all)
    void materialRadioButtonAll(CompoundButton radio, boolean isChecked){
        if(isChecked){
            loadAllList();
        }
    }
    @Background
    void getVolunteerFormData() {
        try{
            if (userID > 0){
                VolunteerModel model   = volunteerClient.getByUserID(userID).getSingleRecord();
                updateUIFormData(model);
            }
        }catch (RestClientException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @UiThread
    void updateUIFormData(VolunteerModel model) {
        tvFullName.setText(model.getFull_name());
        tvAddress.setText(model.getAddress());
        tvContactNumber.setText(model.getContact_number());
    }

    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                TextView t = v.findViewById(R.id.tvID);
                int id = Integer.parseInt(t.getText().toString());
               showReliefRequestList(id);
            }
        });
    }

    private void showReliefRequestList(int id) {
        ReliefRequestListActivity_.intent(this).reliefTaskID(id).startForResult(RELOAD_LIST);
    }

    @Background
    void loadList(boolean active){
        try {
            /** Model is modified to provide values on other fields*/
            int activeValue = active ? 1 :0;
            List<ReliefTaskModel> models = reliefTaskClient.getAllActive(activeValue).getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (RestClientException e){
           showError(e.getMessage());
        }
    }
    @UiThread
    void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @UiThread
    void updateList(List<ReliefTaskModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }
    @OptionsItem(R.id.action_edit)
    void menuPanel(){
        VolunteerFormActivity_.intent(this).volunteerID(id).userType(mUserType).start();
    }
    @OnActivityResult(RELOAD_LIST)
    void onResult(int resultCode) {
        loadAllList();
        Log.d("Result",String.valueOf(resultCode));
    }
}