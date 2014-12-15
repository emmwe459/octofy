package com.example.octofy;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linneanabo on 2014-12-12.
 */
public class TagCloudAdapter implements ListAdapter {

    Context context;
    HashMap<String, Integer> data;
    ArrayList<String> easyData;

    public TagCloudAdapter(HashMap<String, Integer> data, ArrayList<String> easyData, Context c){
        this.data= data;
        this.easyData = easyData;
        this.context=c;
    }



    public void addData(String tag){
        /*
        this.data.add(name);
        for ( DataSetObserver observer: this.observers)
            observer.onChanged();
            */
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(this.context);
        textView.setText(easyData.get(position));
        //textView.setText("new text here");

        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
