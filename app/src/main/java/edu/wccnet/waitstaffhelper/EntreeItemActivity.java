package edu.wccnet.waitstaffhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        ImageView imageView = (ImageView) findViewById(R.id.itemImage);

        RequestOptions options = new RequestOptions().fitCenter().placeholder(R.drawable.lunch).error(R.drawable.dinner);
        Glide.with(this).load(item.getimageUrl()).apply(options).into(imageView);
    }
}
