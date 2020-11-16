package com.example.studentrelief.ui.volunteer;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.View;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static androidx.navigation.Navigation.findNavController;

@EActivity(R.layout.activity_volunteer_panel)
public class VolunteerPanelActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;
    @AfterViews
    void afterViews(){

        setSupportActionBar(toolbar);
    }

}