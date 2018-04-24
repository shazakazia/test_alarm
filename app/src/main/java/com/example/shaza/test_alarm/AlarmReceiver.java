package com.example.shaza.test_alarm;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;
import android.os.*;
import android.provider.Settings;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
           // background.putExtra("status", 7);
        Log.d("heree","HERE5");
        context.startService(background);
        ;

    }

}