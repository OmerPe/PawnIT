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

import com.colman.pawnit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Auction_list_fragment extends Fragment {

    private AuctionListViewmodel mViewModel;
    RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction_list_fragment, container, false);

        list = view.findViewById(R.id.auction_list_rv);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(AuctionListViewmodel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private final View.OnClickListener mOnClickListener = new MyAdapter.MyOnClickListener();

        public MyAdapter(){
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_list_row,parent,false);
            itemView.setOnClickListener(mOnClickListener);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            String title = mViewModel.getData().getValue().get(position).getTitle();
            holder.title.setText(title);

            List<String> imagesList = mViewModel.getData().getValue().get(position).getImages();
            String image = "";
            if(imagesList != null && imagesList.size() != 0)
                image = mViewModel.getData().getValue().get(position).getImages().get(0);
            if(!image.isEmpty())
                Picasso.get().load(image).placeholder(R.drawable.placeholder).into(holder.image);
        }

        @Override
        public int getItemCount() {
            if(mViewModel.getData().getValue() != null){
                return mViewModel.getData().getValue().size();
            }else{
                return 0;
            }
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.auctionlistrow_title);
                image = itemView.findViewById(R.id.auction_row_picture);
            }
        }

        public class MyOnClickListener implements View.OnClickListener{

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