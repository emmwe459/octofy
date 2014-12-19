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
	
	private Context context;
	private String[] tags, imgPaths, subTags, subImgPaths;
	private int[] counts;
    private int firstIndex, numOfImagesToShow;
	
	public CarouselAdapter(Context context, int numOfImagesToShow) {
		this.context = context;
		this.numOfImagesToShow = numOfImagesToShow;
	}
	
	/**
     * Sets tag data for the carousel. 
     *
     * @param  tags array with tag names
     * @param  counts array with tag counts
     * @param  img_paths paths to corresponding tag images, assumed to be in res/drawable
     */
	public void setTagData(String[] tags, int[] counts, String[] imgPaths) {
        this.tags = tags;
        this.counts = counts;
        this.imgPaths = imgPaths;
        init();
    }

	/**
	 * Inits values for adapter.
	 * This includes creating sub array with tag names and sub array with image paths. 
	 */
	private void init() {
        firstIndex = 0;
        subTags = Arrays.copyOfRange(tags,firstIndex,firstIndex+numOfImagesToShow);
        subImgPaths = Arrays.copyOfRange(imgPaths,firstIndex,firstIndex+numOfImagesToShow);
	}

	@Override
	public int getCount() {
        return subTags.length;
	}
	
	/**
	 * Returns the length of the original tag name list.
	 * 
	 * @return the length of the original tag name list
	 */
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
		View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);
        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(subImgPaths[position], "drawable",
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

	/**
	 * Updates the tag object with firstIndex to be at the leftmost in the carousel list.
	 * 
	 * @param firstIndex index for the tag object to be at the leftmost
	 */
    private void setFirstIndex (int firstIndex) {
        this.firstIndex = firstIndex;
    }

    /**
     * Updates sub arrays.
     */
    private void setSubArrays() {
        if(firstIndex >= 0 && firstIndex+numOfImagesToShow <= tags.length) {
            subTags = Arrays.copyOfRange(tags, firstIndex, firstIndex + numOfImagesToShow);
            subImgPaths = Arrays.copyOfRange(imgPaths, firstIndex, firstIndex + numOfImagesToShow);
        }
    }

    /**
     * Called when right button is clicked, to browse to the right in the carousel list. The first index is therefore increased by 1.
     */
    public void browseRight () {
        setFirstIndex(firstIndex+1);
        setSubArrays();
    }

    /**
     * Called when left button is clicked, to browse to the left in the carousel list. The first index is therefore decreased by 1.
     */
    public void browseLeft () {
        setFirstIndex(firstIndex-1);
        setSubArrays();
    }
    
}
