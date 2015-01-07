package com.example.octofy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by linneanabo on 2015-01-05.
 */
public class CustomCarouselAdapter extends CarouselAdapter {

    /**
     * Public class constructor.
     *
     * @param context           Context
     * @param numOfImagesToShow The number of images that should be shown at one time.
     */

    Context context;
    int numOfImagesToShow;

    public CustomCarouselAdapter(Context context, int numOfImagesToShow) {
        super(context, numOfImagesToShow);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);
        /*
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

        return view;*/
        return view;
    }
}
