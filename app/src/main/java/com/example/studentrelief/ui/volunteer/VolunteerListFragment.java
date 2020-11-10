package com.example.studentrelief.ui.volunteer;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.adapters.VolunteerAdapter;
import com.example.studentrelief.ui.donner.DonnerFormActivity_;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
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
    TextView tvSearch;
    @Bean
    VolunteerAdapter adapter;

    @AfterViews
    void afterViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList();
        initItemClick();
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
        VolunteerFormActivity_.intent(this).extra("id",id).startForResult(SHOW_FORM);
    }

    @Background
    void loadList(){
        try {
            String criteria = tvSearch.getText().toString();
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
        VolunteerFormActivity_.intent(getContext()).startForResult(SHOW_FORM);
    }

    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }


}