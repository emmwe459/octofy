package com.example.octofy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * <h1>Carousel</h1>
 * Carousel is an extended LinearLayout, built to display a set of images.
 * A specified number of images are displayed and a the user can browse through all the images.
 * The class contains a set of arrow buttons and a list of images.
 *
 * @see com.example.octofy.CarouselList
 * @see com.example.octofy.CarouselAdapter
 * @see android.widget.LinearLayout
 */
public class Carousel extends LinearLayout {

    /**
     * Context
     */
    private Context context;
    /**
     * The list containing the images.
     */
    private CarouselList carouselList;
    /**
     * Button to browse left.
     */
    private Button button_left;
    /**
     * Button to browse right.
     */
    private Button button_right;
    /**
     * Adapter used to display the images of the CarouselList
     */
    private CarouselAdapter carouselAdapter;

    /**
     * The set number of images to show at one time in the carousel.
     * Specified in the XML layout file. Default value is 5 images.
     */
    private int numOfImagesToShow;
    /**
     * The index used to iterate over the images to display.
     * The index denotes the rightmost image of the current display.
     */
    private int browsingIndex;

    /**
     * Public class constructor.
     *
     * @param context Context
     * @param attrs Attributes
     */
	public Carousel(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context = context;

        // retrieve number of images to show in the carousel, which can be set in file 'res/layout/activity_main.xml'
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Carousel);
        numOfImagesToShow = a.getInteger(R.styleable.Carousel_NumberOfImagesToShow, 5);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.carousel, this, true);

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

        carouselList = (CarouselList) findViewById(R.id.carousel_list);
        carouselList.setAdapter(carouselAdapter);

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
     * @param  tags  carouselTagList
     */
    public void setData(ArrayList<Tag> carouselTagList) {

        carouselAdapter = new CarouselAdapter(context, numOfImagesToShow, carouselTagList);
        carouselAdapter.setTagData(carouselTagList);
        carouselAdapter.notifyDataSetChanged();

        // initiating values for the carousel object
        init();
    }

    public void setAdapter(CarouselAdapter carouselAdapter) {
        this.carouselAdapter = carouselAdapter;
        carouselList.setAdapter(carouselAdapter);
    }
}
