package com.example.shaza.test_alarm;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;
import android.os.*;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class BackgroundService extends Service {

    public static MediaPlayer mediaPlayer ;
    private RequestQueue mQueue ;

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    private int currentrecords ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {

            System.out.println("SERVICE IS RUNNING");

//            mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//            mediaPlayer.start();


            mQueue = Volley.newRequestQueue(BackgroundService.this);

            String url = "http://192.168.1.62:3001/patients/2/numberofseizures";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response){
                            try {
                                JSONArray jsonArray = response.getJSONArray("Seizures");
                                JSONObject patient = jsonArray.getJSONObject(0);
                                currentrecords = Integer.parseInt( patient.getString("numberOfSeizures"));
                              //  savedstatus = currentrecords ;

                                Log.d("done",patient.getString("numberOfSeizures"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener(){
                public void onErrorResponse(VolleyError error){
                    error.printStackTrace();
                }
            });

            mQueue.add(request);

            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            Log.d("heree","HERE3");
            this.backgroundThread.start();
            Log.d("heree","HERE4");
        }
        return START_STICKY;
    }

}