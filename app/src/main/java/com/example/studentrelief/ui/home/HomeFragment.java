package com.example.studentrelief.ui.home;

import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment {
    @RestService
    DonnerClient donnerClient;
   @ViewById(R.id.text_home)
    TextView et;
    private int count;


    @AfterViews
    void Init(){
       et.setText("asdf");

   }

}