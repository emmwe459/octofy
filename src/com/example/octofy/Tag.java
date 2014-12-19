package com.example.octofy;

import android.graphics.Color;


public class Tag implements Comparable<Tag> {

    private String text;
    private int textSize;
    private int color = Color.rgb(0,0,0); // default

    public Tag (String text, int textSize) {
        this.text = text;
        this.textSize = textSize;
    }

    public Tag (String text, int textSize, int color) {
        this.text = text;
        this.textSize = textSize;
        this.color = color;
    }

    public String  getText() {
        return text;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getColor() {
        return color;
    }

    public void setText (String text) {
        this.text = text;
    }

    public void setTextSize (int textSize) {
        this.textSize = textSize;
    }

    public void setColor (int color) {
        this.color = color;
    }

    @Override
    public int compareTo(Tag another) {
        if (this.textSize > another.getTextSize())
            return 1;
        if (this.textSize == another.getTextSize())
            return 0;
        return -1;
    }
}
