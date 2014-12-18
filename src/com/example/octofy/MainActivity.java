package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

public class MainActivity extends Activity {
	
    private Carousel carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        carousel = (Carousel) findViewById(R.id.carousel);

    }
}
