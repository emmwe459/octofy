package com.example.octofy;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Carousel carousel;
	private Button button_left;
	private Button button_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
                
        carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setAdapter(adapter);
        carousel.setNumOfImagesToShow(4);
        
        button_left = (Button) findViewById(R.id.goLeft);
        button_right = (Button) findViewById(R.id.goRight);
        
        button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("test","button left clicked");
			}
		});
        
        button_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("test","button right clicked");
			}
		});
    }
    
    private static String[] colors = new String[]{
    	"Blue",
    	"Green",
    	"Yellow",
    	"Purple",
    	"Pink",
    	"Blue",
    	"Green",
    	"Yellow",
    	"Purple",
    	"Pink" };
    
    private static String[] img_paths = new String[]{
    	"sea_star1_blue",
    	"sea_star1_green",
    	"sea_star1_yellow",
    	"sea_star1_purple",
    	"sea_star1_pink",
    	"sea_star1_blue",
    	"sea_star1_green",
    	"sea_star1_yellow",
    	"sea_star1_purple",
    	"sea_star1_pink" };
    
    private int[] numOfClicks = new int[]{
    		0,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0 };
    
    private BaseAdapter adapter = new BaseAdapter() {
 
        @Override
        public int getCount() {
            return colors.length;
        }
 
        @Override
        public Object getItem(int position) {
            return null;
        }
 
        @Override
        public long getItemId(int position) {
            return 0;
        }
 
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
        	
        	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, null);
            
            Resources resources = parent.getContext().getResources();
            final int resourceId = resources.getIdentifier(img_paths[position], "drawable", 
               parent.getContext().getPackageName());
            view.setBackgroundResource(resourceId);
            
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// Increase count for tag
					numOfClicks[position]++;
					Toast toast = Toast.makeText(parent.getContext(), "Color " + colors[position] + " has " + Integer.toString(numOfClicks[position]) + " votes!", Toast.LENGTH_SHORT);
					toast.show();
				}
				
			});
			
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(colors[position]);
           
            return view;
        }
        
    };
}
