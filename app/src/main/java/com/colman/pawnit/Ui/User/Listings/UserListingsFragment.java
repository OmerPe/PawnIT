package com.colman.pawnit.Ui.User.Listings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;
import com.colman.pawnit.Ui.Market.MarketFragmentDirections;
import com.colman.pawnit.Ui.Market.Market_list_fragment;
import com.colman.pawnit.Ui.User.Pawns.UserPawnFragments;
import com.colman.pawnit.Ui.User.Pawns.UserPawnFragmentsDirections;
import com.colman.pawnit.Ui.User.Pawns.UserPawnFragmentsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListingsFragment extends Fragment {

    private UserListingsViewModel mViewModel;
    RecyclerView list;

    public static UserListingsFragment newInstance() {
        return new UserListingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_listings_fragment, container, false);
        list = view.findViewById(R.id.listings_recyclerView);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(UserListingsViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.notifyDataSetChanged();
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserListingsViewModel.class);
        // TODO: Use the ViewModel
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
            List<String> imagesList;
            String image;
            switch (holder.getItemViewType()){
                case 0:
                    MyAdapter.ResellViewHolder viewHolder = (MyAdapter.ResellViewHolder) holder;
                    viewHolder.title.setText(mViewModel.getData().getValue().get(position).getTitle());
                    viewHolder.price.setText("" +((ResellListing)mViewModel.getData().getValue().get(position)).getPrice());
                    imagesList = mViewModel.getData().getValue().get(position).getImages();
                    image = "";
                    if(imagesList != null && imagesList.size() != 0)
                        image = mViewModel.getData().getValue().get(position).getImages().get(0);
                    if(!image.isEmpty())
                        Picasso.get().load(image).placeholder(R.drawable.placeholder).into(((MyAdapter.ResellViewHolder)holder).image);
                    break;
                case 1:
                    MyAdapter.AuctionViewHolder viewHolder1 = (MyAdapter.AuctionViewHolder) holder;
                    viewHolder1.title.setText(mViewModel.getData().getValue().get(position).getTitle());
                    viewHolder1.startingPrice.setText(""+((AuctionListing)mViewModel.getData().getValue().get(position)).getStartingPrice());
                    imagesList = mViewModel.getData().getValue().get(position).getImages();
                    image = "";
                    if(imagesList != null && imagesList.size() != 0)
                        image = mViewModel.getData().getValue().get(position).getImages().get(0);
                    if(!image.isEmpty())
                        Picasso.get().load(image).placeholder(R.drawable.placeholder).into(((MyAdapter.AuctionViewHolder)holder).image);
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if(mViewModel.getData().getValue() == null)
                return 0;
            return mViewModel.getData().getValue().size();
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
                String id = mViewModel.getData().getValue().get(itemPosition).getListingID();

                UserListingsFragmentDirections.ActionUserListingsFragmentToResellListFragment action =
                        UserListingsFragmentDirections.actionUserListingsFragmentToResellListFragment(id);
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
                String id = mViewModel.getData().getValue().get(itemPosition).getListingID();

                UserListingsFragmentDirections.ActionUserListingsFragmentToAuctionListingFragment action =
                        UserListingsFragmentDirections.actionUserListingsFragmentToAuctionListingFragment(id);
                Navigation.findNavController(v).navigate(action);

            }
        }
    }

}