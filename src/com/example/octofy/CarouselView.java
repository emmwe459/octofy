package com.example.octofy;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class CarouselView extends AdapterView {
	
	CarouselListAdapter _carouselListAdapter;

	public CarouselView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Adapter getAdapter() {
		// TODO Auto-generated method stub
		return _carouselListAdapter;
	}

	@Override
	public View getSelectedView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		// TODO Auto-generated method stub
		_carouselListAdapter = (CarouselListAdapter) adapter;
		
	}

	@Override
	public void setSelection(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
