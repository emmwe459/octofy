package com.example.octofy;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by linneanabo on 2014-12-16.
 */
public class ResultAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> data;

    public ResultAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        data = new ArrayList<String>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("test", "******* inside getview");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row= inflater.inflate(R.layout.result_row, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.row);

        textView.setText(data.get(position));

        return row;
    }

    @Override
    public int getCount() {
        //Log.d("test", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<String> data) {
        Log.d("test", " *** In update data");
        this.data = data;
    }
}
