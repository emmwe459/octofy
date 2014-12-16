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
    InteractiveSearcher is;

    public ResultAdapter(Context context, int resource, InteractiveSearcher is) {
        super(context, resource);
        this.context = context;
        this.is = is;
        data = new ArrayList<String>();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row= inflater.inflate(R.layout.result_row, parent, false);

        RowView rowView = (RowView) row.findViewById(R.id.row);
        rowView.setText(data.get(position));

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is.updateSearchField(data.get(position));
            }
        });

        return row;
    }

    @Override
    public int getCount() {
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
        this.data = data;
    }
}