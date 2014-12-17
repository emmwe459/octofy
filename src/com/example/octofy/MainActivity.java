package com.example.octofy;

import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Carousel carousel;
	private Button button_left;
	private Button button_right;
	private int numOfImagesToShow;
	private CarouselAdapter carouselAdapter;
	private int rightIndex, leftIndex;
	private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
        
        numOfImagesToShow = 4;
        
        /*listview = (ListView) findViewById(R.id.listview);
        carouselAdapter = new CarouselAdapter(this.getBaseContext());
        listview.setAdapter(carouselAdapter);*/
        
        carouselAdapter = new CarouselAdapter(this.getBaseContext(), numOfImagesToShow);
                
        carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setNumOfImagesToShow(numOfImagesToShow);
        carousel.setAdapter(carouselAdapter);
        
        rightIndex = carousel.rightViewIndex;
        leftIndex = carousel.leftViewIndex;
        
        Log.d("test","screen width: " + carousel.screenWidth);
        
        Log.d("test","right: " + rightIndex + ", left: " + leftIndex);
        
        button_left = (Button) findViewById(R.id.goLeft);
        button_right = (Button) findViewById(R.id.goRight);
        
        button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

                carouselAdapter.stepLeft();
                carousel.setAdapter(carouselAdapter);
				
				if(leftIndex >= 0) {
					rightIndex--;
					leftIndex--;
					
					if(leftIndex < 0) {
						button_left.setBackgroundResource(R.drawable.left_arrow_disabled);
						button_left.setEnabled(false);
					}
					
					if(rightIndex < carouselAdapter.getCount()) {
						button_right.setBackgroundResource(R.drawable.right_arrow);
						button_right.setEnabled(true);
					}
					
					//carousel.requestLayout();
				}
	
				
			}
		});
        
        button_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

                carouselAdapter.stepRight();
                carousel.setAdapter(carouselAdapter);


				if(rightIndex < carouselAdapter.getCount()-1) {
					rightIndex++;
					leftIndex++;
					
					if(rightIndex >= carouselAdapter.getCount()-1) {
						button_right.setBackgroundResource(R.drawable.right_arrow_disabled);
						button_right.setEnabled(false);
					}
					
					if(leftIndex >= 0) {
						button_left.setBackgroundResource(R.drawable.left_arrow);
						button_left.setEnabled(true);
					}
					
					//carousel.setAdapter(carouselAdapter);//requestLayout();
				}
			}
		});
    }
}
