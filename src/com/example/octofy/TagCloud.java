package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.*;

public class TagCloud extends LinearLayout{

    ArrayList<Tag> tagArrayList;
    Context context;
    int maxFontSize = 30; // default
    int minFontSize = 6; // default
    private int paddingTop = 1; // default
    private int paddingRight = 5; // default
    private int paddingLeft = 1; // default
    private int paddingBottom = 5; // default

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

        removeAllViews();

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        windowWidth = metrics.widthPixels;

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);

        int i = 0;
        while (i < tagArrayList.size()) {

            out = tagArrayList.get(i).getText();
            fontSize = getTagTextSize(tagArrayList.get(i).getTextSize());

            TextView tv = new TextView(context);
            tv.setText(out);
            tv.setTextSize(fontSize);
            tv.setTextColor(tagArrayList.get(i).getColor());
            tv.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

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
            i++;
        }
        addView(ll);
    }

    public void setData (ArrayList<Tag> tagArrayList) {
        this.tagArrayList = tagArrayList;
        updateTags();
    }

    public void setMaxFontSize (int maxFontSize) {
        this.maxFontSize = maxFontSize;
        updateTags();
    }

    public void setMinFontSize (int minFontSize) {
        this.minFontSize = minFontSize;
        updateTags();
    }

    public void setTagPadding (int t, int r, int b, int l) {
        this.paddingTop = t;
        this.paddingRight = r;
        this.paddingBottom = b;
        this.paddingLeft = l;
        updateTags();
    }

    public void addTag (Tag t) {
        tagArrayList.add(t);
        updateTags();
    }

    private double getMax() {
        Tag t = Collections.max(tagArrayList);
        return t.getTextSize();

    }

    private double getMin() {
        Tag t = Collections.min(tagArrayList);
        return t.getTextSize();
    }

    private int getTagTextSize (int value) {
        double norm = (value - getMin()) / (getMax() - getMin());
        double size = (minFontSize*(1-norm)) + norm*maxFontSize;
        return (int) size;
    }
}
