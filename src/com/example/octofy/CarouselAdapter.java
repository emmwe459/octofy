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

/**
 * Adapter to display images in a Carousel.
 * Displays numOfImagesToShow images at one time.
 *
 * @see com.example.octofy.Carousel
 * @see com.example.octofy.CarouselList
 */
public class CarouselAdapter extends BaseAdapter {

    /**
     * Context
     */
	private Context context;
    /**
     * String of tags corresponding to each image.
     */
	private String[] tags;
    /**
     * Image paths to all images;
     */
    private String[] imgPaths;
    /**
     * Subarray of the tag array, containing numOfImagesToShow number of tags.
     */
    private String[] subTags;
    /**
     * Subarray of the image path array, containing numOfImagesToShow number of paths.
     */
    private String[] subImgPaths;

    /**
     * Array containing all the counts of the images,
     * in this case denoting number of clicks on the image.
     */
	private int[] counts;
    /**
     * Index denoting the leftmost displayed image in the whole array.
     */
    private int browseIndex;
    /**
     * The number of images that should be shown at one time.
     */
    private int numOfImagesToShow;

    /**
     * Public class constructor.
     *
     * @param context Context
     * @param numOfImagesToShow The number of images that should be shown at one time.
     */
	public CarouselAdapter(Context context, int numOfImagesToShow) {
		this.context = context;
		this.numOfImagesToShow = numOfImagesToShow;
	}
	
	/**
     * Sets tag data for the carousel. 
     *
     * @param  tags array with tag names
     * @param  counts array with tag counts
     * @param  imgPaths paths to corresponding tag images, assumed to be in res/drawable
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
        browseIndex = 0;
        subTags = Arrays.copyOfRange(tags,browseIndex,browseIndex+numOfImagesToShow);
        subImgPaths = Arrays.copyOfRange(imgPaths,browseIndex,browseIndex+numOfImagesToShow);
	}
	
	public int getNumOfImagesToShow() {
		return numOfImagesToShow;
	}

    /**
     * Get the length of the subarray to show at once.
     * @return length of the subarray
     */
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

    /**
     * Get view of one image in the CarouselList.
     * On click the count for the image is incremented by one.
     * A toast shows the current count.
     *
     * @param position The position of the object of which the view should be returned
     * @param convertView ConvertView
     * @param parent Parent
     * @return View of the object to show
     */
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
				counts[position+browseIndex]++;
				String message = subTags[position] + " has " + Integer.toString(counts[position+browseIndex]);
				if(counts[position+browseIndex] == 1) {
					message += " vote!";
				} else {
					message += " votes!";
				}
				Toast toast = Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT);
				toast.show();
			}
			
		});
		
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(tags[position+browseIndex]);
        
        return view;
	}

	/**
	 * Updates the tag object with firstIndex to be at the leftmost in the carousel list.
	 * 
	 * @param firstIndex index for the tag object to be at the leftmost
	 */
    private void setFirstIndex (int firstIndex) {
        this.browseIndex = firstIndex;
    }
    
    /**
     * Get the index of the tag object at the leftmost in the carousel list.
     * 
     * @return first index
     */
    public int getFirstIndex() {
    	return browseIndex;
    }

    /**
     * Updates sub arrays.
     */
    private void setSubArrays() {
        if(browseIndex >= 0 && browseIndex+numOfImagesToShow <= tags.length) {
            subTags = Arrays.copyOfRange(tags, browseIndex, browseIndex + numOfImagesToShow);
            subImgPaths = Arrays.copyOfRange(imgPaths, browseIndex, browseIndex + numOfImagesToShow);
        }
    }

    /**
     * Called when right button is clicked, to browse to the right in the carousel list. The first index is therefore increased by 1.
     */
    public void browseRight () {
        setFirstIndex(browseIndex+1);
        setSubArrays();
    }

    /**
     * Called when left button is clicked, to browse to the left in the carousel list. The first index is therefore decreased by 1.
     */
    public void browseLeft () {
        setFirstIndex(browseIndex-1);
        setSubArrays();
    }
    
}
