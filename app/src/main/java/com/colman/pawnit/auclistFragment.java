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
import android.widget.TextView;

import com.colman.pawnit.Model.Listing;

import java.util.ArrayList;
import java.util.List;


public class auclistFragment extends Fragment {

    private RecyclerView recyclerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_auclist, container, false);



        List<Listing> demo = new ArrayList<>();
        Listing listing1 = new Listing();
        listing1.setTitle("eden");
        Listing listing2 = new Listing();
        listing2.setTitle("Dor");
        Listing listing3 = new Listing();
        listing3.setTitle("Yanivloser");
        demo.add(listing1);
        demo.add(listing2);
        demo.add(listing3);



        recyclerList = view.findViewById(R.id.auclist_reclist);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setHasFixedSize(true);
        recyclerList.setAdapter(new MyAdapter(demo));


        return view;
    }



    class MyAdapter extends RecyclerView.Adapter<auclistFragment.MyAdapter.ViewHolder>{

        private List<Listing> mList;


        public MyAdapter(List<Listing> data){
            mList = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =  LayoutInflater.from(getContext()).inflate(R.layout.auction_list_item_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Listing currentListing = mList.get(position);

            holder.title.setText(currentListing.getTitle());
            // holder.requested.setText(currentListing.p);


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title, sPrice;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.auction_row_picture);
                title =itemView.findViewById(R.id.auctionlistrow_title);
                sPrice =itemView.findViewById(R.id.auctionlistrow_sPrice);
            }
        }


    }
}