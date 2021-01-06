package com.example.studentrelief.ui.misc;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value =SharedPref.Scope.APPLICATION_DEFAULT)
public interface MyPrefs {
    @DefaultString("")
    String session();
}
