package com.example.my2048;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import static android.animation.ObjectAnimator.ofFloat;

public class Panel2048 extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 6;
    private int blocks[][] = new int [5][5];
    private int numBlock = 0;
    private boolean isGameOver = false;
    private boolean isPanelFull = false;
    private boolean isWin = false;
    private boolean ismoved = false;
    public int score = 0;
    private Paint mPaint = new Paint();
    private Paint mbgPaint = new Paint();

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    private Context mContext = null;

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
    private Bitmap newb_2;
    private Bitmap newb_4;
    private Bitmap mergeb_4;
    private Bitmap mergeb_8;
    private Bitmap mergeb_16;
    private Bitmap mergeb_32;
    private Bitmap mergeb_64;
    private Bitmap mergeb_128;
    private Bitmap mergeb_256;
    private Bitmap mergeb_512;
    private Bitmap mergeb_1024;
    private Bitmap mergeb_2048;

    public Panel2048(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        blocks[0][0] = 1024;
        blocks[0][1] = 1024;
        mContext = context;
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
        b_128   = BitmapFactory.decodeResource(getResources(), R.drawable.block128 );
        b_256   = BitmapFactory.decodeResource(getResources(), R.drawable.block256);
        b_512   = BitmapFactory.decodeResource(getResources(), R.drawable.block512);
        b_1024  = BitmapFactory.decodeResource(getResources(), R.drawable.block1024);
        b_2048  = BitmapFactory.decodeResource(getResources(), R.drawable.block2048);
        newb_2 = BitmapFactory.decodeResource( getResources(), R.drawable.newblock2 );
        newb_4 = BitmapFactory.decodeResource( getResources(), R.drawable.newblock4 );
        mergeb_4 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock4 );
        mergeb_8 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock8 );
        mergeb_16 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock16 );
        mergeb_32 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock32 );
        mergeb_64 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock64 );
        mergeb_128 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock128 );
        mergeb_256 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock256 );
        mergeb_512 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock512 );
        mergeb_1024 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock1024 );
        mergeb_2048 = BitmapFactory.decodeResource( getResources(), R.drawable.mergedblock2048 );

        mbgPaint.setColor(Color.LTGRAY);
        mbgPaint.setAntiAlias(true);
        mbgPaint.setDither(true);
        mbgPaint.setStyle(Paint.Style.FILL);

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



    private void searchPanel(Canvas canvas) {
        for (int m = 0; m < 5; m++){
            for (int n = 0; n < 5; n++){
                if (blocks[m][n] > 0) {
                    float output_x = (float) (m + 0.5);
                    float output_y = (float) (n + 0.5);
                    int blockType = blocks[m][n];

                    if (blockType == 2){    canvas.drawBitmap(b_2, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 4){    canvas.drawBitmap(b_4, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 8){    canvas.drawBitmap(b_8, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 16){   canvas.drawBitmap(b_16, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 32){   canvas.drawBitmap(b_32, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 64){   canvas.drawBitmap(b_64, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 128){  canvas.drawBitmap(b_128, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 256){  canvas.drawBitmap(b_256, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 512){  canvas.drawBitmap(b_512, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 1024){ canvas.drawBitmap(b_1024, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 2048){ canvas.drawBitmap(b_2048, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 3){ canvas.drawBitmap(newb_2, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 11){ canvas.drawBitmap(newb_4, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 5){ canvas.drawBitmap(mergeb_4, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 9){ canvas.drawBitmap(mergeb_8, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 17){ canvas.drawBitmap(mergeb_16, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 33){ canvas.drawBitmap(mergeb_32, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 65){ canvas.drawBitmap(mergeb_64, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 129){ canvas.drawBitmap(mergeb_128, output_x * mLineHeight, output_y * mLineHeight, null);}
                    if (blockType == 257){ canvas.drawBitmap(mergeb_256, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 513){ canvas.drawBitmap(mergeb_512, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 1025){ canvas.drawBitmap(mergeb_1024, output_x * mLineHeight, output_y * mLineHeight, null); }
                    if (blockType == 2049){ canvas.drawBitmap(mergeb_2048, output_x * mLineHeight, output_y * mLineHeight, null); }
                }
            }
        }
    }

    protected void  onMeasure(int widthMeasureSpec, int heightMeasureSpec){
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
        newb_2  = Bitmap.createScaledBitmap(newb_2, (int) mLineHeight, (int) mLineHeight, false);
        newb_4  = Bitmap.createScaledBitmap(newb_4, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_4  = Bitmap.createScaledBitmap(mergeb_4, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_8  = Bitmap.createScaledBitmap(mergeb_8, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_16  = Bitmap.createScaledBitmap(mergeb_16, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_32  = Bitmap.createScaledBitmap(mergeb_32, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_64  = Bitmap.createScaledBitmap(mergeb_64, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_128  = Bitmap.createScaledBitmap(mergeb_128, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_256  = Bitmap.createScaledBitmap(mergeb_256, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_512  = Bitmap.createScaledBitmap(mergeb_512, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_1024  = Bitmap.createScaledBitmap(mergeb_1024, (int) mLineHeight, (int) mLineHeight, false);
        mergeb_2048  = Bitmap.createScaledBitmap(mergeb_2048, (int) mLineHeight, (int) mLineHeight, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  onDraw(Canvas canvas){
        canvas.drawRoundRect(0,0, getMeasuredWidth(), getMeasuredHeight(), 30.0f, 30.0f, mbgPaint);
        super.onDraw(canvas);
        drawGrid(canvas);
        searchPanel(canvas);
        checkPanelFull();
        checkGameOver();
    }


    private void checkPanelFull() {
        for (int m = 0; m < 5; m++){
            for (int n = 0; n < 5; n++){
                if (blocks[m][n] > 0) {
                    numBlock++;
                 }
            }
        }
        if (numBlock == 25){
            isPanelFull = true;
        }
    }

    private void checkGameOver() {
        int determine = 0;
        for (int m = 0; m < 5; m++){
            for (int n = 0; n < 5; n++){
                if (blocks[m][n] == 2049) {
                    determine = 2049;
                }
            }
        }
        if (determine == 2049){
            isGameOver = true;
            isWin = true;
        }
        if (isPanelFull == true) {
        isGameOver = true;
        for (int m = 0; m < 4; m++){
            for (int n = 0; n < 4; n++){
                if (blocks[m][n] == blocks[m][n+1]) {
                    isGameOver = false;
                }
                if (blocks[m+1][n] == blocks[m][n]) {
                    isGameOver = false;
                }
            }
        }
        }
        if (isGameOver == true){
            alert = null;
            builder = new AlertDialog.Builder(mContext);
            if (isWin == false){
                alert = builder.setIcon(R.drawable.block2048)
                        .setTitle("你输了！")
                        .setMessage("再来一局？")
                        .setNegativeButton("退出", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                System.exit(0);
                            }
                        })
                        .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restart();
                            }
                }).create();
                alert.show();
            }else {
                alert = builder.setIcon(R.drawable.block2048)
                        .setTitle("你赢了！")
                        .setMessage("再来一局？")
                        .setNegativeButton("退出", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                System.exit(0);
                            }
                        })
                        .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restart();
                            }
                        }).create();
                alert.show();
            }
        }
    }

    private void restart(){
        for (int m = 0; m < 5; m++){
            for (int n = 0; n < 5; n++){
                blocks[m][n] = 0;
            }
        }
        addnumber();
        numBlock = 0;
        isWin = false;
        isGameOver = false;
        isPanelFull = false;
        score = 0;
        invalidate();
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
        int i = 1;
        int j = 0;
        removeOddnumber();
        while (j < 5){
            while (i < 5){
                int k = i;
                while (k != 0 && (blocks[k][j] != 0 && blocks[k-1][j] == 0)){
                    blocks[k-1][j] = blocks[k][j];
                    blocks[k][j] = 0;
                    ismoved = true;
                    k = k - 1;
                }
                i = i + 1;
            }
            i = 1;
            j = j + 1;
        }
        mergeleft();
        if (ismoved == true){
            addnumber();
            ismoved = false;
        }
        invalidate();
    }
    public void MovetoRight(){
        int i = 3;
        int j = 0;
        removeOddnumber();
        while (j < 5){
            while (i >= 0){
                int k = i;
                while (k != 4 && (blocks[k][j] != 0 && blocks[k+1][j] == 0)){
                    blocks[k+1][j] = blocks[k][j];
                    blocks[k][j] = 0;
                    ismoved = true;
                    k = k + 1;
                }
                i = i - 1;
            }
            i = 3;
            j = j + 1;
        }
        mergeright();
        if (ismoved == true){
            addnumber();
            ismoved = false;
        }
        invalidate();
    }
    public void MovetoUp(){
        int i = 0;
        int j = 1;
        removeOddnumber();
        while (i < 5){
            while (j < 5){
                int k = j;
                while (k !=0 && (blocks[i][k] != 0 && blocks[i][k-1] == 0)){
                    blocks[i][k-1] = blocks[i][k];
                    blocks[i][k] = 0;
                    ismoved = true;
                    k = k -1;
                }
                j = j + 1;
            }
            j = 1;
            i = i + 1;
        }
        mergeup();
        if (ismoved == true){
            addnumber();
            ismoved = false;
        }
        invalidate();
    }
    public void MovetoDown(){
        int i = 0;
        int j = 3;
        removeOddnumber();
        while (i < 5){
            while (j >= 0){
                int k = j;
                while (k != 4 &&(blocks[i][k] != 0 && blocks[i][k+1] == 0)){
                    blocks[i][k+1] = blocks[i][k];
                    blocks[i][k] = 0;
                    ismoved = true;
                    k = k + 1;
                }
                j = j - 1;
            }
            j = 3;
            i = i + 1;
        }
        mergedown();
        if (ismoved == true){
            addnumber();
            ismoved = false;
        }
        invalidate();
    }
    public void addnumber(){
        ArrayList<Integer> emp = getempty();
        if (emp.size() != 0){
            int i = (int) (Math.random() * (emp.size() - 1));
            double j = (Math.random() * 10);
            while (i % 2 != 0) {
                i = (int) (Math.random() * (emp.size() - 1));
            }
            if (j <= 2.5){
                blocks[emp.get( i )][emp.get( i + 1 )] = 11;
            }
            else if (j > 2.5){
                blocks[emp.get( i )][emp.get( i + 1 )] = 3;
            }
        }
    }
    public ArrayList<Integer> getempty(){
        int i = 0;
        int j = 0;
        ArrayList<Integer> result = new ArrayList<>( );
        while (i < 5){
            while (j < 5){
                if (blocks[i][j] == 0){
                    result.add(i);
                    result.add(j);
                }
                j = j + 1;
            }
            j = 0;
            i = i + 1;
        }
        return result;
    }
    public void mergeleft(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j <5; j++){
                if (blocks[i][j] != 0 && i != 4){
                    if (blocks[i][j] == blocks[i+1][j]){
                        score = score + blocks[i][j];
                        blocks[i][j] = (blocks[i][j] *2) + 1;
                        ismoved = true;
                        int k = i+1;
                        while (k < 4){
                            blocks[k][j] = blocks[k+1][j];
                            k = k + 1;
                        }
                        blocks[4][j] = 0;
                    }
                }
            }
        }
    }
    public void mergeright(){
        for (int i = 4; i >=0; i--){
            for (int j = 0; j < 5; j++){
                if (blocks[i][j] != 0 && i != 0){
                    if (blocks[i][j] == blocks[i-1][j]){
                        score = score + blocks[i][j];
                        blocks[i][j] = (blocks[i][j] *2) + 1;
                        ismoved = true;
                        int k = i - 1;
                        while (k > 0){
                            blocks[k][j] = blocks[k-1][j];
                            k = k - 1;
                        }
                        blocks[0][j] = 0;
                    }
                }
            }
        }
    }
    public void mergeup(){
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 5; i++){
                if (blocks[i][j] != 0 && j != 4){
                    if (blocks[i][j] == blocks[i][j+1]){
                        score = score + blocks[i][j];
                        blocks[i][j] = (blocks[i][j] *2) + 1;
                        ismoved = true;
                        int k = j + 1;
                        while (k < 4){
                            blocks[i][k] = blocks[i][k+1];
                            k = k + 1;
                        }
                        blocks[i][4] = 0;
                    }
                }
            }
        }
    }
    public void mergedown(){
        for (int j = 4; j >= 0; j--){
            for (int i = 0; i < 5; i++){
                if (blocks[i][j] != 0 && j != 0){
                    if (blocks[i][j] == blocks[i][j-1]){
                        score = score + blocks[i][j];
                        blocks[i][j] = (blocks[i][j] *2) + 1;
                        ismoved = true;
                        int k = j - 1;
                        while (k > 0){
                            blocks[i][k] = blocks[i][k-1];
                            k = k - 1;
                        }
                        blocks[i][0] = 0;
                    }
                }
            }
        }
    }
    public int getScore(){
        return score;
    }
    public void removeOddnumber(){
        for(int i = 0; i < 5; i ++){
            for(int j = 0; j < 5; j++){
                if (blocks[i][j] == 11){
                    blocks[i][j] = 4;
                }
                if (blocks[i][j] % 2 != 0){
                    blocks[i][j] = blocks[i][j] - 1;
                }
            }
        }
    }
}

