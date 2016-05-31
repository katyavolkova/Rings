package com.katya.ring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.katya.ring.activity.GameActivity;
import com.katya.ring.activity.ResultActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Work on 19.06.2015.
 */
public class RingView extends View implements Runnable{
    private final static int COORDINATE_X = 0;
    private final static int COORDINATE_Y = 1;
    private float timeRing;
    private float timeGame;
    private ArrayList<Result> results = new ArrayList<>();
    private int coordinateX;
    private int coordinateY;
    private Paint paint;
    private Paint successPaint;
    private Random rnd = new Random();
    private Context context;
    private Handler handler;
    private int maxRadius;
    private int radius;
    private long startTimeRing;
    private long startTimeGame;
    private GameActivity activity;
    private Result result;

    public RingView(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();
        activity = (GameActivity) context;
        initDefaultParams(context.getResources());
        initPaint();
        initTouchListener();
        startTimeGame = System.currentTimeMillis();
        startNewRing();
    }


    private void initDefaultParams(Resources resources) {
        maxRadius = resources.getInteger(R.integer.maxRadius);
        float speed = Setting.getInstance().getSpeedGrowthRing();
        timeRing = maxRadius / speed;
        timeGame = resources.getInteger(R.integer.TIME_GAME);
    }

    private void initTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        if (inCircle(event.getX(), event.getY(), coordinateX, coordinateY, radius)){
                            successPaint.setColor(Color.GREEN);
                            result.setSuccess(true);
                            stopCurrentRing();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }


    private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if ((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }

    private void initPaint(){
        paint = new Paint();
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.FILL);

        successPaint = new Paint();
        successPaint.setStrokeWidth(50);
        successPaint.setColor(Color.GREEN);
    }

    private void startNewRing(){
        radius = context.getResources().getInteger(R.integer.radius);
        paint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        coordinateX = getCoordinate(COORDINATE_X);
        coordinateY = getCoordinate(COORDINATE_Y);
        result = new Result();
        startTimeRing = System.currentTimeMillis();
    }

    private int getCoordinate(int kindCoordinate){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int value;
        if(kindCoordinate == COORDINATE_Y){
            value = metrics.heightPixels;
        } else {
            value = metrics.widthPixels;
        }
        int padding = maxRadius;
        int i = rnd.nextInt((value-padding)-padding)+padding;
        return i;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, getWidth(), 0, successPaint);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);
        handler.postDelayed(this, (long) timeRing);
    }


    @Override
    public void run() {
        if ((System.currentTimeMillis() - startTimeGame) >= timeGame) {
            stopGame();
            return;
        }
        if (radius < maxRadius) {
            radius += 1.5;
        } else {
            successPaint.setColor(Color.RED);
            result.setSuccess(false);
            stopCurrentRing();
        }
        postInvalidate();
    }


    private void stopCurrentRing(){
        long difference = System.currentTimeMillis() - startTimeRing;
        result.setTime((int) difference);
        result.setCoordinateX(coordinateX);
        result.setCoordinateY(coordinateY);
        results.add(result);
        startNewRing();
    }

    private void stopGame(){
        activity.finish();
        Intent intent = new Intent(activity, ResultActivity.class);
        Bundle bundleObject = new Bundle();
        bundleObject.putSerializable("RESULTS", results);
        intent.putExtras(bundleObject);
        activity.startActivityForResult(intent, 0);
    }
}
