package edu.ycp.cs482.triviagame;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class GameAlertReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "Times Up", "5 second has passed", "Alert");

    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, GamePage.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        builder.setContentIntent(notificIntent);

        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        builder.setAutoCancel(true);

        NotificationManager mn = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mn.notify(1, builder.build());

    }
}