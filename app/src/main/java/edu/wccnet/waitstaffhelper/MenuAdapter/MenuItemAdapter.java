package edu.wccnet.waitstaffhelper.MenuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import edu.wccnet.waitstaffhelper.R;

public class MenuItemAdapter extends ArrayAdapter<MenuItems>{
    private ArrayList<MenuItems> menuItemsArrayList;
    Context context;

    public MenuItemAdapter( Context context, ArrayList<MenuItems> menuItemsArrayList) {
        super(context, R.layout.menu_item, menuItemsArrayList);
        this.menuItemsArrayList = menuItemsArrayList;
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.menu_item,parent, false);

            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.menu_model);
            holder.image=(ImageView)convertView.findViewById(R.id.menu_image);

            /************  Set holder with LayoutInflater ************/
            convertView.setTag( holder );
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(menuItemsArrayList.size()>0) {
            /***** Get each Model object from Arraylist ********/
            MenuItems workingMenuItem = menuItemsArrayList.get( position );

            holder.text.setText( workingMenuItem.getName() );
            holder.image.setImageResource(
                    convertView.getResources().getIdentifier(workingMenuItem.getImage(), "drawable", "edu.wccnet.waitstaffhelper"));
        }
        return convertView;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public ImageView image;
        public TextView text;
    }
}
