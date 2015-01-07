package com.example.octofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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

    ArrayList<Tag> objects;
    ArrayList<Tag> subObjects;

    /**
     * Index denoting the leftmost displayed image in the whole array.
     */
    private int browseIndex;
    int browsePosition;

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
     * Public class constructor.
     *
     * @param context Context
     * @param numOfImagesToShow The number of images that should be shown at one time.
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
	 * This includes creating sub array with tag names and sub array with image paths. 
	 */
	private void init() {
        browseIndex = 0;
        setSubArrays();
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
        return numOfImagesToShow;
	}
	
	/**
	 * Returns the length of the original tag name list.
	 * 
	 * @return the length of the original tag name list
	 */
	public int getTotLength() {
		return objects.size();
	}

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

        View v;
        if(convertView == null) {
            v = new View(context);
        } else
            v = convertView;

        return v;

        /*

		View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);

        browsePosition = position+browseIndex;
        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(imgPaths[browsePosition], "drawable",
           parent.getContext().getPackageName());
        view.setBackgroundResource(resourceId);
        
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Increase count for tag
				counts[position+browseIndex]++;
				String message = tags[position+browseIndex] + " has " + Integer.toString(counts[browsePosition]);
				if(counts[browsePosition] == 1) {
					message += " vote!";
				} else {
					message += " votes!";
				}
				Toast toast = Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT);
				toast.show();
			}
			
		});

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("some text");//tags[place]);
        
        return view;*/
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
        //if(browseIndex >= 0 && browseIndex+numOfImagesToShow <= tags.length) {
        if(browseIndex >= 0 && browseIndex+numOfImagesToShow <= objects.size()) {
            //subTags = Arrays.copyOfRange(tags, browseIndex, browseIndex + numOfImagesToShow);
            //subImgPaths = Arrays.copyOfRange(imgPaths, browseIndex, browseIndex + numOfImagesToShow);

            subObjects = new ArrayList<Tag>();
            for(int i = 0; i<numOfImagesToShow;i++) {
                subObjects.add(objects.get(browseIndex+i));
            }

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
