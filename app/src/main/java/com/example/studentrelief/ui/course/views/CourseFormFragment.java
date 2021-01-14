package com.example.studentrelief.ui.course.views;

import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.studentrelief.MainActivityViewModel;
import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.CourseClient;
import com.example.studentrelief.services.model.CourseModel;

import com.example.studentrelief.ui.course.CourseViewModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

@OptionsMenu(R.menu.menu_form)
@EFragment(R.layout.fragment_course_form)
public class CourseFormFragment extends Fragment {

    private static final String COURSE_ID = "courseID";
    @Pref
    MyPrefs_ myPrefs;

    @RestService
    CourseClient courseClient;
    private MainActivityViewModel mMainActivityViewModel;
    private int courseID;
    private CourseModel courseModel;

    @ViewById
    TextInputEditText courseTitleInput;
    private CourseViewModel mViewModel;

    public void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        courseClient.setCookie(name,session);
    }


    @AfterViews
    void afterViews(){
        getActivity().setTitle("Course Form");
        initAuthCookies();
        initObservers();

        courseID = getArguments().getInt(COURSE_ID);
        if(courseID > 0) {
            getFormData();
        }else{
          courseModel = new CourseModel();
        }

    }
    @Background
    void getFormData() {
        try {
            if (courseID > 0){
                courseModel = courseClient.get(courseID);
                updateUIFormData();
            }
        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }
    @UiThread
    void updateUIFormData() {
       courseTitleInput.setText(courseModel.getTitle());
    }

    @UiThread
    void showError(String message) {

    }

    @OptionsMenuItem(R.id.action_delete)
    void onShowActionDelete(MenuItem menuItem){
        if(courseID == 0){
            menuItem.setVisible(false);
        }
    }


    private void initObservers() {
        mViewModel = new ViewModelProvider(getParentFragment()).get(CourseViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mMainActivityViewModel.getAppTitle().setValue("Course Form");

    }


    @OptionsItem(R.id.action_save)
    void onSave(){
        String title = courseTitleInput.getText().toString();
        courseModel.setTitle(title);
        // always 0 for new record
        saveAsync();
    }
    @Background
    void saveAsync() {
        try {
            if (courseID > 0){
                courseClient.edit(courseID,courseModel);
            }else{
                courseClient.addNew(courseModel);
            }
            updateUIAfterAction();
        }catch(RestClientException e) {
            showError(e.toString());
        }
    }
    @OptionsItem(R.id.action_delete)
    void onDelete(){
        deleteAsync();
    }
    @Background
    void deleteAsync() {
        try {
            if (courseID > 0){
                courseClient.delete(courseID);
            }
            updateUIAfterAction();
        }catch(RestClientException e) {
            showError(e.toString());
        }
    }
    @UiThread
    void updateUIAfterAction() {
        NavHostFragment.findNavController(CourseFormFragment.this)
                .navigate(R.id.action_fragment_course_form_to_fragment_course_list);

    }
}