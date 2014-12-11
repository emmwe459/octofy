package com.example.octofy;

import android.app.Activity;
import android.os.Bundle;

public class Lab3 extends Activity {

    private InteractiveSearcher interactiveSearcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_3);

        interactiveSearcher = (InteractiveSearcher)findViewById(R.id.interactive);
    }
}
