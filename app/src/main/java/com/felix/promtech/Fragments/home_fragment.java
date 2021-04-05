package com.felix.promtech.Fragments;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felix.promtech.R;

/**
 * Home fragment
 */
public class home_fragment extends Fragment {

    private TextView your_tasks;

    private static final String TAG = "home_fragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        your_tasks = view.findViewById(R.id.your_tasks);

        //set on clicklistener
        your_tasks.setOnClickListener(v -> {
            OpenYourTasks();
        });

        TextView finished_tasks = view.findViewById(R.id.finished_tasks);
        finished_tasks.setOnClickListener(v -> {
            openFinishedTasks();
        });

        return view;
    }

    private void openFinishedTasks() {
        try{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_home_fragment_to_finished_tasks_fragment);

    }catch (Exception e){

            Log.e(TAG,"open finished tasks", e);
        }
    }

    private void OpenYourTasks() {
        try {
          NavHostFragment.findNavController(this)
                  .navigate(R.id.action_home_fragment_to_your_tasks_fragment);

        }catch (Exception e){

            Log.e(TAG,"open your tasks", e);
        }
    }
}