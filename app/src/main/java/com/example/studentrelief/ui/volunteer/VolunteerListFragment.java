package com.example.studentrelief.ui.volunteer;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.adapters.VolunteerAdapter;

import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;


@EFragment(R.layout.fragment_volunteer_list)
public class VolunteerListFragment extends Fragment {

    static  final int SHOW_FORM = 101;
    @RestService
    VolunteerClient client;
    @RestService
    UserClient userClient;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextInputEditText etSearch;
    @ViewById
    TextInputLayout tiSearch;
    @Bean
    VolunteerAdapter adapter;

    @Pref
    MyPrefs_ myPrefs;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        client.setCookie(name,session);
    }
    @AfterViews
    void afterViews() {
        initAuthCookies();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList();
        initItemClick();
        initSearch();
    }

    private void initSearch() {
        tiSearch.setEndIconOnClickListener(v ->{
            loadList();
        });
    }


    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                TextView t = v.findViewById(R.id.idView);
               int volunteerID = Integer.parseInt(t.getText().toString());
                //showFormDialog(id);
                checkUserStatus(volunteerID);
            }
        });
    }
    @Background
    void checkUserStatus(int volunteerID) {
        try {
            VolunteerModel volunteerModel = client.get(volunteerID);
            if(volunteerModel != null){
                int userID = volunteerModel.getUser_id();
                UserModel userModel = userClient.get(userID);
                boolean active = userModel.isActive();
                if(active){
                    String actions[] = {"Edit","De activate user account","Reset Password"};
                    showActionDialog(userID,volunteerID,actions);

                }else{
                    String actions[] = {"Edit","Activate user account","Reset Password"};
                    showActionDialog(userID,volunteerID,actions);
                }

            }else{
                showError("Volunteer not found!");
            }
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }catch (Exception ex){
            showError(ex.getMessage());
        }
    }

    @UiThread()
    void showActionDialog(int userID, int volunteerID, String[] actions) {


        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_student_activate_title))
                .setItems(actions, (dialog, which) -> {
                    switch (which){
                        case 0:
                            showForm(volunteerID);
                            break;
                        case 1:
                            String value = actions[1];
                            if(value.contentEquals("De activate user account")) {
                                showDeActivateDialog(userID);
                            }else{
                                showActivateDialog(userID);
                            }
                            break;
                        case 2:
                            showResetPasswordDialog(userID);
                            break;
                    }
                    dialog.dismiss();
                }).show();
    }

    private void showResetPasswordDialog(int userID) {
        View v = getLayoutInflater().inflate(R.layout.dialog_password, null);
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_title_password_reset))
                .setMessage(getString(R.string.dialog_message_password_reset))
                .setPositiveButton("Yes", (dialog, which) -> {
                    resetPasswordAsync(userID);
                    dialog.dismiss();})
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
    @Background
    void resetPasswordAsync(int userID) {
        try {
            UserModel model = userClient.get(userID);
            String userName = model.getUsername();
            model.setPassword(userName);
            userClient.edit(userID,model);
            showSuccessMessage(getString(R.string.notification_success_password_reset));

        }catch (RestClientException ex){
            showError(ex.getMessage());
        }catch (Exception ex){
            showError(ex.getMessage());
        }
    }

    private void showActivateDialog(int userID) {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_student_activate_title))
                .setMessage(getString(R.string.dialog_student_activate_content_text))
                .setPositiveButton("Yes", (dialog, which) -> {
                    updateUserAccountActiveStateAsync(userID,true);

                dialog.dismiss();})
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void showDeActivateDialog(int volunteerID) {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_student_deactivate_title))
                .setMessage(getString(R.string.dialog_student_deactivate_content_text))
                .setPositiveButton("Yes", (dialog, which) -> {
                    updateUserAccountActiveStateAsync(volunteerID, false);

                    dialog.dismiss();})
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
    @Background
    void updateUserAccountActiveStateAsync(int userID, boolean active) {
        try{
                UserModel userModel = userClient.get(userID);
                userModel.setActive(active);
                userClient.activate(userID,userModel);
                String message = active ? getString(R.string.nofitication_success_user_account_activated) : getString(R.string.notification_success_user_account_deactivated);
                showSuccessMessage(message);
                loadList();

        }catch (RestClientException e){
            showError(e.getMessage());

        }catch (Exception e){
            showError(e.getMessage());
        }
    }
    @UiThread
    void showSuccessMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    private void showForm(int volunteerID) {
        VolunteerFormActivity_.intent(this).volunteerID(volunteerID).userType(Constants.USER_TYPE_VOLUNTEER).startForResult(SHOW_FORM);
    }

    @Background
    void loadList(){
        try {
            String criteria = etSearch.getText().toString();
            List<VolunteerModel> models = client.getAll2(criteria).getRecords();
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<VolunteerModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.fab)
    void click(View view){
        showForm(0);
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }


}