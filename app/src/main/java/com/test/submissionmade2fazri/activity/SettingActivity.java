package com.test.submissionmade2fazri.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.notif.DailyNotif;
import com.test.submissionmade2fazri.notif.ReleaseNotif;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private DailyNotif dailyNotif;
    private ReleaseNotif releaseNotif;
    private SwitchCompat switchDaily, switchRelease;
    private LinearLayout lyLanguageAppChange;
    private SharedPreferences sharedPreferences;
    private String dateSharedInfo = "date_shared";
    private String daily_check = "daily_check";
    private String release_check = "release_check";
    private Boolean daily_swipe = false;
    private Boolean release_swipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        switchDaily = findViewById(R.id.switch_daily_btn);
        switchRelease = findViewById(R.id.switch_release_btn);
        lyLanguageAppChange = findViewById(R.id.ly_language_apps);
        dailyNotif = new DailyNotif();
        releaseNotif = new ReleaseNotif();

        lyLanguageAppChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(dateSharedInfo, Context.MODE_PRIVATE);
        daily_swipe = sharedPreferences.getBoolean(daily_check, false);
        release_swipe = sharedPreferences.getBoolean(release_check, false);

        if (daily_swipe){
            switchDaily.setChecked(true);
        }else {
            switchDaily.setChecked(false);
        }

        if (release_swipe){
            switchRelease.setChecked(true);
        }else {
            switchRelease.setChecked(false);
        }



        switchDaily.setOnClickListener(this);
        switchRelease.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.switch_daily_btn:
                if (switchDaily.isChecked()){
                    dailyNotif.setDailyNotif(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(daily_check, true);
                    editor.apply();
                }else {
                    dailyNotif.cancelDailyNotification(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(daily_check);
                    editor.apply();
                }
                break;
            case R.id.switch_release_btn:
                if (switchRelease.isChecked()){
                    releaseNotif.setReleaseNotif(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(release_check, true);
                    editor.apply();
                }else {
                    releaseNotif.cancelReleaseNotification(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(release_check);
                    editor.apply();
                }
                break;
        }
    }
}
