package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

public class MainActivity extends Activity {
	
    private Carousel carousel;
    public String[] tags;
    public int[] counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        tags = new String[]{"Blue seastar","Orange flower","Green seastar","Blue-yellow flower",
                "Yellow seastar","Red-yellow flower","Purple seastar","Green flower","Pink seastar","Orange flower 2" };

        counts = new int[]{0,0,0,0,0,0,0,0,0,0 };

        carousel = (Carousel) findViewById(R.id.carousel);
        carousel.setData(tags, counts);


    }
}
