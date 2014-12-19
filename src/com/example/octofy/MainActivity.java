package com.example.octofy;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    TextView header;

    private Carousel carousel;
    public String[] tags, img_paths;
    public int[] counts;

    TagCloud tagCloud;
    ArrayList<Tag> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // header
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
        		11,
        		7,
        		3,
        		1,
        		5,
        		10,
        		8,
        		5 };
        
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

        // tag added to data for the tag cloud
        tagList = new ArrayList<Tag>();
        // initiating data for tag cloud, every other is purple (just for fun!)
        for(int i = 0; i < tags.length; i++) {
            if (i%3 == 0) {
                tagList.add(new Tag(tags[i],counts[i], Color.rgb(148,0,211)));
            } else {
                tagList.add(new Tag(tags[i],counts[i]));
            }
        }

        // set up for tag cloud
        tagCloud = (TagCloud) findViewById(R.id.tagcloud);
        tagCloud.setData(tagList);
        
        // set up for carousel
        carousel = (Carousel) findViewById(R.id.carousel);
        carousel.setData(tags, counts, img_paths);

        //Possible edits on the tag cloud
        tagCloud.addTag(new Tag("Grizzlybears", 1, Color.CYAN));
        tagCloud.setMinFontSize(10);
        tagCloud.setMaxFontSize(32);
        tagCloud.setTagPadding(2,7,2,7);
    }
}
