package com.example.octofy;

import java.util.Arrays;

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
    int firstIndex = 0;
    private String[] subTags, subPaths;
    private int[] subCount;
	
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
		    	"Pink",
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
				"sea_star1_pink",
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
				0,
				0,
				0,
				0,
				0,
				0 };

        subTags = Arrays.copyOfRange(tags,firstIndex,firstIndex+3);
        subPaths = Arrays.copyOfRange(img_paths,firstIndex,firstIndex+3);
        subCount = Arrays.copyOfRange(counts,firstIndex,firstIndex+3);
	}

	@Override
	public int getCount() {
        return subTags.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		Log.d("test","child pos: " + position);
		View view = LayoutInflater.from(_context).inflate(R.layout.carousel_item, null);
        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(subPaths[position], "drawable",
           parent.getContext().getPackageName());
        view.setBackgroundResource(resourceId);
        
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Increase count for tag
				subCount[position]++;
				Toast toast = Toast.makeText(parent.getContext(), "Color " + subTags[position] + " has " + Integer.toString(subCount[position]) + " votes!", Toast.LENGTH_SHORT);
				toast.show();
			}
			
		});
		
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(tags[position]);
        
        return view;
	}

    private void setFirstIndex ( int firstIndex ) {
        this.firstIndex = firstIndex;
    }

    private void setSubs() {
        if(firstIndex+3 < tags.length) {
            subTags = Arrays.copyOfRange(tags, firstIndex, firstIndex + 3);
            subPaths = Arrays.copyOfRange(img_paths, firstIndex, firstIndex + 3);
            subCount = Arrays.copyOfRange(counts, firstIndex, firstIndex + 3);
        }
    }

    public void stepRight () {
        setFirstIndex(firstIndex+1);
        setSubs();
    }

    public void stepLeft () {
        setFirstIndex(firstIndex-1);
        setSubs();
    }
}
