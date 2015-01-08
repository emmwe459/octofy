package com.example.octofy;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomCarouselAdapter extends CarouselAdapter {

    /**
     * Public class constructor.
     *
     * @param context Context
     *
     */

    Context context;

    public CustomCarouselAdapter(Context context, int numOfImagesToShow) {
        super(context, numOfImagesToShow);
        this.context = context;
    }

    public CustomCarouselAdapter(Context context, int numOfImagesToShow, ArrayList<Tag> objects) {
        super(context, numOfImagesToShow, objects);
        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view;
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.carousel_item, null);
        } else {
            view = convertView;
        }

        final Tag currentTag = getItem(position);

        Resources resources = parent.getContext().getResources();
        final int resourceId = resources.getIdentifier(currentTag.getImgPath(), "drawable",
                parent.getContext().getPackageName());
        view.setBackgroundResource(resourceId);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Increase count for tag
                currentTag.setCount(currentTag.getCount() + 1);
                String message = currentTag.getText()+ " has " + Integer.toString(currentTag.getCount());
                if(currentTag.getCount() == 1) {
                    message += " vote!";
                } else {
                    message += " votes!";
                }
                Toast toast = Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT);
                toast.show();
            }

        });

        return view;
    }
}
