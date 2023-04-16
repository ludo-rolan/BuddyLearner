package com.example.buddylearner.ui.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import com.example.buddylearner.R;
import com.example.buddylearner.services.NotifyService;

public class NotificationActivity extends AppCompatActivity {


//    private NotifyService myService;
//    private boolean mBound = false;
//
//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            NotifyService.LocalBinder binder = (NotifyService.LocalBinder) service;
//            myService = (NotifyService) binder.getService();
//            mBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mBound = false;
//        }
//    };
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        bindService(new Intent(this, NotifyService.class), mConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        unbindService(mConnection);
//    }

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        textView = findViewById(R.id.notification_text);
        String data = getIntent().getStringExtra("data");
        textView.setText(data);

    }

}