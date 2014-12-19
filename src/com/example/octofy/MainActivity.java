package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
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
        header.setTextColor(Color.BLUE);
        
        // tag names
        tags = new String[]{
        		"Blue seastar",
        		"Orange flower",
        		"Green seastar",
        		"Blue-yellow flower",
                "Yellow seastar",
                "Red-yellow flower",
                "Purple seastar",
                "Green flower",
                "Pink seastar",
                "Super orange flower" };
        
        // tag counters, i.e. how many times the image corresponding to the tag has been clicked in the carousel
        counts = new int[]{
        		3,
        		5,
        		1,
        		7,
        		3,
        		5,
        		5,
        		10,
        		8,
        		11 };
        
        // names for the image corresponding to each tag
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

        // initiating data for tag cloud
        data = new HashMap<String, Integer>();
        
        for(int i = 0; i < tags.length; i++) {
        	data.put(tags[i], counts[i]);
        }

        // set up for tag cloud
        tagCloud = (TagCloud) findViewById(R.id.tagcloud);
        tagCloud.setData(data);
        tagCloud.updateTags();
        tagCloud.addTag("Grizzlybears", 1);
        tagCloud.setMinFontSize(10);
        
        // set up for carousel
        carousel = (Carousel) findViewById(R.id.carousel);
        carousel.setData(tags, counts, img_paths);
    }
}
