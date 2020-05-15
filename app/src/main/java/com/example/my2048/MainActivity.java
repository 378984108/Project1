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

public class MainActivity extends AppCompatActivity {
    private TextView Score;
    private Panel2048 panel;
    private int score;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        handler = new Handler(  ){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0){
                    score = panel.getScore();
                    Score.setText( score + "" );
                    sendEmptyMessageDelayed( 0,300 );
                }
            }
        };
        Score = findViewById( R.id.score );
        panel = findViewById( R.id.panel );
        handler.sendEmptyMessageDelayed( 0,100 );
    }

}
