package edu.wccnet.waitstaffhelper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class CustomWaitstaffAdapter extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_waitstaff_adapter);

        final ListView listview = (ListView)findViewById(R.id.listView2);
        final ArrayList<MenuItems> menuItemsArray = new ArrayList<MenuItems>();

        ArrayList<String> menuArray;

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE);

        menuArray = new ArrayList<>(sharedPref.getStringSet("Menu",null));
        for(int i=menuArray.size()-1;i>=0;i--) {
            Log.i(TAG,"Position i="+i);
            String tempString = menuArray.get(i);
            MenuItems menuItem = new MenuItems();
            menuItem.setImage(tempString.toLowerCase());
            menuItem.setSize(sharedPref.getStringSet(tempString, null).size());
            menuItem.setName(tempString);
            menuItemsArray.add(menuItem);
        }
        /*
        the above code but using a enhanced for loop + a reversal method
        for(String subitem:menuArray) {
            MenuItems menuItem = new MenuItems();
            menuItem.setImage(subitem.toLowerCase());
            menuItem.setSize(sharedPref.getStringSet(subitem, null).size());
            menuItem.setName(subitem);
            menuItemsArray.add(menuItem);
        }
        Collections.reverse(menuItemsArray);
        */

        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter( this, menuItemsArray );
        listview.setAdapter(menuItemAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int position, long id)
            {
                Toast.makeText(CustomWaitstaffAdapter.this, "There are "+menuItemsArray.get(position).getSize()+" items in "+menuItemsArray.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}