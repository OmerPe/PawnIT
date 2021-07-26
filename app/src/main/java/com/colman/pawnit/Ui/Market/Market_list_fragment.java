package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Market_list_fragment extends Fragment {

    private MarketListViewModel mViewModel;
    RecyclerView list;
    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_list_fragment, container, false);
        list = view.findViewById(R.id.market_list_rv);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(MarketListViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.setData(data);
        });

        MarketFragment frag = ((MarketFragment)this.getParentFragment());
        TextInputEditText sb = frag.searchBox;

        sb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        return view;
    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

        private final View.OnClickListener mOnClickListenerResell = new MyAdapter.MyOnClickListenerResell();
        private final View.OnClickListener mOnClickListenerAuction = new MyAdapter.MyOnClickListenerAuction();

        List<Listing> listingsFiltered;

        public MyAdapter(){
            listingsFiltered = new LinkedList<>();
        }

        public void setData(List<Listing> listings){
            listingsFiltered = listings;
            notifyDataSetChanged();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        listingsFiltered = mViewModel.getData().getValue();
                    } else {
                        List<Listing> filteredList = new LinkedList<>();
                        if(mViewModel.getData().getValue() != null){
                            for (Listing listing : mViewModel.getData().getValue()) {
                                if(listing.getTitle().toLowerCase().contains(charString.toLowerCase())
                                        || listing.getDescription().toLowerCase().contains(charString.toLowerCase())){
                                    filteredList.add(listing);
                                }
                            }
                        }


                        listingsFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = listingsFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    listingsFiltered = (LinkedList<Listing>) results.values;
                    notifyDataSetChanged();
                }
            };
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case 0:
                    View itemViewResell = LayoutInflater.from(parent.getContext()).inflate(R.layout.resell_list_row,parent,false);
                    itemViewResell.setOnClickListener(mOnClickListenerResell);
                    return new MyAdapter.ResellViewHolder(itemViewResell);
                case 1:
                    View itemViewAuction = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_list_row,parent,false);
                    itemViewAuction.setOnClickListener(mOnClickListenerAuction);
                    return new MyAdapter.AuctionViewHolder(itemViewAuction);
                default:
                    return null;
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            List<String> imagesList;
            String image;
            Listing listing = listingsFiltered.get(position);
            switch (holder.getItemViewType()){
                case 0:
                    ResellViewHolder viewHolder = (ResellViewHolder) holder;
                    ResellListing rl = (ResellListing) listing;
                    viewHolder.title.setText(rl.getTitle());
                    viewHolder.price.setText("" + rl.getPrice());
                    imagesList = rl.getImages();
                    image = "";
                    if(imagesList != null && imagesList.size() != 0)
                        image = rl.getImages().get(0);
                    if(!image.isEmpty())
                        Picasso.get().load(image).placeholder(R.drawable.placeholder).into(((ResellViewHolder) holder).image);
                    break;
                case 1:
                    AuctionViewHolder viewHolder1 = (AuctionViewHolder) holder;
                    AuctionListing al = (AuctionListing) listing;
                    viewHolder1.title.setText(al.getTitle());
                    viewHolder1.startingPrice.setText(""+ al.getStartingPrice());
                    imagesList = al.getImages();
                    image = "";
                    if(imagesList != null && imagesList.size() != 0)
                        image = al.getImages().get(0);
                    if(!image.isEmpty())
                        Picasso.get().load(image).placeholder(R.drawable.placeholder).into(((AuctionViewHolder)holder).image);
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return listingsFiltered.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(listingsFiltered.get(position) instanceof ResellListing){
                return 0;
            }else {
                return 1;
            }
        }

        public class ResellViewHolder extends RecyclerView.ViewHolder {
             private TextView title;
             private TextView price;
             private ImageView image;
            public ResellViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Resell_row_title);
                price = itemView.findViewById(R.id.Resell_row_Price);
                image = itemView.findViewById(R.id.Resell_row_picture);
            }
        }

        public class MyOnClickListenerResell implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = listingsFiltered.get(itemPosition).getListingID();

                MarketFragmentDirections.ActionMarketFragmentToResellListFragment action =
                        MarketFragmentDirections.actionMarketFragmentToResellListFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }

        public class AuctionViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView startingPrice;
            private ImageView image;
            public AuctionViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.auctionlistrow_title);
                startingPrice = itemView.findViewById(R.id.auctionlistrow_price);
                image = itemView.findViewById(R.id.auction_row_picture);
            }
        }

        public class MyOnClickListenerAuction implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = listingsFiltered.get(itemPosition).getListingID();

                MarketFragmentDirections.ActionMarketFragmentToAuctionListingFragment action =
                        MarketFragmentDirections.actionMarketFragmentToAuctionListingFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }

    }
}