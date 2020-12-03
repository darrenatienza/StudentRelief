package com.example.studentrelief.ui.home;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.interfaces.UserClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

@OptionsMenu(R.menu.main)
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
    @RestService
    DonnerClient donnerClient;
    @RestService
    UserClient userClient;



    @AfterViews
    void Init(){


   }
   @OptionsItem(R.id.action_home_logout)
    void logout(){

        logoutCurrentUser();
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