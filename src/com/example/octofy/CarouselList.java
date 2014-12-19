package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class CarouselList extends AdapterView<Adapter> {

    private CarouselAdapter carouselAdapter;

    public int leftViewIndex;
    public int rightViewIndex;
    public int nextX;
    public int currentX;
    public final int screenWidth;

    private int numOfImagesToShow;
    private int displayOffset;
    private int maxX;

    public CarouselList(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        
        initView();
    }

    private synchronized void initView() {
        leftViewIndex = -1;
        rightViewIndex = 0;
        nextX = 0;
        currentX = 0;
        maxX = Integer.MAX_VALUE;
    }

    private synchronized void reset(){
        initView();
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int arg0) {
    }

    @Override
    public Adapter getAdapter() {
        return carouselAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        carouselAdapter = (CarouselAdapter) adapter;
        reset();
    }

    public int getNumOfImagesToShow() {
        return numOfImagesToShow;
    }

    public void setNumOfImagesToShow(int num) {
        numOfImagesToShow = num;
    }

    @Override
    protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(carouselAdapter == null) {
            return;
        }

        int dx = currentX - nextX;

        fillList(dx);
        positionItems(dx);

        currentX = nextX;
    }

    private void fillList(final int dx) {

        int edge = 0;
        View child = getChildAt(getChildCount()-1);
        if(child != null) {
            edge = child.getRight();
        }
        fillListRight(edge, dx);

        edge = 0;
        child = getChildAt(0);
        if(child != null) {
            edge = child.getLeft();
        }
        fillListLeft(edge, dx);
    }

    private void fillListRight(int rightEdge, final int dx) {

        while(rightEdge + dx < getWidth() && rightViewIndex < carouselAdapter.getCount()) {
            View child = carouselAdapter.getView(rightViewIndex, null, this);
            addAndMeasureChild(child, -1);
            rightEdge += child.getMeasuredWidth();

            if(rightViewIndex == carouselAdapter.getCount()-1) {
                maxX = currentX + rightEdge - getWidth();
            }

            if (maxX < 0) {
                maxX = 0;
            }
            rightViewIndex++;
        }

    }

    private void fillListLeft(int leftEdge, final int dx) {
        while(leftEdge > 0 && leftViewIndex >= 0) {
            View child = carouselAdapter.getView(leftViewIndex, null, this);
            addAndMeasureChild(child, 0);
            leftEdge -= child.getMeasuredWidth();
            leftViewIndex--;
        }
    }

    private void positionItems(final int dx) {

        if(getChildCount() > 0){
            displayOffset += dx;
            int left = displayOffset;
            for(int i=0;i<numOfImagesToShow;i++){

                View child = getChildAt(i);

                int childHeight = child.getMeasuredHeight();
                int childWidth = child.getMeasuredWidth();
                int forcedWidth = screenWidth/numOfImagesToShow;
                double ratio = (double) forcedWidth/(double) childWidth;
                child.layout(left, 0, left + forcedWidth, (int) (childHeight*ratio));
                left += forcedWidth;
            }
        }
    }

    private void addAndMeasureChild(final View child, int viewPos) {
        LayoutParams params = child.getLayoutParams();
        if(params == null) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        addViewInLayout(child, viewPos, params, true);

        child.measure(0, 0);

        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int forcedWidth = screenWidth/numOfImagesToShow;
        double ratio = (double) forcedWidth / (double) w;

        child.measure(MeasureSpec.makeMeasureSpec(forcedWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec((int) (h*ratio), MeasureSpec.AT_MOST));
    }

}
