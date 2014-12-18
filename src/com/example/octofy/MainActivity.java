package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends Activity {

    TextView header;

    private Carousel carousel;
    public String[] tags, img_paths;
    public int[] counts;

    TagCloud tagCloud;
    HashMap<String, Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = (TextView) findViewById(R.id.header);
        header.setText("The Rorschach carousel!");

        data = new HashMap<String, Integer>();
        data.put("Blue seastar", 3);
        data.put("Orange flower", 5);
        data.put("Green seastar", 1);
        data.put("Blue-yellow flower", 7);
        data.put("Yellow seastar", 3);
        data.put("Red-yellow flower", 5);
        data.put("Purple seastar", 5);
        data.put("Green flower", 10);
        data.put("Pink seastar", 8);
        data.put("Orange flower", 11);

        tagCloud = (TagCloud) findViewById(R.id.tagcloud);
        tagCloud.setData(data);
        tagCloud.updateTags();
        tagCloud.addTag("Grizzlybears", 1);
        tagCloud.setMinFontSize(10);


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
