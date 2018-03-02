package edu.wccnet.waitstaffhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

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
                    Toast.makeText(LoginScreen.this, "Logged in with:\nUsername:"+username+"\nPassword:"+password, Toast.LENGTH_SHORT).show();
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
}