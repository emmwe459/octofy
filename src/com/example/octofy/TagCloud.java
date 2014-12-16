package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class TagCloud extends LinearLayout{

    HashMap<String, Integer> data;
    Context context;
    int wordsPerRow = 3; // default

    public TagCloud(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tag_cloud, this, true);

        setOrientation(LinearLayout.VERTICAL);
    }

    public void updateText() {

        // TODO check if empty

        String out;
        int size;
        int rowWidth;
        //getWidth(context);
        int windowWidth = 1080;
        int count = 0;

        removeAllViews();

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
       // LayoutParams lp = new LayoutParams(windowWidth,100);
       // updateViewLayout(ll, lp);
        //LayoutParams lp = (LayoutParams) ll.getLayoutParams();
        //rowWidth = getWidth(context);//lp.width; // måste vara hela screenen !

        for (Map.Entry<String,Integer> entry : data.entrySet()) {

            out = "  " + entry.getKey().toString() + "  ";
            size = (entry.getValue() * 3);

            TextView tv = new TextView(context);
            tv.setText(out);
            tv.setTextSize(size);

            //lineWidth += tv.getWidth();
            Log.d("test", String.valueOf(wordsPerRow));
            //if (lineWidth > windowWidth) {
            if (count % wordsPerRow == 0) {
                addView(ll);
                //create new linear layout
                ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setGravity(Gravity.CENTER_HORIZONTAL);

                ll.addView(tv);
            } else {
                ll.addView(tv);
            }
            count++;
        }

        addView(ll);

    }

    public void setWordsPerRow (int wordsPerRow) {
        this.wordsPerRow = wordsPerRow;
    }

    public void setData (HashMap<String, Integer> data) {
        this.data = data;
    }

    //check out onmeasure!
}
