package com.example.octofy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

/**
 * <h1>CarouselList</h1>
 * CarouselList is an extended AdapterView, containing Views for each tag item in a Carousel.
 * 
 * @see com.example.octofy.CarouselAdapter
 * @see com.example.octofy.Carousel
 */
public class CarouselList extends AdapterView<Adapter> {

	/**
	 * Adapter for the carousel list.
	 */
    private CarouselAdapter carouselAdapter;
    
    /**
     * Screen width of device.
     */
    private final int screenWidth;

    /**
     * Public class constructor.
     * 
     * @param context Context
     * @param attrs Attributes
     */
    public CarouselList(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
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
        removeAllViewsInLayout();
        requestLayout();
    }

    /**
     * Get views and add them to layout.
     */
    @Override
    protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        fillList();
    }

    /**
     * Calculates width and height for each child view, and adds them to the layout.
     */
    private void fillList() {
    	
    	View tagItem;
    	int usedSpace = 0;
    	
    	for(int i = 0; i < carouselAdapter.getCount(); i++) {
    		tagItem = carouselAdapter.getView(i, null, this);
    		
    		// measure item
    		addAndMeasureChild(tagItem);
    		
    		int childHeight = tagItem.getMeasuredHeight();
            int childWidth = tagItem.getMeasuredWidth();
            
            // calculate width for each item in order to fit the desired number of items
            int forcedWidth = screenWidth/carouselAdapter.getNumOfImagesToShow();
            
            // calculate ratio to scale image
            double ratio = (double) forcedWidth/(double) childWidth;
            
            // position item
            tagItem.layout(usedSpace, 0, usedSpace + forcedWidth, (int) (childHeight*ratio));
            
            usedSpace += forcedWidth;
    	}
    }

    /**
     * Adds view to layout and measures it.
     * 
     * @param tagItem item to be measured
     */
    private void addAndMeasureChild(final View tagItem) {
    	
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // second parameter set to -1 in order to add the view in the end of the list
        addViewInLayout(tagItem, -1, params, true);                
        tagItem.measure(0,0);
        
    }

}
