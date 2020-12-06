package com.example.studentrelief.ui.home;

import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DashboardClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.DashboardModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

@OptionsMenu(R.menu.main)
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
    @RestService
    UserClient userClient;
    @RestService
    DashboardClient dashboardClient;
    @Pref
    MyPrefs_ myPrefs;

    @ViewById(R.id.textview_donner_count)
    TextView textViewDonnerCount;

    @ViewById(R.id.textview_employee_count)
    TextView textViewEmployeeCount;

    @ViewById(R.id.textview_relief_request_count)
    TextView textViewReliefRequestCount;

    @ViewById(R.id.textview_relief_task_count)
    TextView textViewReliefTaskCount;

    @ViewById(R.id.textview_student_count)
    TextView textViewStudentCount;

    @ViewById(R.id.textview_user_count)
    TextView textViewUserCount;
    private DashboardModel dashboardModel;


    @AfterViews
    void Init(){
        initAuthCookies();
        loadDashboard();

   }
    @Background
     void loadDashboard() {
        try {
            dashboardModel = dashboardClient.get().getSingleRecord();
            updateUIDashboardData();

        }catch (RestClientException ex){
            showError(ex.getMessage());
        }
    }
    @UiThread
    void updateUIDashboardData() {

        textViewDonnerCount.setText(String.valueOf(dashboardModel.getDonner_count()));
        textViewEmployeeCount.setText(String.valueOf(dashboardModel.getEmployee_count()));
        textViewReliefRequestCount.setText(String.valueOf(dashboardModel.getRelief_request_count()));
        textViewReliefTaskCount.setText(String.valueOf(dashboardModel.getRelief_task_count()));
        textViewStudentCount.setText(String.valueOf(dashboardModel.getStudent_count()));
        textViewUserCount.setText(String.valueOf(dashboardModel.getUser_count()));
    }

    @OptionsItem(R.id.action_home_logout)
    void logout(){

        logoutCurrentUser();
   }
    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        userClient.setCookie(name,session);
        dashboardClient.setCookie(name,session);
    }
    @Background
   void logoutCurrentUser() {
        try {
            userClient.logout();
            updateUIAfterLogout();
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }catch (Exception ex){
            showError(ex.getMessage());
        }
    }
    @UiThread
    void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    @UiThread
    void updateUIAfterLogout() {
        getActivity().finish();
    }
}