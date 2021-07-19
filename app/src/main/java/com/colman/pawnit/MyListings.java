package com.colman.pawnit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.pawnit.Model.Listing;

import java.util.ArrayList;
import java.util.List;

public class MyListings extends Fragment {

    private RecyclerView recyclerList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_listings, container, false);


        List<Listing> demo = new ArrayList<>();
        Listing listing1 = new Listing();
        listing1.setTitle("eden");
        Listing listing2 = new Listing();
        listing2.setTitle("Dor");
        demo.add(listing1);
        demo.add(listing2);


        recyclerList = view.findViewById(R.id.recyclerView);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setHasFixedSize(true);
        recyclerList.setAdapter(new MyAdapter(demo));


        return view;
    }

    public void goToDetails(Listing currentListing, View v){
        Navigation.findNavController(v).navigate(R.id.action_myListings_to_auctionFragment);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Listing> mList;


        public MyAdapter(List<Listing> data) {
            mList = data;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.market_list_row, parent, false);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyListings.MyAdapter.ViewHolder holder, int position) {
            Listing currentListing = mList.get(position);

            holder.title.setText(currentListing.getTitle());


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ConstraintLayout row;
            ImageView imageView;
            TextView title, price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.market_row_picture);
                title =itemView.findViewById(R.id.market_row_title);
                price =itemView.findViewById(R.id.market_row_Requested);
                row = itemView.findViewById(R.id.constraintLayout_row);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position  = getLayoutPosition();
                        Listing currentListing = mList.get(position);
                        goToDetails(currentListing, v);

                    }
                });

            }


        }


    }


}