package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final TextView versionText = findViewById(R.id.versionText);
        RetrieveVersion task = new RetrieveVersion(versionText);
        task.execute(new String[] { "http://api.jsonbin.io/b/5a84ada773fb541c61a4d0d5" });

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

                Log.i(TAG,"getString'version' is: "+settingsLevel.getString("version"));
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
}