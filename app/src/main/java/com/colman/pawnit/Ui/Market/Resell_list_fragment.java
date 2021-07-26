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

import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Resell_list_fragment extends Fragment {

    private ResellListViewModel mViewModel;
    RecyclerView list;
    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resell_list_fragment, container, false);

        list = view.findViewById(R.id.Resell_list_rv);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(ResellListViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(), (data) -> {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

        private final View.OnClickListener mOnClickListener = new MyAdapter.MyOnClickListener();
        List<ResellListing> listingsFiltered;

        public MyAdapter() {
            listingsFiltered = new ArrayList<>();
        }

        public void setData(List<ResellListing> listings){
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
                        List<ResellListing> filteredList = new ArrayList<>();
                        if(mViewModel.getData().getValue() != null){
                            for (ResellListing listing : mViewModel.getData().getValue()) {
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
                    listingsFiltered = (ArrayList<ResellListing>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resell_list_row, parent, false);
            itemView.setOnClickListener(mOnClickListener);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            ResellListing rl = mViewModel.getData().getValue().get(position);
            String title = rl.getTitle();
            holder.title.setText(title);

            List<String> imagesList = rl.getImages();
            String image = "";
            if(imagesList != null && imagesList.size() != 0)
                image = rl.getImages().get(0);
            if(!image.isEmpty())
                Picasso.get().load(image).placeholder(R.drawable.placeholder).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return listingsFiltered.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Resell_row_title);
                image = itemView.findViewById(R.id.Resell_row_picture);
            }
        }

        public class MyOnClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = listingsFiltered.get(itemPosition).getListingID();

                MarketFragmentDirections.ActionMarketFragmentToResellListFragment action =
                        MarketFragmentDirections.actionMarketFragmentToResellListFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }
    }
}