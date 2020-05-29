package com.example.my2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView Score;
    private Panel2048 panel;
    private int score;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Score = findViewById( R.id.score );
        panel = findViewById( R.id.panel );
        timer = new Timer(  );
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                myhandler.sendMessage( msg );
            }
        },0,100 );
    }
    private Handler myhandler = new Handler(  ){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0){
                score = panel.getScore();
                Score.setText( score + "" );
            }
        }
    };

}
