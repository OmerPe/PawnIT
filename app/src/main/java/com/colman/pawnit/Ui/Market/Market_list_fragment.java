package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;

import java.util.LinkedList;
import java.util.List;

import io.perfmark.Link;

public class Market_list_fragment extends Fragment {

    private MarketListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_list_fragment, container, false);

        RecyclerView list = view.findViewById(R.id.market_list_rv);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(MarketListViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });

        return view;
    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Listing> data = new LinkedList<>();

        public MyAdapter(){
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case 0:
                    return new ResellViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.resell_list_row,parent,false));
                case 1:
                    return new AuctionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_list_row,parent,false));
                default:
                    return null;
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()){
                case 0:
                    ResellViewHolder viewHolder = (ResellViewHolder) holder;
                    viewHolder.title.setText(data.get(position).getTitle());
                    viewHolder.price.setText("" +((ResellListing)data.get(position)).getPrice());
                    break;
                case 1:
                    AuctionViewHolder viewHolder1 = (AuctionViewHolder) holder;
                    viewHolder1.title.setText(data.get(position).getTitle());
                    viewHolder1.startingPrice.setText(""+((AuctionListing)data.get(position)).getStartingPrice());
                    break;
                default:
                    break;
            }

        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<Listing> list){
            this.data = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if(data.get(position) instanceof ResellListing){
                return 0;
            }else {
                return 1;
            }
        }

        public class ResellViewHolder extends RecyclerView.ViewHolder {
             private TextView title;
             private TextView price;
            public ResellViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Resell_row_title);
                price = itemView.findViewById(R.id.Resell_row_Price);
            }
        }
        public class AuctionViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView startingPrice;
            public AuctionViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.auctionlistrow_title);
                startingPrice = itemView.findViewById(R.id.auctionlistrow_sPrice);
            }
        }
    }
}