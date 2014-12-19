package com.example.octofy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Carousel extends LinearLayout {
	
    private Context context;
    private CarouselList carouselList;
    private Button button_left;
    private Button button_right;
    private CarouselAdapter carouselAdapter;

    private int numOfImagesToShow;
    private int browsingIndex;

	public Carousel(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context = context;

        // retrieve number of images to show in the carousel, which can be set in file 'res/layout/activity_main.xml'
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Carousel);
        numOfImagesToShow = a.getInteger(R.styleable.Carousel_NumberOfImagesToShow, 7);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.carousel, this, true);

        // initiating values for the carousel object
        init();
	}
	
	/**
	 * Inits values for a carousel object.
	 * This includes the horizontal list, buttons for browsing the list, a carousel adapter and number of images to show.
	 * Also, OnClickListeners are set for the buttons. 
	 */
    private void init() {
    	
    	// set the first image (with index 0) to be the one showing leftmost in the carousel list
        browsingIndex = 0;
        
        button_left = (Button) findViewById(R.id.goLeft);
        button_left.setEnabled(false);
        button_right = (Button) findViewById(R.id.goRight);

        carouselAdapter = new CarouselAdapter(context, numOfImagesToShow);
        
        carouselList = (CarouselList) findViewById(R.id.carousel_list);
        carouselList.setAdapter(carouselAdapter);
        carouselList.setNumOfImagesToShow(numOfImagesToShow);

        // OnClickListener on button for browsing left in the carousel list
        button_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                browsingIndex--;

                if(browsingIndex >= 0 && browsingIndex <= carouselAdapter.getTotLength() - numOfImagesToShow) {

                    if(browsingIndex == carouselAdapter.getTotLength() - numOfImagesToShow - 1) {
                        button_right.setEnabled(true);
                        button_right.setBackgroundResource(R.drawable.right_arrow);
                    }

                    carouselAdapter.browseLeft();
                    carouselList.setAdapter(carouselAdapter);

                    if(browsingIndex == 0) {
                        button_left.setEnabled(false);
                        button_left.setBackgroundResource(R.drawable.left_arrow_disabled);
                    }

                }

            }
        });

        // OnClickListener on button for browsing right in the carousel list
        button_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                browsingIndex++;

                if(browsingIndex >= 0 && browsingIndex <= carouselAdapter.getTotLength() - numOfImagesToShow) {

                    if(browsingIndex == 1) {
                        button_left.setEnabled(true);
                        button_left.setBackgroundResource(R.drawable.left_arrow);
                    }

                    carouselAdapter.browseRight();
                    carouselList.setAdapter(carouselAdapter);

                    if(browsingIndex == carouselAdapter.getTotLength() - numOfImagesToShow) {
                        button_right.setEnabled(false);
                        button_right.setBackgroundResource(R.drawable.right_arrow_disabled);
                    }

                }

            }
        });
        
    }

    /**
     * Calls method setTagData in CarouselAdapter to set tag data for the carousel. 
     *
     * @param  tags  array with tag names
     * @param  counts array with tag counts
     * @param  img_paths paths to corresponding tag images, assumed to be in res/drawable
     */
    public void setData(String[] tags, int[] counts, String[] img_paths) {
        carouselAdapter.setTagData(tags,counts, img_paths);
        carouselAdapter.notifyDataSetChanged();
    }
}
