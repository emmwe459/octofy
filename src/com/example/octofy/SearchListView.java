package com.example.octofy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linneanabo on 2014-12-15.
 */
public class SearchListView extends View {

    Paint paint = new Paint();
    Canvas canvas;
    ArrayList<String> names;
    int x, y;

    public SearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        names = new ArrayList<String>();
        this.canvas = canvas;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawText("here" , 20, 20, paint);
        x = 50;
        y = 20;

        // if list of names is not empty, draw
        //if (!names.isEmpty()) {
            for (int i = 0; i < names.size(); i++) {
                canvas.drawText(names.get(i), x, y, paint);
                x = x + 50;
            }
        //}
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public void updateList() {
        draw(this.canvas);
    }
}
