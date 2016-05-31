package com.katya.ring.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.katya.ring.R;
import com.katya.ring.Result;

import java.util.ArrayList;

public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        LinearLayout resultsPane = (LinearLayout) findViewById(R.id.resultsPane);
        if (getIntent().getExtras() != null) {
            Bundle bundleObject = getIntent().getExtras();
            ArrayList<Result> results = (ArrayList<Result>) bundleObject.getSerializable("RESULTS");
            for (Result result : results) {
                TextView textView = new TextView(this);
                resultsPane.addView(textView);
                String success;
                if (result.isSuccess()){
                    success = "success";
                } else {
                    success = "failed";
                }
                //textView.setText(result.getTime()+" - " + success + "   X:" + result.getCoordinateX()+ " Y:" + result.getCoordinateY());
                textView.setText(result.getTime()+" - " + success);
            }
        }
    }
}
