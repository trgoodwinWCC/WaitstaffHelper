package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EntreeItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entree_item);
        Intent intent = getIntent();
        EntreeItems item = (EntreeItems)intent.getSerializableExtra("EntreeItem");
        TextView itemName = (TextView)findViewById(R.id.itemName);
        TextView itemPrice = (TextView)findViewById(R.id.itemPriceText);
        itemName.setText(item.getname());
        itemPrice.setText(item.getprice());
        // load image using Glide
        // https://github.com/bumptech/glide
        //@TODO Fix to use all items and load the image.
    }
}
