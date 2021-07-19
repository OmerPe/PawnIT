package com.colman.pawnit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colman.pawnit.Model.Listing;

import java.util.ArrayList;
import java.util.List;


public class fragment_pawn extends Fragment {

    private RecyclerView recyclerList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pawn, container, false);

        List<Listing> demo = new ArrayList<>();
        Listing listing1 = new Listing();
        listing1.setTitle("Eden");
        Listing listing2 = new Listing();
        listing2.setTitle("Dor");
        Listing listing3 = new Listing();
        listing3.setTitle("Daniel");
        Listing listing4 = new Listing();
        listing4.setTitle("Omer");
        demo.add(listing1);
        demo.add(listing2);
        demo.add(listing3);
        demo.add(listing4);

        recyclerList = view.findViewById(R.id.pawn_reclist);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setHasFixedSize(true);
        recyclerList.setAdapter(new MyAdapter(demo));
        pawnActivity.progressBar.setVisibility(View.GONE);
        return view;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Listing> mList;

        public MyAdapter(List<Listing> data) {
            mList = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.pawn_list_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Listing currentListing = mList.get(position);

            holder.title.setText(currentListing.getTitle());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title, price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.pawn_row_picture);
                title = itemView.findViewById(R.id.pawn_row_title);
                price = itemView.findViewById(R.id.pawn_row_price);
            }

        }


    }
}