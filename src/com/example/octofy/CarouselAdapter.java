package com.example.octofy;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
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
    int firstIndex;
    private String[] subTags, subPaths;
    private int[] subCount;
    private int numOfImagesToShow;
	
	public CarouselAdapter(Context applicationContext, int num) {
		_context = applicationContext;
		numOfImagesToShow = num;
		init();
	}

	private void init() {
		
		tags = new String[]{};
        counts = new int[]{};
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

        firstIndex = 0;
        subTags = Arrays.copyOfRange(tags,firstIndex,firstIndex+numOfImagesToShow);
        subPaths = Arrays.copyOfRange(img_paths,firstIndex,firstIndex+numOfImagesToShow);
        subCount = Arrays.copyOfRange(counts,firstIndex,firstIndex+numOfImagesToShow);
	}

	@Override
	public int getCount() {
        return subTags.length;
	}
	
	public int getTotLength() {
		return tags.length;
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
		View view = LayoutInflater.from(_context).inflate(R.layout.carousel_item, null);
        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(subPaths[position], "drawable",
           parent.getContext().getPackageName());
        view.setBackgroundResource(resourceId);
        
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Increase count for tag
				counts[position+firstIndex]++;
				String message = subTags[position] + " has " + Integer.toString(counts[position+firstIndex]);
				if(counts[position+firstIndex] == 1) {
					message += " vote!";
				} else {
					message += " votes!";
				}
				Toast toast = Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT);
				toast.show();
			}
			
		});
		
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(tags[position+firstIndex]);
        
        return view;
	}

    private void setFirstIndex ( int firstIndex ) {
        this.firstIndex = firstIndex;
    }

    private void setSubs() {
        if(firstIndex >= 0 && firstIndex+numOfImagesToShow <= tags.length) {
            subTags = Arrays.copyOfRange(tags, firstIndex, firstIndex + numOfImagesToShow);
            subPaths = Arrays.copyOfRange(img_paths, firstIndex, firstIndex + numOfImagesToShow);
            subCount = Arrays.copyOfRange(counts, firstIndex, firstIndex + numOfImagesToShow);
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

    public void setData(String[] s, int[] i) {
        tags = s;
        counts = i;
    }
}
