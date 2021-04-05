package com.felix.promtech.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felix.promtech.R;

import static android.content.ContentValues.TAG;

/**
 *
 */
public class userProfile_fragment extends Fragment {

    private static final String TAG = "userProfile_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        TextView Profile_Details = view.findViewById(R.id.profile_details);
        TextView Social_Media_Profiles = view.findViewById(R.id.social_media_profiles);
        TextView Post_Prices = view.findViewById(R.id.post_prices);
        TextView Payment_Details = view.findViewById(R.id.payment_details);

        //Setting onClick listeners

        Profile_Details.setOnClickListener(v -> {
            change_profile_settings();
        });

        Social_Media_Profiles.setOnClickListener(v -> {
            social_media_profiles();
        });

        Post_Prices.setOnClickListener(v -> {
            post_prices();
        });

        Payment_Details.setOnClickListener(v -> {
            payment_details();
        });


        return view;
    }

    private void change_profile_settings() {

        try {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_userProfile_fragment_to_change_profile2);

        }catch (Exception e){

            Log.e(TAG,"change profile", e);

        }
    }

    private void social_media_profiles(){

        try {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_userProfile_fragment_to_social_media_profiles2);

        }catch (Exception e){

            Log.e(TAG, "social_media_profiles", e);
        }
    }

    private void post_prices(){

        try {
          NavHostFragment.findNavController(this)
                  .navigate(R.id.action_userProfile_fragment_to_post_prices_fragment);

        }catch (Exception e){

            Log.e(TAG,"post prices", e);
        }
    }

    private void payment_details(){
        try {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_userProfile_fragment_to_payment_details2);

        }catch (Exception e){

            Log.e(TAG,"payment details", e);
        }

    }

}

  