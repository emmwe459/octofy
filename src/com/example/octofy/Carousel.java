package com.example.octofy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Carousel extends LinearLayout {
	
    Context context;
    CarouselList carouselList;
    private Button button_left;
    private Button button_right;
    CarouselAdapter carouselAdapter;

    int numOfImagesToShow;
    int index;


	public Carousel(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Carousel);
        numOfImagesToShow = a.getInteger(R.styleable.Carousel_NumberOfImagesToShow, 7);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.carousel, this, true);

        setOrientation(LinearLayout.VERTICAL);

        init();
	}

    private void init() {
        carouselList = (CarouselList) findViewById(R.id.carousel_list);
        button_left = (Button) findViewById(R.id.goLeft);
        button_left.setEnabled(false);
        button_right = (Button) findViewById(R.id.goRight);

        carouselAdapter = new CarouselAdapter(context, numOfImagesToShow);

        carouselList.setNumOfImagesToShow(numOfImagesToShow);
        carouselList.setAdapter(carouselAdapter);

        index = 0;

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
                    carouselList.setAdapter(carouselAdapter);

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
                    carouselList.setAdapter(carouselAdapter);

                    if(index == carouselAdapter.getTotLength() - numOfImagesToShow) {
                        button_right.setEnabled(false);
                        button_right.setBackgroundResource(R.drawable.right_arrow_disabled);
                    }

                }

            }
        });
    }
}
