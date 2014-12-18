package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
	
    private Carousel carousel;
    public String[] tags, img_paths;
    public int[] counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        tags = new String[]{"Blue seastar","Orange flower","Green seastar","Blue-yellow flower",
                "Yellow seastar","Red-yellow flower","Purple seastar","Green flower","Pink seastar","Orange flower 2" };

        counts = new int[]{0,0,0,0,0,0,0,0,0,0 };
        img_paths = new String[]{
                "sea_star1_blue",
                "flower1",
                "sea_star1_green",
                "flower2",
                "sea_star1_yellow",
                "flower3",
                "sea_star1_purple",
                "flower4",
                "sea_star1_pink",
                "flower5" };

        carousel = (Carousel) findViewById(R.id.carousel);
        carousel.setData(tags, counts, img_paths);
    }
}
