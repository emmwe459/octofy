package com.example.octofy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * <h1>CarouselAdapter</h1>
 * Adapter extending BaseAdapter to display images in a Carousel.
 * Displays numOfImagesToShow images at one time.
 *
 * @see com.example.octofy.Carousel
 * @see com.example.octofy.CarouselList
 * @see android.widget.BaseAdapter
 */
public class CarouselAdapter extends BaseAdapter {

    /**
     * Context.
     */
	private Context context;

    /**
     * List of objects to be displayed in the carousel.
     */
    ArrayList<Tag> objects;

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
     */
    public CarouselAdapter(Context context) {
        this.context = context;
    }

    /**
     * Public class constructor.
     *
     * @param context Context
     * @param numOfImagesToShow The number of images that should be shown at one time
     */
    public CarouselAdapter(Context context, int numOfImagesToShow) {
        this.context = context;
        this.numOfImagesToShow = numOfImagesToShow;
    }

    /**
     * Public class constructor.
     *
     * @param context Context
     * @param numOfImagesToShow The number of images that should be shown at one time
     * @param objects ArrayList of Tag objects for the carousel
     */
	public CarouselAdapter(Context context, int numOfImagesToShow, ArrayList<Tag> objects) {
		this.context = context;
		this.numOfImagesToShow = numOfImagesToShow;
        this.objects = objects;
	}
	
	/**
     * Sets tag data for the carousel. 
     *
     * @param  objects The arraylist with what should be shown in the carousel
     */
	public void setTagData(ArrayList<Tag> objects) {
        this.objects = objects;
        init();
    }

	/**
	 * Inits values for adapter.
	 */
	private void init() {
        browseIndex = 0;
	}

    /**
     * Set number of images to show.
     *
     * @param numOfImagesToShow Number of images to show at once in the carousel
     */
    public void setNumOfImagesToShow(int numOfImagesToShow) {
        this.numOfImagesToShow = numOfImagesToShow;
    }

    /**
     * Get number of images to show.
     *
     * @return Number of images to show
     */
	public int getNumOfImagesToShow() {
		return numOfImagesToShow;
	}

    /**
     * Get the length of the number of images to show at once in the carousel.
     * 
     * @return Number of images to show
     */
	@Override
	public int getCount() {
        return numOfImagesToShow;
	}
	
	/**
	 * Returns the length of the original tag name list.
	 * 
	 * @return The length of the original tag name list
	 */
	public int getTotLength() {
		return objects.size();
	}

    /**
     * Returns the object that should be drawn in the view, depending on position and browsing index.
     * 
     * @param position
     * @return The object that should be used in the view
     */
	@Override
	public Tag getItem(int position) {
        return objects.get(position+browseIndex);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

    /**
     * Get view of one image in the CarouselList.
     *
     * @param position The position of the object of which the view should be returned
     * @param convertView ConvertView
     * @param parent Parent
     * @return View of the object to show
     */
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		
        View v;
        
        if(convertView == null) {
            v = new View(context);
        } else {
        	v = convertView;
        }
        
        return v;
	}

	/**
	 * Updates the tag object with firstIndex to be at the leftmost in the carousel list.
	 * 
	 * @param firstIndex Index for the tag object to be at the leftmost
	 */
    private void setFirstIndex (int firstIndex) {
        this.browseIndex = firstIndex;
    }
    
    /**
     * Get the index of the tag object at the leftmost in the carousel list.
     * 
     * @return First index
     */
    public int getFirstIndex() {
    	return browseIndex;
    }

    /**
     * Called when right button is clicked, to browse to the right in the carousel list. The first index is therefore increased by 1.
     */
    public void browseRight () {
        setFirstIndex(browseIndex+1);
    }

    /**
     * Called when left button is clicked, to browse to the left in the carousel list. The first index is therefore decreased by 1.
     */
    public void browseLeft () {
        setFirstIndex(browseIndex-1);
    }
    
}
