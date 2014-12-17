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
	private int index;
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
        
        index = 0;
        
        button_left = (Button) findViewById(R.id.goLeft);
        button_left.setEnabled(false);
        button_right = (Button) findViewById(R.id.goRight);
        
        button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				index--;
				
				if(index >= 0 && index <= carouselAdapter.getTotLength() - numOfImagesToShow) {
					
					if(index == carouselAdapter.getTotLength() - numOfImagesToShow - 1) {
						button_right.setEnabled(true);
						button_right.setBackgroundResource(R.drawable.right_arrow);
					}
					
					carouselAdapter.stepLeft();
					carousel.setAdapter(carouselAdapter);
					
					if(index == 0) {
						button_left.setEnabled(false);
						button_left.setBackgroundResource(R.drawable.left_arrow_disabled);
					}
					
				}
				
			}
		});
        
        button_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				index++;
				
				if(index >= 0 && index <= carouselAdapter.getTotLength() - numOfImagesToShow) {
					
					if(index == 1) {
						button_left.setEnabled(true);
						button_left.setBackgroundResource(R.drawable.left_arrow);
					}
					
					carouselAdapter.stepRight();
					carousel.setAdapter(carouselAdapter);
					
					if(index == carouselAdapter.getTotLength() - numOfImagesToShow) {
						button_right.setEnabled(false);
						button_right.setBackgroundResource(R.drawable.right_arrow_disabled);						
					}
					
				}
				
			}
		});
    }
}
