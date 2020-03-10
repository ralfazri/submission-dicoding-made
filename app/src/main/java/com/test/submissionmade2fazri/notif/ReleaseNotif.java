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
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.MainActivity;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.model.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReleaseNotif extends BroadcastReceiver {

    private String CHANNEL_ID = "Channel_02";
    private String CHANNEL_NAME = "ReleaseReminder_channel";
    public static final String TYPE= "type";
    private final static int RELEASE_ID = 200;
    private final static int NOTIF_ID = 200;
    private static final int MAX_NOTIFICATION = 1;
    private final List<Film> stackNotif = new ArrayList<>();
    private final static String GROUP_KEY = "group_key";


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String type = intent.getStringExtra(TYPE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String todayDate = dateFormat.format(currentTime);

        String url = Constant.API_NOTIF + Constant.API_KEY + Constant.GTE + todayDate + Constant.LTE + todayDate;

        AsyncHttpClient http = new AsyncHttpClient();
        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ArrayList<Film> arrayList = new ArrayList<>();
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray data = responseObject.getJSONArray("results");

                    for (int i = 0; i < data.length(); i++){
                        JSONObject json = data.getJSONObject(i);
                        Film filmItem = new Film(json);
                        arrayList.add(filmItem);
                        String header = json.getString("title");
                        String message = context.getResources().getString(R.string.release_movie);
                        String box = header + ", " + message;
                        showReleaseNotification(context, header, box, RELEASE_ID + i);
                    }

//
                } catch (JSONException e) {
                    Log.d("Exception get movies: ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure get movies: ", error.toString());
            }
        });
    }

    private void showToastHeader(Context context,String title, String message){
        Toast.makeText(context,title + " : ", Toast.LENGTH_SHORT).show();
    }

    private void showMessage(Context context, int id){
        if (id == 1){
            Toast.makeText(context, R.string.release_notif_on, Toast.LENGTH_SHORT).show();
        }else if (id == 2){
            Toast.makeText(context, R.string.release_notif_off, Toast.LENGTH_SHORT).show();
        }
    }

    public  void setReleaseNotif(Context context){
        AlarmManager alert = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent =  new Intent(context, ReleaseNotif.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);

        if (alert != null){
            alert.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        showMessage(context, 1);
    }



    private void showReleaseNotification(Context context, String title, String message, int notificationId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder;

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
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

    public void cancelReleaseNotification(Context context){
        AlarmManager alertCancel =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);
        pendingIntent.cancel();
        if (alertCancel != null){
            alertCancel.cancel(pendingIntent);
        }
        showMessage(context, 2);
    }

//    public boolean isAlarmClicked(Context context){
//        Intent intent = new Intent(context, ReleaseNotif.class);
//        int value = 1;
//        return PendingIntent.getBroadcast(context,value,intent, PendingIntent.FLAG_NO_CREATE)!=null;
//    }
}
