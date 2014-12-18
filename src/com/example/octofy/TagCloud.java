package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TagCloud extends LinearLayout{

    HashMap<String, Integer> data;
    Context context;
    int maxFontSize = 30; // default
    int minFontSize = 6; // default

    public TagCloud(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tag_cloud, this, true);

        setOrientation(LinearLayout.VERTICAL);
    }

    public void updateTags() {

        // TODO check if empty

        String out;
        int fontSize;
        int windowWidth;
        int lineWidth = 0;
        int pLeft = 7;
        int pTop = 2;
        int pRight = 7;
        int pBottom = 2;

        //this.measure(0, 0);

        removeAllViews();

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        windowWidth = metrics.widthPixels;

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);

        for (Map.Entry<String,Integer> entry : data.entrySet()) {

            out = entry.getKey().toString();
            fontSize = getTagTextSize(entry.getValue());

            TextView tv = new TextView(context);
            tv.setText(out);
            tv.setTextSize(fontSize);
            tv.setPadding(pLeft, pTop, pRight, pBottom);

            tv.measure(0,0);
            lineWidth += tv.getMeasuredWidth();

            if (lineWidth > windowWidth) {
                addView(ll);

                //create new linear layout
                ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setGravity(Gravity.CENTER_HORIZONTAL);

                ll.addView(tv);
                lineWidth = tv.getMeasuredWidth();
            } else {
                ll.addView(tv);
            }
        }
        addView(ll);
    }

    public void setData (HashMap<String, Integer> data) {
        this.data = data;
    }

    public void setMaxFontSize (int maxFontSize) {
        this.maxFontSize = maxFontSize;
        updateTags();
    }

    public void setMinFontSize (int minFontSize) {
        this.minFontSize = minFontSize;
        updateTags();
    }

    public void addTag (String s, int i) {
        data.put(s,i);
        updateTags();
    }

    private double getMax() {
        Collection<Integer> c = data.values();
        return Collections.max(c);

    }

    private double getMin() {
        Collection<Integer> c = data.values();
        return Collections.min(c);
    }

    private int getTagTextSize (int value) {
        double norm = (value - getMin()) / (getMax() - getMin());
        double size = (minFontSize*(1-norm)) + norm*maxFontSize;
        return (int) size;
    }


    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }*/
}
