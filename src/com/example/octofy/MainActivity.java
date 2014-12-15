package com.example.octofy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private EditText searchField;
	private int search_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_3);
        
        // initialize search_id
        search_id = 0;
        
        searchField = (EditText)findViewById(R.id.searchField);
        
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
			            getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		        if(networkInfo != null && networkInfo.isConnected()) {
		            new DownloadWebpage().execute(url);
		        } else {
		            Log.d("test","No network connection available.");
		        }
		        
		        // increase search id in order to be unique for each search made
		        search_id++;
				
			}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        jsonReader.nextName();
        toReturn = toReturn + "search id: " + search_id + ", names: ";
        
        jsonReader.beginArray();
        
        while(jsonReader.hasNext()) {
        	toReturn = toReturn + jsonReader.nextString() + ",";
        }
        
        jsonReader.endArray();
        jsonReader.endObject();
        jsonReader.close();
        
        return toReturn;
    }
    
}
