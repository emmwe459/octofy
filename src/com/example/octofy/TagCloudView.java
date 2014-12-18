package com.example.octofy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Map;

/**
 * Not used at the moment / dec 17
 */
public class TagCloudView extends AdapterView {

    Context context;
    Adapter adapter;

    public TagCloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw (Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        canvas.drawText("Tagitag", 10, 10, p);
        //View v = adapter.getView(1,this,this);
        //this.addView(v);
   }

    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        //removeAllViewsInLayout();
        invalidate();
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {

        canvas.save();
        super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return false;
    }

}
