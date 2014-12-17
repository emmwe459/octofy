package com.example.octofy;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

public class Carousel extends AdapterView<Adapter> {
	
	private Adapter carouselAdapter;
	private int numOfImagesToShow;
	private int screenWidth;
	private int leftViewIndex;
	private int rightViewIndex;
	private int maxX;
	private int currentX;
	private Queue<View> removedViewQueue = new LinkedList<View>();

	public Carousel(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		screenWidth = metrics.widthPixels;
		leftViewIndex = -1;
		rightViewIndex = 0;
		currentX = 0;
		maxX = Integer.MAX_VALUE;
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
		carouselAdapter = adapter;
        requestLayout();
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
		
		fillList();
		left = 0;
		
		for(int i = 0;i < numOfImagesToShow;i++){
			
			View child = getChildAt(i);
			
			if(child != null) {
				int childHeight = child.getMeasuredHeight();
				int childWidth = child.getMeasuredWidth();
				int forcedWidth = screenWidth/numOfImagesToShow;
				double ratio = (double) forcedWidth/(double) childWidth;
				child.layout(left, 0, left + forcedWidth, (int) (childHeight*ratio));
				left += forcedWidth;
				Log.d("test","child weight: " + childWidth);
			} else {
				Log.d("test","child nr: " + Integer.toString(i+1) + " is null");
			}
			
		}
	}
	
	private void fillList() {
		int edge = 0;
		View child = getChildAt(getChildCount()-1);
		Log.d("test","getchildcount: " + Integer.toString(getChildCount()));
		if(child != null) {
			edge = child.getMeasuredWidth();
		}
		fillListRight(edge);
		
		edge = 0;
		child = getChildAt(0);
		if(child != null) {
			edge = child.getLeft();
		}
		fillListLeft(edge);
	}
	
	private void fillListRight(int rightEdge) {
		while(rightEdge < getWidth() && rightViewIndex < carouselAdapter.getCount()) {
			View child = carouselAdapter.getView(rightViewIndex, removedViewQueue.poll(), this);
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
	
	private void fillListLeft(int leftEdge) {
		while(leftEdge > 0 && leftViewIndex >= 0) {
			View child = carouselAdapter.getView(leftViewIndex, removedViewQueue.poll(), this);
			addAndMeasureChild(child, 0);
			leftEdge -= child.getMeasuredWidth();
			leftViewIndex--;
		}
	}

	private void addAndMeasureChild(final View child, int viewPos) {
		//Log.d("test","inside addandmeasure --> viewPos: " + viewPos);
		LayoutParams params = child.getLayoutParams();
		if(params == null) {
			params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

		addViewInLayout(child, viewPos, params, true);
		child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
				MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
	}
	 
	private void removeNonVisibleItems(final int dx) {
		View child = getChildAt(0);
		while(child != null && child.getRight() + dx <= 0) {
			removedViewQueue.offer(child);
			removeViewInLayout(child);
			leftViewIndex++;
			child = getChildAt(0);
		}
		
		child = getChildAt(getChildCount()-1);
		while(child != null && child.getLeft() + dx >= getWidth()) {
			removedViewQueue.offer(child);
			removeViewInLayout(child);
			rightViewIndex--;
			child = getChildAt(getChildCount()-1);
		}
	}
}
