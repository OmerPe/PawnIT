package com.colman.pawnit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.Model;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class pawnActivity extends AppCompatActivity {

    List<Listing> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        RecyclerView rView = findViewById(R.id.pawn_activity_list);
        rView.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rView.setLayoutManager(manager);

        data = Model.instance.getListingData();
        data.add(new Listing());
        data.add(new Listing());
        data.add(new Listing());
        data.add(new Listing());

        MyAdapter adapter = new MyAdapter();
        rView.setAdapter(adapter);

    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.pawn_list_row, parent, false);
            MyViewHolder holder = new pawnActivity.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Listing listing = data.get(position);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
