package com.example.octofy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by linneanabo on 2014-12-16.
 */
public class RowView extends View {

    private String text;
    Paint paint = new Paint();

    public RowView(Context context) {
        super(context);
    }

    public RowView(Context context, AttributeSet attr) {
        super(context,attr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = 0;//this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int textsize = height*4/3;

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(textsize);
        paint.setARGB(255, 81,185,90);//51b95a
        paint.setLinearText(true);
        canvas.drawText(text, width, height, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = widthMeasureSpec;
        int height = heightMeasureSpec;
        setMeasuredDimension(width, height);
    }

    public void setText (String s) {
        this.text = s;
    }
}
