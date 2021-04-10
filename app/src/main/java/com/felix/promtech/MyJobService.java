package com.felix.promtech;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";
    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(getApplicationContext(),"Welcome back Tiger", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onStartJob: Welcome back");

        //Reschedule
        BackgroundUtil.scheduleBackgroundTask(this);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(getApplicationContext(),"New job invites awaits you", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"onStop: New jobs awaits you");
        return true;
    }
}
