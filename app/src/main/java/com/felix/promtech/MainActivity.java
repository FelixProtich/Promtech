package com.felix.promtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;

import com.felix.promtech.Fragments.home_fragment;
import com.felix.promtech.Fragments.settings_fragment;
import com.felix.promtech.Fragments.userProfile_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>tasksList;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initRecyclerView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Fragment_Container, new home_fragment()).commit();
    }

    private void initData() {

        tasksList = new ArrayList<>();
        tasksList.add(new ModelClass("CAMPAIGN","KCB Wezesha", "23433"));
        tasksList.add(new ModelClass("CAMPAIGN","Safaricom Datafree", "23433"));
        tasksList.add(new ModelClass("CAMPAIGN","COOP Easter", "23433"));
        tasksList.add(new ModelClass("CAMPAIGN","TUK Admission", "23433"));
        tasksList.add(new ModelClass("CAMPAIGN","Stay safe wear mask", "23433"));
        tasksList.add(new ModelClass("CAMPAIGN","Tuff form matresses", "23433"));

    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapter(tasksList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch(item.getItemId()){

                case R.id.home:
                    selectedFragment = new home_fragment();
                    break;

                case R.id.profile:
                    selectedFragment = new userProfile_fragment();
                    break;

                case R.id.settings:
                    selectedFragment = new settings_fragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container, selectedFragment);

            return true;
        }
    };

}