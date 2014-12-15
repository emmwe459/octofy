package com.example.octofy;

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

    HashMap<String, Integer> data;
    TagCloudView tagCloudView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new HashMap<String, Integer>();

        data.put("Flowers", 3);
        data.put("Animals", 5);
        data.put("Parties", 1);
        data.put("People", 0);

        ArrayList<String> easyData = new ArrayList<String>();
        easyData.add("Flower blue");
        easyData.add("Flower red");
        easyData.add("Flower violet");
        easyData.add("Flower green");

        listView = (ListView) findViewById(R.id.listview);
        TagCloudAdapter tagCloudAdapter = new TagCloudAdapter(data, easyData, this);
        listView.setAdapter(tagCloudAdapter);
        //tagCloudView = (TagCloudView) findViewById(R.id.tagcloud);
        //tagCloudView.setAdapter(tagCloudAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
