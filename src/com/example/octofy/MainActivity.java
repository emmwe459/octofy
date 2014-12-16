package com.example.octofy;

import android.nfc.Tag;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    TagCloud tagCloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, Integer> data = new HashMap<String, Integer>();
        data.put("Flowers", 3);
        data.put("Animals", 5);
        data.put("Parties", 1);
        data.put("People", 7);
        data.put("Pigs", 3);
        data.put("Seastars", 5);
        data.put("Phones", 5);
        data.put("Hyppopotamus", 10);
        data.put("Balloons", 8);
        data.put("Octupie", 10);

        tagCloud = (TagCloud) findViewById(R.id.tagcloud);
        tagCloud.setData(data);
        tagCloud.setWordsPerRow(3);
        tagCloud.updateText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
