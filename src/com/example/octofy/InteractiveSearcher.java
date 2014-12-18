package com.example.octofy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class InteractiveSearcher extends RelativeLayout {

    public EditText searchField;
    private ListPopupWindow resultList;
    private int search_id, latestRequestId;
    ArrayList<String> names;
    ResultAdapter resultAdapter;
    int rowLimit = 10; //default

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

        resultList = new ListPopupWindow(context);
        resultAdapter = new ResultAdapter(context, R.layout.result_row, this);
        resultList.setAdapter(resultAdapter);
        resultList.setAnchorView(searchField);
        resultList.setWidth(300);
        resultList.setHeight(400);
        resultList.setModal(false);

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

                names.clear();
                if (arg0.length() == 0) {
                    resultList.dismiss();
                } else if (!resultList.isShowing()) {
                    resultList.show();
                }
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
            //Log.d("test","Result: " + result);
            resultAdapter.updateData(names);
            resultList.setAdapter(resultAdapter);
            if(!names.isEmpty()) {
                resultList.show();
                searchField.requestFocus();
            }
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
            /*int response = conn.getResponseCode();
            Log.d("test", "Response code: " + response);*/
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
        names.clear();

        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");

        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginObject();

            jsonReader.nextName();
            String search_id = jsonReader.nextString();

            // if the returned is not the same as requested, return nothing
            if (Integer.parseInt(search_id) != latestRequestId) {
            	jsonReader.close();
                return "";
            }

            jsonReader.nextName();
            toReturn = toReturn + "search id: " + search_id + ", names: ";

            jsonReader.beginArray();

            int i = 0;
            while(jsonReader.hasNext()) {
                if(i < rowLimit) {
                    names.add(jsonReader.nextString());
                    i++;
                } else {
                    jsonReader.nextString();
                }
            }

            jsonReader.endArray();
            jsonReader.endObject();
            jsonReader.close();

            return toReturn;

        } catch(IOException e) {
            Log.d("test", e.getMessage());
        }
        return "";
    }

    public void setRowLimit (int n) {
        this.rowLimit = n;
    }

    public void updateSearchField (String s) {
        searchField.setText(s);
        searchField.setSelection(s.length());
    }
}
