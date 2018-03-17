package edu.wccnet.waitstaffhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomWaitstaffAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_waitstaff_adapter);

        final ListView listview = (ListView)findViewById(R.id.listView2);

        // Note that
        final ArrayList<MenuItems> menuItemsArray = new ArrayList<MenuItems>();
        MenuItems myVehicle = new MenuItems();
        //@TODO fix up naming
        myVehicle.setImage("breakfast");
        myVehicle.setName("Breakfast");
        menuItemsArray.add(myVehicle);

        MenuItems myVehicle2 = new MenuItems();
        myVehicle2.setImage("desserts");
        myVehicle2.setName("Older Truck");
        menuItemsArray.add(myVehicle2);

        // Where the context is the activity instance, the second parameter is the XML layout for the item (more later), and the last is the array of data
        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter( this, menuItemsArray );
        listview.setAdapter(menuItemAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int position, long id)
            {
                Toast.makeText(CustomWaitstaffAdapter.this, menuItemsArray.get(position).getName() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
