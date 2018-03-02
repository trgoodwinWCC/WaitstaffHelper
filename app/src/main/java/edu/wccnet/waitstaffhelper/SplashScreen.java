package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
}