package edu.wccnet.waitstaffhelper;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EntreeAdapterScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getCanonicalName();
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView entreeRecyclerView;
    private RecyclerView.LayoutManager entreeLayoutManager;

    public static class EntreeViewHolder extends RecyclerView.ViewHolder {

        EntreeItemsBean hiddenModel;
        public TextView eTextView;
        public EntreeViewHolder(View v) {
            super(v);
            eTextView = v.findViewById(R.id.entree_model);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hiddenModel !=null) {
                        Intent intentTest = new Intent(view.getContext(),EntreeItemActivity.class);
                        intentTest.putExtra("EntreeItem", hiddenModel);
                        view.getContext().startActivity(intentTest);
                    }
                    Log.i(TAG,"Testing "+eTextView.getText());
                }
            });
        }
        public void assignModelClass(EntreeItemsBean model) {
            hiddenModel =model;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitstaff_subscreen);

        Query query = FirebaseDatabase.getInstance().getReference("Entrees").limitToLast(10);

        FirebaseRecyclerOptions<EntreeItemsBean> options = new FirebaseRecyclerOptions.Builder<EntreeItemsBean>().setQuery(query, EntreeItemsBean.class).build();

        adapter = new FirebaseRecyclerAdapter<EntreeItemsBean, EntreeViewHolder>(options) {
            @Override
            public EntreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entree_item, parent, false);
                return new EntreeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(EntreeViewHolder holder, int position, EntreeItemsBean model) {
                // Bind the model object to the Holder
                holder.assignModelClass(model);
                // I passed the class into the holder.
                // the holder has a OnClickListener to start the detail view.
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
        entreeRecyclerView.setHasFixedSize(true);

        entreeLayoutManager = new LinearLayoutManager(this);
        entreeRecyclerView.addItemDecoration(new DividerItemDecoration(EntreeAdapterScreen.this, DividerItemDecoration.VERTICAL));
        entreeRecyclerView.setLayoutManager(entreeLayoutManager);
        entreeRecyclerView.setAdapter(adapter);

        // Note: you need may to download my project's firebase metadata as json from the online console and put it the /project/app folder otherwise it will not build.
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