package com.example.octofy;

import android.graphics.Color;

/**
 * <h1>Tag</h1>
 * Tag class holding information of a tag used in a tag cloud.
 * @see com.example.octofy.TagCloud
 */
public class Tag implements Comparable<Tag> {

    /**
     * The text of the tag.
     */
    private String text;
    /**
     * The count (for example times used, importance or click count) of the tag.
     */
    private int count;
    /**
     * The image path to a tag
     */
    private String imgPath;
    /**
     * The color of the tag, default value is black color.
     */
    private int color = Color.rgb(0,0,0); // default

    /**
     * Public class constructor.
     *
     * @param text The text of the tag.
     * @param count The count  of the tag.
     */
    public Tag (String text, int count) {
        this.text = text;
        this.count = count;
    }

    /**
     * Public class constructor.
     *
     * @param text The text of the tag.
     * @param count The count  of the tag.
     * @param imgPath The path of the tag image.
     */
    public Tag (String text, int count, String imgPath) {
        this.text = text;
        this.count = count;
        this.imgPath = imgPath;
    }

    /**
     * Public class constructor.
     *
     * @param text The text of the tag.
     * @param count The count of the tag.
     * @param color The color of the tag.
     */
    public Tag (String text, int count, int color) {
        this.text = text;
        this.count = count;
        this.color = color;
    }

    /**
     * @return Text of the tag.
     */
    public String  getText() {
        return text;
    }

    /**
     * @return The count of the tag.
     */
    public int getCount() {
        return count;
    }

    /**
     * @return The color of the tag.
     */
    public int getColor() {
        return color;
    }

    /**
     * @return The image path of the tag.
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * @param text Text to set on the tag.
     */
    public void setText (String text) {
        this.text = text;
    }

    /**
     * @param count Count to set on the tag.
     */
    public void setCount (int count) {
        this.count = count;
    }

    /**
     * @param color Color to set on the tag.
     */
    public void setColor (int color) {
        this.color = color;
    }

    /**
     * @param imgPath Path to set on the image of the tag.
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Comparator implemented through comparable.
     * @param t2 The tag to compare this to
     * @return Value correspondent to the comparison.
     *
     * @see java.lang.Comparable
     */
    @Override
    public int compareTo(Tag t2) {
        if (this.count > t2.getCount())
            return 1;
        if (this.count == t2.getCount())
            return 0;
        return -1;
    }
}