package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenuScreen extends AppCompatActivity {

    private static final String TAG = MainMenuScreen.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen);


        Button entreeButton=(Button)findViewById(R.id.entreeButton);
        entreeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent entreeActivityStarter = new Intent(view.getContext(),WaitstaffAdapterSubScreen.class);
                Log.i(TAG,"Entreebutton clicked, sending to WaitstaffAdapterSubScreen");
                startActivity(entreeActivityStarter);
            }
        });

        Button orderButton=(Button)findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent orderActivityStarter = new Intent(view.getContext(),WaitstaffAdapterScreen.class);
                Log.i(TAG,"Orderbutton clicked, sending to WaitstaffAdapterScreen");
                startActivity(orderActivityStarter);
            }
        });

    }
}
