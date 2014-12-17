package com.example.octofy;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CarouselAdapter extends BaseAdapter {
	
	private Context _context;
	private String[] tags, img_paths;
	private int[] counts;
	
	public CarouselAdapter(Context applicationContext) {
		_context = applicationContext;
		init();
	}

	private void init() {
		tags = new String[]{
				"Blue",
		    	"Green",
		    	"Yellow",
		    	"Purple",
		    	"Pink" };
		
		img_paths = new String[]{
				"sea_star1_blue",
				"sea_star1_green",
				"sea_star1_yellow",
				"sea_star1_purple",
				"sea_star1_pink" };
		
		counts = new int[]{
				0,
				0,
				0,
				0,
				0 };
	}

	@Override
	public int getCount() {
		return tags.length;
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
		Log.d("test","inside getview");
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, null);
        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(img_paths[position], "drawable", 
           parent.getContext().getPackageName());
        view.setBackgroundResource(resourceId);
        
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Increase count for tag
				counts[position]++;
				Toast toast = Toast.makeText(parent.getContext(), "Color " + tags[position].toLowerCase() + " has " + Integer.toString(counts[position]) + " votes!", Toast.LENGTH_SHORT);
				toast.show();
			}
			
		});
		
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(tags[position]);
        
        return view;
	}

}
