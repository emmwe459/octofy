package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.*;

/**
 * <h1>TagCloud</h1>
 * TagCloud is an extended LinearLayout, built to display a data set of tags.
 * 
 * @see com.example.octofy.Tag
 * @see android.widget.LinearLayout
 */
public class TagCloud extends LinearLayout{

    /**
     * The data set of tags
     */
    ArrayList<Tag> tagArrayList;
    /**
     * Context
     */
    Context context;
    /**
     * Maximum wanted text size of the tag cloud, default value of 30dp.
     */
    int maxFontSize = 30; // default
    /**
     * Minimum wanted text size of the tag cloud, default value of 6dp.
     */
    int minFontSize = 6; // default
    /**
     * Specifies the top padding of each tag in the tag cloud.
     */
    private int tagPaddingTop = 1; // default
    /**
     * Specifies the right padding of each tag in the tag cloud.
     */
    private int tagPaddingRight = 5; // default
    /**
     * Specifies the left padding of each tag in the tag cloud.
     */
    private int tagPaddingLeft = 1; // default
    /**
     * Specifies the bottom padding of each tag in the tag cloud.
     */
    private int tagPaddingBottom = 5; // default

    /**
     * Public class constructor.
     *
     * @param context Context
     * @param attrs Class attributes
     *
     * @see android.content.Context
     * @see android.util.AttributeSet
     */
    public TagCloud(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tag_cloud, this, true);

        setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * Updates the tags on the screen.
     * The arraylist of tags must not be empty.
     * If the array is empty nothing is done.
     * The tags will be drawn with the text and text color specified in the tag object.
     * The font size will be calculated depending of the count of the tag
     * and the count of the rest of the counts in the array.
     * The number of tags on one row depends on the sizes of the tags.
     *
     * @see         Tag
     */
    public void updateTags() {

        if (!tagArrayList.isEmpty()) {

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
                fontSize = getTagTextSize(tagArrayList.get(i).getCount());

                TextView tv = new TextView(context);
                tv.setText(out);
                tv.setTextSize(fontSize);
                tv.setTextColor(tagArrayList.get(i).getColor());
                tv.setPadding(tagPaddingLeft, tagPaddingTop, tagPaddingRight, tagPaddingBottom);

                tv.measure(0, 0);
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
    }
    /**
     * Set or update data in the data set.
     * The tag cloud will be updated after the data set is changed.
     *
     * @param tagArrayList the new data set of tags
     *
     * @see         Tag
     */
    public void setData (ArrayList<Tag> tagArrayList) {
        this.tagArrayList = tagArrayList;
        updateTags();
    }

    /**
     * Set the wanted max font size of the tag cloud.
     * The tag cloud will be updated.
     *
     * @param maxFontSize The wanted maximum text size.
     */
    public void setMaxFontSize (int maxFontSize) {
        this.maxFontSize = maxFontSize;
        updateTags();
    }

    /**
     * Set the wanted min font size of the tag cloud.
     * The tag cloud will be updated.
     *
     * @param minFontSize The wanted minimum text size.
     */
    public void setMinFontSize (int minFontSize) {
        this.minFontSize = minFontSize;
        updateTags();
    }

    /**
     * Set the wanted padding for the tags.
     * The tag cloud will be updated.
     *
     * @param t Top padding
     * @param r Right padding
     * @param b Bottom padding
     * @param l Left padding
     */
    public void setTagPadding (int t, int r, int b, int l) {
        this.tagPaddingTop = t;
        this.tagPaddingRight = r;
        this.tagPaddingBottom = b;
        this.tagPaddingLeft = l;
        updateTags();
    }

    /**
     * Add a new tag to the data set.
     * The tag cloud will be updated.
     *
     * @param t The new tag to be added to the data set
     */
    public void addTag (Tag t) {
        tagArrayList.add(t);
        updateTags();
    }

    /**
     * Get max count value of the tag cloud data set.
     *
     * @return Max value of the count values of the tags.
     */
    private double getMax() {
        Tag t = Collections.max(tagArrayList);
        return t.getCount();

    }

    /**
     * Get min count value of the tag cloud data set.
     *
     * @return Min value of the count values of the tags.
     */
    private double getMin() {
        Tag t = Collections.min(tagArrayList);
        return t.getCount();
    }

    /**
     * Get the appropriate text size depending on the count of the total data set.
     * This is to get a weighted font size between wanted maximum and minimum wanted text size.
     *
     * @param value The count value of the specified tag
     * @return The text size of the specified tag
     */
    private int getTagTextSize (int value) {
        double norm = (value - getMin()) / (getMax() - getMin());
        double size = (minFontSize*(1-norm)) + norm*maxFontSize;
        return (int) size;
    }
}
