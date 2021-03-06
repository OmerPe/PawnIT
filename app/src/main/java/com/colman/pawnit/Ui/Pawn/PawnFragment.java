package com.colman.pawnit.Ui.Pawn;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PawnFragment extends Fragment {

    private PawnViewModel mViewModel;
    MyAdapter adapter;
    RecyclerView list;

    public static PawnFragment newInstance() {
        return new PawnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PawnViewModel.class);

        View view = inflater.inflate(R.layout.pawn_fragment, container, false);

        FloatingActionButton addBtn = view.findViewById(R.id.pawn_fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_pawnFragment_to_addPawnListingFragment);
            }
        });
        addBtn.setVisibility(View.GONE);
        if(Model.instance.isLoggedIn()){
            addBtn.setVisibility(View.VISIBLE);
        }

        list = view.findViewById(R.id.PawnList_recyclerView);
        adapter = new MyAdapter();

        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list.setHasFixedSize(true);
        list.setAdapter(adapter);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });

        TextInputEditText searchbox = view.findViewById(R.id.pawn_Searchbox);
        searchbox.addTextChangedListener(new TextWatcher() {
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


        ProgressBar progressBar = view.findViewById(R.id.pawn_list_pb);
        progressBar.setVisibility(View.GONE);

        Model.instance.pawnListingLoadingState.observe(getViewLifecycleOwner(),(state)->{
            switch (state){
                case loaded:
                    progressBar.setVisibility(View.GONE);
                    break;
                case loading:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

        private final View.OnClickListener mOnClickListener = new MyOnClickListener();
        List<PawnListing> listingsFiltered;

        public MyAdapter(){
            listingsFiltered = new ArrayList<>();
        }

        public void setData(List<PawnListing> listings){
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
                        List<PawnListing> filteredList = new ArrayList<>();
                        if(mViewModel.getData().getValue() != null){
                            for (PawnListing listing : mViewModel.getData().getValue()) {
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
                    listingsFiltered = (ArrayList<PawnListing>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pawn_list_row,parent,false);
            itemView.setOnClickListener(mOnClickListener);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            PawnListing pl = listingsFiltered.get(position);
            String title = pl.getTitle();
            String requested = Double.valueOf(pl.getLoanAmountRequested()).toString();
            holder.title.setText(title);
            holder.requested.setText(requested);

            List<String> imagesList = pl.getImages();
            String image = "";
            if(imagesList != null && imagesList.size() != 0)
                image = pl.getImages().get(0);
            if(!image.isEmpty())
                Picasso.get().load(image).placeholder(R.drawable.placeholder).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return listingsFiltered.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView title;
            TextView requested;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.pawn_row_title);
                requested = itemView.findViewById(R.id.pawn_row_price);
                image = itemView.findViewById(R.id.pawn_row_picture);
            }

        }

        public class MyOnClickListener implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = listingsFiltered.get(itemPosition).getListingID();

                PawnFragmentDirections.ActionPawnFragmentToPawnListingFragment action =
                        PawnFragmentDirections.actionPawnFragmentToPawnListingFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }
    }
}