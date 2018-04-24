package com.example.shaza.test_alarm;

import android.app.Activity;
import android.os.*;
import android.content.*;
import android.app.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ExampleActivity extends Activity  {

    private Context context;
    private Button stopbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopbtn = findViewById(R.id.stopbtn) ;
        Log.d("heree","HERE1");
        this.context = this;

        Intent alarm = new Intent(this.context, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

        if(alarmRunning == false) {
            Log.d("heree","HERE2");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 3000, pendingIntent);
        }

//        stopbtn.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlarmReceiver.mediaPlayer.stop();
//            }
//        }));
    }

}