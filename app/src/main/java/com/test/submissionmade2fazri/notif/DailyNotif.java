package com.test.submissionmade2fazri.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.test.submissionmade2fazri.MainActivity;
import com.test.submissionmade2fazri.R;

import java.util.Calendar;

public class DailyNotif extends BroadcastReceiver {

    private String CHANNEL_ID = "Channel_01";
    private String CHANNEL_NAME = "DailyReminder_channel";
    private int REMINDER_ID = 201;
    private int NOTIF_ID = 201;
    private String header, desc;

    public DailyNotif(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        header = context.getResources().getString(R.string.show_daily);
        desc =  context.getResources().getString(R.string.desc_show_daily);
        showDailyNotification(context, header, desc, REMINDER_ID);
    }

    public void setDailyNotif(Context context){
        AlarmManager alert = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);

        if (alert !=null){
            alert.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        showMessage(context, 1);
    }

    private void showMessage(Context context, int id){
        if (id == 1){
            Toast.makeText(context, R.string.daily_notif_on, Toast.LENGTH_SHORT).show();
        }else if (id == 2){
            Toast.makeText(context, R.string.daily_notif_off, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDailyNotification(Context context, String title,String message, int notificationId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notificationId, notification);
        }
    }

    public void cancelDailyNotification(Context context){
        AlarmManager alertCancel =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID, intent, 0);
        pendingIntent.cancel();
        if (alertCancel != null){
            alertCancel.cancel(pendingIntent);
        }
        showMessage(context, 2);
    }

//    public boolean isAlarmClicked(Context context){
//        Intent intent = new Intent(context, DailyNotif.class);
//        int value = 1;
//        return PendingIntent.getBroadcast(context,value,intent, PendingIntent.FLAG_NO_CREATE)!=null;
//    }
}
