package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;

public class Market_list_fragment extends Fragment {

    private MarketListViewModel mViewModel;
    RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_list_fragment, container, false);
        list = view.findViewById(R.id.market_list_rv);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(MarketListViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.notifyDataSetChanged();
        });

        return view;
    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final View.OnClickListener mOnClickListenerResell = new MyAdapter.MyOnClickListenerResell();
        private final View.OnClickListener mOnClickListenerAuction = new MyAdapter.MyOnClickListenerAuction();


        public MyAdapter(){
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
            switch (holder.getItemViewType()){
                case 0:
                    ResellViewHolder viewHolder = (ResellViewHolder) holder;
                    viewHolder.title.setText(mViewModel.getData().getValue().get(position).getTitle());
                    viewHolder.price.setText("" +((ResellListing)mViewModel.getData().getValue().get(position)).getPrice());
                    break;
                case 1:
                    AuctionViewHolder viewHolder1 = (AuctionViewHolder) holder;
                    viewHolder1.title.setText(mViewModel.getData().getValue().get(position).getTitle());
                    viewHolder1.startingPrice.setText(""+((AuctionListing)mViewModel.getData().getValue().get(position)).getStartingPrice());
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if(mViewModel.getData().getValue() != null){
                return mViewModel.getData().getValue().size();
            }else{
                return 0;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(mViewModel.getData().getValue().get(position) instanceof ResellListing){
                return 0;
            }else {
                return 1;
            }
        }

        public class ResellViewHolder extends RecyclerView.ViewHolder {
             private TextView title;
             private TextView price;
             private ImageView img;
            public ResellViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Resell_row_title);
                price = itemView.findViewById(R.id.Resell_row_Price);
                img = itemView.findViewById(R.id.Resell_row_picture);
            }
        }

        public class MyOnClickListenerResell implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = mViewModel.getData().getValue().get(itemPosition).getListingID();

                MarketFragmentDirections.ActionMarketFragmentToResellListFragment action =
                        MarketFragmentDirections.actionMarketFragmentToResellListFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }

        public class AuctionViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView startingPrice;
            private ImageView img;
            public AuctionViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.auctionlistrow_title);
                startingPrice = itemView.findViewById(R.id.auctionlistrow_price);
                img = itemView.findViewById(R.id.auction_row_picture);
            }
        }

        public class MyOnClickListenerAuction implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = mViewModel.getData().getValue().get(itemPosition).getListingID();

                MarketFragmentDirections.ActionMarketFragmentToAuctionListingFragment action =
                        MarketFragmentDirections.actionMarketFragmentToAuctionListingFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }

    }
}