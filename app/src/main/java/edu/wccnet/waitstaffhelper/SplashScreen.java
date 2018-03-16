package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final TextView versionText = findViewById(R.id.versionText);
        RetrieveVersion task = new RetrieveVersion(versionText);
        task.execute("http://api.jsonbin.io/b/5a84ada773fb541c61a4d0d5");
        getMenuItems task2 = new getMenuItems("Breakfast","Lunch","Dinner","Dessert");
        task2.execute(new String[]{
                "http://api.jsonbin.io/b/5a9cb434a121bc097fe799ef",
                "http://api.jsonbin.io/b/5a9cb489859c4e1c4d5ddb56",
                "http://api.jsonbin.io/b/5a9cb4f0a67185097469e68d",
                "http://api.jsonbin.io/b/5a9cb528a67185097469e691"
        });

        Button continueButton=(Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent secondActivityStarter = new Intent(view.getContext(),LoginScreen.class);
                Log.i(TAG,"Got to first button click, sending to LoginScreen");
                startActivity(secondActivityStarter);
            }
        });
    }

    private class RetrieveVersion extends AsyncTask<String, Void, String> {
        private TextView textView;

        public RetrieveVersion(TextView textView) {
            this.textView=textView;
        }

        @Override
        protected String doInBackground(String... urls) {
            String version = "UNKNOWN";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(builder.toString());
                JSONObject settingsLevel = topLevel.getJSONObject("settings");

                Log.i(TAG,"getString('version') returns : "+settingsLevel.getString("version"));
                version = "Version "+settingsLevel.getString("version");

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return version;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }

    }

    private class getMenuItems extends AsyncTask<String, Void, String> {
        private ArrayList<String> listOfMenuItems = new ArrayList<>();
        private int positionOfItems;

        public getMenuItems(String... menuItemNames) {
            listOfMenuItems.addAll(Arrays.asList(menuItemNames));
            positionOfItems =0;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                for(String stringUrl : urls) {
                    if(listOfMenuItems.size()<urls.length) {
                        return "Error, names inputted do not match urls inputted";
                    }

                    URL url = new URL(stringUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    JSONObject topLevel = new JSONObject(builder.toString());
                    JSONArray itemsArray = topLevel.getJSONArray("items");

                    ArrayList<String> listOfSubItems = new ArrayList<>();
                    for (int i=0;i<itemsArray.length();i++) {
                        listOfSubItems.add(itemsArray.getJSONObject(i).getString("name"));
                    }

                    SharedPreferences.Editor editObj = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE).edit();
                    editObj.putStringSet(listOfMenuItems.get(positionOfItems),new HashSet<String>(listOfSubItems));
                    Log.i(TAG,"Item:"+listOfMenuItems.get(positionOfItems)+"\nList of all subitems:");
                    for(String item:listOfSubItems) {
                        Log.i(TAG,item);
                    }
                    editObj.commit();

                    positionOfItems++;

                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "Everything saved properly";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,result);
        }
    }
}