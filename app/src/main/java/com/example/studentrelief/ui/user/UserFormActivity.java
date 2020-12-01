package com.example.studentrelief.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_user_form)
public class UserFormActivity extends AppCompatActivity {


    @ViewById
    Toolbar toolbar;

    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
    }

}