package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;

public class Resell_listing_Fragment extends Fragment {

    TextView title;
    TextView price;
    TextView description;

    private ResellListingViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resell_listing, container, false);

        price = view.findViewById(R.id.resell_listing_priceTv);
        description = view.findViewById(R.id.resell_listing_descriptionTV);

        String id = (String) getArguments().get("listingID");
        if(id != null){
            Model.instance.getResellListing(id,(listing1 -> {
                if(listing1 !=null) {
                    ResellListing listing = (ResellListing) listing1;
                    title.setText(listing.getTitle());
                    description.setText(listing.getDescription());
                    price.setText("" + listing.getPrice());
                }
            }));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResellListingViewModel.class);
        // TODO: Use the ViewModel
    }

}