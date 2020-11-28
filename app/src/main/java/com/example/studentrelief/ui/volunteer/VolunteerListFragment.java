package com.example.studentrelief.ui.volunteer;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.adapters.VolunteerAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
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

import java.util.List;


@EFragment(R.layout.fragment_volunteer_list)
public class VolunteerListFragment extends Fragment {

    static  final int SHOW_FORM = 101;
    @RestService
    VolunteerClient client;
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
                int id = Integer.parseInt(t.getText().toString());
                showFormDialog(id);
            }
        });
    }

    private void showFormDialog(int id) {
        VolunteerFormActivity_.intent(this).volunteerID(id).startForResult(SHOW_FORM);
    }

    @Background
    void loadList(){
        try {
            String criteria = etSearch.getText().toString();
            List<VolunteerModel> models = client.getAll(criteria).getRecords();
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
    @Click(R.id.btnSearch)
    void search(){
        loadList();
    }
    @Click(R.id.fab)
    void click(View view){
        showFormDialog(0);
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }


}