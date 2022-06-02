package com.example.ticketpurchase.extendview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CircleView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint myPaint;
    private Bitmap myBitMap;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Drawable drawable = getDrawable();
        if(drawable instanceof BitmapDrawable){
            myBitMap = ((BitmapDrawable) drawable).getBitmap();
        }
    }

    private void init() {
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(myBitMap != null){
            drawCircle(canvas);
        }
        else{
            super.onDraw(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {

        BitmapShader bitmapShader = new BitmapShader(myBitMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        float scaleX = getWidth() / (float) myBitMap.getWidth();
        float scaleY = getHeight() / (float) myBitMap.getHeight();
        matrix.setScale(scaleX, scaleY);

        bitmapShader.setLocalMatrix(matrix);
        myPaint.setShader(bitmapShader);
        canvas.drawCircle(getWidth()/2f, getHeight()/2f, Math.min(getWidth()/2, getHeight()/2), myPaint);

        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        myPaint.reset();
        myPaint.setAntiAlias(true);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(8);
        myPaint.setColor(Color.WHITE);

        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, Math.min(getWidth() / 2f, getHeight() / 2f) - 4, myPaint);
    }
}
