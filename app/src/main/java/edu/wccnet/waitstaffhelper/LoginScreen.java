package edu.wccnet.waitstaffhelper;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = LoginScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button loginButton=(Button)findViewById(R.id.login_screen_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG,"Got to second button click");
                EditText editUsername=(EditText)findViewById(R.id.login_screen_et_username);
                String username = editUsername.getText().toString();
                EditText editPassword=(EditText)findViewById(R.id.login_screen_et_password);
                String password = editPassword.getText().toString();
                if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)) {
                    RetrieveUserAndPass task = new RetrieveUserAndPass(username,password);
                    task.execute("http://api.jsonbin.io/b/5a84a257a67185097468daa2");
                }
                else {
                    Toast.makeText(LoginScreen.this, "Username and Password are required", Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"Failed to login");
                }
            }
        });
        // the following is probably way overdoing it but I struggled to find a solution that didn't require overriding/redrawing the EditText class
        final EditText focusedEditUsername=(EditText)findViewById(R.id.login_screen_et_username);
        focusedEditUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditUsername.setBackgroundColor(Color.WHITE);
                } else {
                    focusedEditUsername.setBackgroundColor(getResources().getColor(R.color.backgroundColorSplashScreen));
                }
            }
        });
        final EditText focusedEditPassword=(EditText)findViewById(R.id.login_screen_et_password);
        focusedEditPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditPassword.setBackgroundColor(Color.WHITE);
                } else {
                    focusedEditPassword.setBackgroundColor(getResources().getColor(R.color.backgroundColorSplashScreen));
                }
            }
        });

    }

    private class RetrieveUserAndPass extends AsyncTask<String, Void, String> {

        private String usernameToCheck;
        private String passwordToCheck;
        private String foundPassword;
        private String foundUsername;

        public RetrieveUserAndPass(String usernameToCheck,String passwordToCheck) {
            this.usernameToCheck=usernameToCheck;
            this.passwordToCheck=passwordToCheck;
        }

        @Override
        protected String doInBackground(String... urls) {
            String loginMessage="ERROR";
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
                JSONObject userLevel = topLevel.getJSONObject("user");

                Log.i(TAG,"getString('username') returns : "+userLevel.getString("username"));
                Log.i(TAG,"getString('password') returns : "+userLevel.getString("password"));


                foundUsername = userLevel.getString("username");
                foundPassword = userLevel.getString("password");

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            // the following is a bit long but shouldn't fail if json retrieval fails, instead it should run the else block
            if (android.text.TextUtils.equals(foundUsername, usernameToCheck) && android.text.TextUtils.equals(foundPassword, passwordToCheck)) {
                loginMessage = "Logged in with:\nUsername:"+usernameToCheck+"\nPassword:"+foundPassword;
            }
            else {
                loginMessage = "Username or password is incorrect";
            }
            return loginMessage;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(LoginScreen.this, result, Toast.LENGTH_SHORT).show();
        }

    }
}