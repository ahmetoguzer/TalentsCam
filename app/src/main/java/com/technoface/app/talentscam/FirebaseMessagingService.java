package com.technoface.app.talentscam;

/**
 * Created by Ahmet Oguzer on 15.11.2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.technoface.app.talentscam.Activities.ActivitySplashScreen;
import com.technoface.app.talentscam.Activities.ForgotPasswordActivity;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Utils.Common;


import java.util.Map;

/**
 * Created by Ahmet on 10.10.2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {
    private static final String TAG = "FirebaseMessagingService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String title = remoteMessage.getNotification().getTitle();
//        String message = remoteMessage.getNotification().getBody();

        String value="";
    try {
        Map<String, String> meMap = remoteMessage.getData();
         value = (String) meMap.get("body");
    }catch (Exception ex){
        value=ex.getMessage();
    }
        sendNotification(value);
    }


    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, ActivitySplashScreen.class);
        intent.putExtra("message",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.topicon)
                .setContentTitle("TalentsCam")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        Intent intent1 = new Intent("myFunction");
        // add data
        intent.putExtra("message", messageBody);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
    }
}