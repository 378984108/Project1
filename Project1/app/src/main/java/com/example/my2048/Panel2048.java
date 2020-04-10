package com.example.my2048;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.nfc.cardemulation.CardEmulation;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Panel2048 extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 6;
    private int numbers[][];

    private Paint mPaint = new Paint();

    private Bitmap b_2;
    private Bitmap b_4;
    private Bitmap b_8;
    private Bitmap b_16;
    private Bitmap b_32;
    private Bitmap b_64;
    private Bitmap b_128;
    private Bitmap b_256;
    private Bitmap b_512;
    private Bitmap b_1024;
    private Bitmap b_2048;

    public Panel2048(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        b_2     = BitmapFactory.decodeResource(getResources(), R.drawable.block2);
        b_4     = BitmapFactory.decodeResource(getResources(), R.drawable.block4);
        b_8     = BitmapFactory.decodeResource(getResources(), R.drawable.block8);
        b_16    = BitmapFactory.decodeResource(getResources(), R.drawable.block16);
        b_32    = BitmapFactory.decodeResource(getResources(), R.drawable.block32);
        b_64    = BitmapFactory.decodeResource(getResources(), R.drawable.block64);
        b_128   = BitmapFactory.decodeResource(getResources(), R.drawable.block128);
        b_256   = BitmapFactory.decodeResource(getResources(), R.drawable.block256);
        b_512   = BitmapFactory.decodeResource(getResources(), R.drawable.block512);
        b_1024  = BitmapFactory.decodeResource(getResources(), R.drawable.block1024);
        b_2048  = BitmapFactory.decodeResource(getResources(), R.drawable.block2048);

        setOnTouchListener( new OnTouchListener() {

            private float startx, starty;
            private float endx, endy;
            private float offsetx, offsety;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        endx = event.getX();
                        endy = event.getY();
                        offsetx = endx - startx;
                        offsety = endy - starty;
                        if(Math.abs( offsetx ) > Math.abs( offsety )){
                            if (offsetx < -5) {
                                Movetoleft();
                            }
                            else if (offsetx > 5){
                                MovetoRight();
                            }
                        }
                        else{
                            if (offsety < -5){
                                MovetoUp();
                            }
                            else if (offsety > 5){
                                MovetoDown();
                            }
                        }
                    case MotionEvent.ACTION_DOWN:
                        startx = event.getX();
                        starty = event.getY();
                        break;
                }
                return true;
            }
        } );
    }

    protected  void  onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if(widthMode == MeasureSpec.UNSPECIFIED){
            width = heightSize;
        }else if(heightMode == MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }

        setMeasuredDimension(width, width);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        b_2     = Bitmap.createScaledBitmap(b_2,    (int) mLineHeight, (int) mLineHeight, false);
        b_4     = Bitmap.createScaledBitmap(b_4,    (int) mLineHeight, (int) mLineHeight, false);
        b_8     = Bitmap.createScaledBitmap(b_8,    (int) mLineHeight, (int) mLineHeight, false);
        b_16    = Bitmap.createScaledBitmap(b_16,   (int) mLineHeight, (int) mLineHeight, false);
        b_32    = Bitmap.createScaledBitmap(b_32,   (int) mLineHeight, (int) mLineHeight, false);
        b_64    = Bitmap.createScaledBitmap(b_64,   (int) mLineHeight, (int) mLineHeight, false);
        b_128   = Bitmap.createScaledBitmap(b_128,  (int) mLineHeight, (int) mLineHeight, false);
        b_256   = Bitmap.createScaledBitmap(b_256,  (int) mLineHeight, (int) mLineHeight, false);
        b_512   = Bitmap.createScaledBitmap(b_512,  (int) mLineHeight, (int) mLineHeight, false);
        b_1024  = Bitmap.createScaledBitmap(b_1024, (int) mLineHeight, (int) mLineHeight, false);
        b_2048  = Bitmap.createScaledBitmap(b_2048, (int) mLineHeight, (int) mLineHeight, false);

    }

    protected void  onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawGrid(canvas);
    }
    private void drawGrid(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++){
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }
    public void Movetoleft(){
        int emp[] = getempty();

    }
    public void MovetoRight(){

    }
    public void MovetoUp(){

    }
    public void MovetoDown(){

    }
    public int[] getempty(){
        int i = 0;
        int j = 0;
        int result[] = new int[50];
        int a = 0,b = 0;
        while (i < 5){
            while (j < 5){
                if (numbers[i][j] == 0){
                    result[a] = i;
                    result[b] = j;
                    a = a + 1;
                    b = b + 1;
                }
                j = j + 1;
            }
            j = 0;
            i = i + 1;
        }
        return result;
    }
}

