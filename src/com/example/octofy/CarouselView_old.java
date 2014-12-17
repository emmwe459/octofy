package com.example.octofy;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

public class CarouselView_old extends AdapterView<ListAdapter> {
	
	// source: http://www.dev-smart.com/archives/34
	
	int numOfImagesToShow;
	
	ListAdapter listAdapter;
	Scroller scroller;
	int leftViewIndex;
	int rightViewIndex;
	int displayOffset;
	int currentX;
	int nextX;
	int maxX;
	int screenWidth;
	boolean dataChanged;
	GestureDetector gesture;
	Queue<View> removedViewQueue = new LinkedList<View>();
	OnItemSelectedListener onItemSelected;
	OnItemClickListener onItemClicked;
	OnItemLongClickListener onItemLongClicked;

	public CarouselView_old(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		screenWidth = metrics.widthPixels;
		initView();
	}
	
	private synchronized void initView() {
		numOfImagesToShow = 5;
		leftViewIndex = -1;
		rightViewIndex = 0;
		displayOffset = 0;
		currentX = 0;
		nextX = 0;
		maxX = Integer.MAX_VALUE;
		//scroller = new Scroller(getContext());
		//gesture = new GestureDetector(getContext(), onGesture);
	}
	
	@Override
	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
		onItemSelected = listener;
	}
	
	@Override
	public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
		onItemClicked = listener;
	}
	
	@Override
	public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
		onItemLongClicked = listener;
	}

	@Override
	public ListAdapter getAdapter() {
		return listAdapter;
	}

	@Override
	public View getSelectedView() {
		return null;
	}

	public void setAdapter(ListAdapter adapter) {
		listAdapter = adapter;
		//removeAllViewsInLayout();
        //requestLayout();
	}

	@Override
	public void setSelection(int position) {
	}
	
	@Override
	protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		removeNonVisibleItems(0);
		fillList(0);
		left = 0;
		
		for(int i=0;i<numOfImagesToShow;i++){
			View child = getChildAt(i);			
			if(child != null) {
				int childHeight = child.getMeasuredHeight();
				int childWidth = child.getMeasuredWidth();
				int forcedWidth = screenWidth/numOfImagesToShow;
				double ratio = (double) forcedWidth/(double) childWidth;
				child.layout(left, 0, left + forcedWidth, (int) (childHeight*ratio));
				left += forcedWidth;
			} else {
				Log.d("test","child was null, index: " + Integer.toString(i));
			}
		}

		/*if(listAdapter == null){
			return;
		}
		
		if(dataChanged){
			int oldCurrentX = currentX;
			initView();
			removeAllViewsInLayout();
			nextX = oldCurrentX;
			dataChanged = false;
		}

		if(scroller.computeScrollOffset()){
			int scrollx = scroller.getCurrX();
			nextX = scrollx;
		}
		
		if(nextX <= 0){
			nextX = 0;
			//scroller.forceFinished(true);
		}
		if(nextX >= maxX) {
			nextX = maxX;
			//scroller.forceFinished(true);
		}
		
		int dx = currentX - nextX;
		
		removeNonVisibleItems(dx);
		fillList(dx);
		positionItems(dx);
		
		currentX = nextX;
		
		if(!scroller.isFinished()){
			post(new Runnable(){
				@Override
				public void run() {
					requestLayout();
				}
			});
			
		}*/
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
		while(rightEdge + dx < getWidth() && rightViewIndex < listAdapter.getCount()) {
			
			View child = listAdapter.getView(rightViewIndex, removedViewQueue.poll(), this);
			addAndMeasureChild(child, -1);
			rightEdge += child.getMeasuredWidth();
			
			if(rightViewIndex == listAdapter.getCount()-1) {
				maxX = currentX + rightEdge - getWidth();
			}
			
			if (maxX < 0) {
				maxX = 0;
			}
			rightViewIndex++;
		}
		
	}
	
	private void fillListLeft(int leftEdge, final int dx) {
		while(leftEdge + dx > 0 && leftViewIndex >= 0) {
			View child = listAdapter.getView(leftViewIndex, removedViewQueue.poll(), this);
			addAndMeasureChild(child, 0);
			leftEdge -= child.getMeasuredWidth();
			leftViewIndex--;
			displayOffset -= child.getMeasuredWidth();
		}
	}

	private void addAndMeasureChild(final View child, int viewPos) {
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
			displayOffset += child.getMeasuredWidth();
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
	
	private void positionItems(final int dx) {
		if(getChildCount() > 0){
			displayOffset += dx;
			int left = displayOffset;
			for(int i=0;i<getChildCount();i++){
				View child = getChildAt(i);
				int childWidth = child.getMeasuredWidth();
				//child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
				child.layout(left, 50, left + childWidth, child.getMeasuredHeight()+50);
				left += childWidth + child.getPaddingRight();
			}
		}
	}
	
	/*private OnGestureListener onGesture = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			return CarouselView_old.this.onDown(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return CarouselView_old.this.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			synchronized(CarouselView_old.this){
				nextX += (int)distanceX;
			}
			requestLayout();
			
			return true;
		}

	};*/
	
	/*protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		synchronized(CarouselView_old.this){
			scroller.fling(nextX, 0, (int)-velocityX, 0, 0, maxX, 0, 0);
		}
		requestLayout();

		return true;
	}

	protected boolean onDown(MotionEvent e) {
		scroller.forceFinished(true);
		return true;
	}
	
	public synchronized void scrollTo(int x) {
		scroller.startScroll(nextX, 0, x - nextX, 0);
		requestLayout();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = super.dispatchTouchEvent(ev);
		handled |= gesture.onTouchEvent(ev);
		return handled;
	}*/
	
}
