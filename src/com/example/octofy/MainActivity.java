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

        header = (TextView) findViewById(R.id.header);
        header.setText("The Rorschach carousel!");

        tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Blue seastar", 3, Color.rgb(0,0,210)));
        tagList.add(new Tag("Orange flower", 5));
        tagList.add(new Tag("Green seastar", 10, Color.rgb(0,240,0)));
        tagList.add(new Tag("Blue-yellow flower", 7));
        tagList.add(new Tag("Yellow seastar", 7));
        tagList.add(new Tag("Red-yellow flower", 3));
        tagList.add(new Tag("Purple seastar", 1, Color.rgb(150, 0, 90)));
        tagList.add(new Tag("Green flower", 15));
        tagList.add(new Tag("Pink seastar", 7));
        tagList.add(new Tag("Orange flower", 6, Color.rgb(255,165,0)));

        tagCloud = (TagCloud) findViewById(R.id.tagcloud);
        tagCloud.setData(tagList);

        //Possible edits
        tagCloud.addTag(new Tag("Grizzlybears", 1));
        tagCloud.setMinFontSize(10);
        tagCloud.setMaxFontSize(32);
        tagCloud.setTagPadding(2,7,2,7);


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
