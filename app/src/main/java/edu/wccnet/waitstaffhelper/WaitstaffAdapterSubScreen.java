package edu.wccnet.waitstaffhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.wccnet.waitstaffhelper.MenuAdapter.MenuItemAdapter;
import edu.wccnet.waitstaffhelper.MenuAdapter.MenuItems;

public class WaitstaffAdapterSubScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitstaff_subscreen);

        /*
        final ListView listview = (ListView)findViewById(R.id.subMenuListView);
        final ArrayList<MenuItems> menuItemsArray = new ArrayList<MenuItems>();

        ArrayList<String> menuArray;




        the above code but using a enhanced for loop + a reversal method
        for(String subitem:menuArray) {
            MenuItems menuItem = new MenuItems();
            menuItem.setImage(subitem.toLowerCase());
            menuItem.setSize(sharedPref.getStringSet(subitem, null).size());
            menuItem.setName(subitem);
            menuItemsArray.add(menuItem);
        }
        Collections.reverse(menuItemsArray);


        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter( this, menuItemsArray );
        listview.setAdapter(menuItemAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int position, long id)
            {
                Toast.makeText(WaitstaffAdapterSubScreen.this, "There are "+menuItemsArray.get(position).getSize()+" items in "+menuItemsArray.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
}