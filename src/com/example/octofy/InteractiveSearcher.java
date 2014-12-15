package com.example.octofy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linneanabo on 2014-12-11.
 */
public class InteractiveSearcher extends RelativeLayout {

    private EditText searchField;
    private SearchListView searchListView;
    private int search_id, latestRequestId;
    ArrayList<String> names;

    Context context;

    public InteractiveSearcher(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        names = new ArrayList<String>();

        // initialize search_id
        search_id = 0;
        // check that request and answer id are the same
        latestRequestId = 0;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.interactive_searcher, this, true);


        searchField = (EditText) findViewById(R.id.search_field);
        searchListView = (SearchListView) findViewById(R.id.search_list);

        // listen for changes in search field
        searchField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

				/* use AsyncTask, otherwise error NetworkOnMainThreadException when trying to connect to URL on main thread
				 * Example taken from here: http://developer.android.com/training/basics/network-ops/connecting.html:
				 * Class: DownloadWebpage
				 * Functions: downloadUrl, readIt
				 * */

                String url = "http://flask-afteach.rhcloud.com/getnames/"+ Integer.toString(search_id) + "/" + arg0.toString();

                // check if there is network connection
                ConnectivityManager connMgr = (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpage().execute(url);
                } else {
                    Log.d("test", "No network connection available.");
                }

                // set latest request
                latestRequestId = search_id;

                // put the names in the list of the dropdown view
                searchListView.setNames(names);
                searchListView.updateList();
                names.clear();

                // increase search id in order to be unique for each search made
                search_id++;

            }

        });
    }

    private class DownloadWebpage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("test","Result: " + result);
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("test", "Response code: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // adapt to JSON object?
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

        String toReturn = "";

        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");

        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.beginObject();

        jsonReader.nextName();
        String search_id = jsonReader.nextString();

        // if the returned is not the same as requested, return nothing
        if (Integer.parseInt(search_id) != latestRequestId) {
            return "";
        }

        jsonReader.nextName();
        toReturn = toReturn + "search id: " + search_id + ", names: ";

        jsonReader.beginArray();

        while(jsonReader.hasNext()) {
            //toReturn = toReturn + jsonReader.nextString() + ",";
            names.add(jsonReader.nextString());
        }

        jsonReader.endArray();
        jsonReader.endObject();
        jsonReader.close();

        return toReturn;
    }
}
