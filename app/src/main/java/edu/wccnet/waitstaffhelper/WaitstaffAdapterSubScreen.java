package edu.wccnet.waitstaffhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class WaitstaffAdapterSubScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView entreeRecyclerView;
    private RecyclerView.LayoutManager entreeLayoutManager;

    public static class EntreeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        EntreeItems testModel;
        public TextView eTextView;
        public EntreeViewHolder(View v) {
            super(v);
            eTextView = v.findViewById(R.id.entree_model);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(testModel!=null) {
                        Log.i(TAG,"Testing onclick and model is not null");
                        Intent intentTest = new Intent(view.getContext(),EntreeItemActivity.class);
                        intentTest.putExtra("EntreeItem",testModel);
                        view.getContext().startActivity(intentTest);
                    }

                    Log.i(TAG,"Testing "+eTextView.getText());
                }
            });
        }
        public void assignModelClass(EntreeItems model) {
            testModel=model;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitstaff_subscreen);


        Query query = FirebaseDatabase.getInstance().getReference("Entrees").limitToLast(10);



        FirebaseRecyclerOptions<EntreeItems> options = new FirebaseRecyclerOptions.Builder<EntreeItems>().setQuery(query, EntreeItems.class).build();

        adapter = new FirebaseRecyclerAdapter<EntreeItems, EntreeViewHolder>(options) {
            @Override
            public EntreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                // replace message with my own layout for the item.
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entree_item, parent, false);
                return new EntreeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(EntreeViewHolder holder, int position, EntreeItems model) {
                // Bind the model object to the Holder
                // ...
                holder.assignModelClass(model);
                holder.eTextView.setText(model.getname());

            }
            @Override
            public void onError(DatabaseError e) {
                Log.e(TAG,"Error:",e.toException());
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
            }
        };

        entreeRecyclerView = (RecyclerView) findViewById(R.id.subMenuListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //entreeRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        entreeLayoutManager = new LinearLayoutManager(this);
        entreeRecyclerView.addItemDecoration(new DividerItemDecoration(WaitstaffAdapterSubScreen.this, DividerItemDecoration.VERTICAL));
        entreeRecyclerView.setLayoutManager(entreeLayoutManager);
        entreeRecyclerView.setAdapter(adapter);

        /*
        Note: you need to download my project's firebase json from the online console and put it the project/app folder otherwise it will not build.
         */

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}