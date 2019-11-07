package com.example.richtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class RectSpan extends ReplacementSpan {

    private RectF mRectF = new RectF();
    static Random random = new Random();
    private Paint mPaint;

    private int[] mColors = new int[20];

    public RectSpan() {
        initPaint();
        initColors();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        mPaint.setAntiAlias(true);
    }

    private void initColors() {
        for(int index = 0 ; index < mColors.length ; index++) {
            mColors[index] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable FontMetricsInt fm) {
        return (int) paint.measureText(text, start, end);
    }


    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        Log.d("RectSpan", text.toString() + " " + start + " " + end);
        float charx = x;
//        参数理解有误
//        for(int i = start ; i<end; i++) {
//            if (text.charAt(start) == '|'){
//                continue;
//            }
//            int tempStart = i;
//            while(text.charAt(i) != '|'){
//                i ++;
//            }
//            int tempEnd = i;
//            String charAt = extractText(text, tempStart, tempEnd);
//            Log.d("dugu", charAt);
//            float charWidth = paint.measureText(charAt);
//            mRectF.set(charx, top, charx += charWidth, bottom);
//            mPaint.setColor(mColors[i % mColors.length]);
////            //根据每个字的位置绘制背景
//            canvas.drawRect(mRectF, mPaint);
//        }

        //绘制背景
        String charAt = extractText(text, start, end);
        Log.d("dugu", charAt);
        float charWidth = paint.measureText(charAt);
        mRectF.set(charx, top, charx + charWidth, bottom);
        mPaint.setColor(mColors[start % mColors.length]);
        canvas.drawRect(mRectF, mPaint);


        //绘制字体，如果不掉用这个函数，就不会显示字体啦。
//        Paint paint1 = paint;
//        paint1.setTextSize(paint1.getTextSize() * 0.5f);
        canvas.drawText(text, start, end, x, y, paint);
    }

    private String extractText(CharSequence text, int start, int end) {
        return text.subSequence(start, end).toString();
    }
}
