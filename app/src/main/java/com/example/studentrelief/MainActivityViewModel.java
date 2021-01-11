package com.example.studentrelief;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> appTitle;


    public MutableLiveData<String> getAppTitle(){
        if(appTitle == null){
            appTitle = new MutableLiveData<>();

        }
        return  appTitle;
    }
}
