package com.katya.ring.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.katya.ring.R;
import com.katya.ring.Setting;

public class SettingActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SeekBar speed = (SeekBar) findViewById(R.id.seekBar);
        speed.setOnSeekBarChangeListener(this);
        speed.setProgress(Setting.getInstance().getSpeedGrowthRing());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int speed = seekBar.getProgress();
        Setting.getInstance().setSpeedGrowthRing(speed);
    }
}
