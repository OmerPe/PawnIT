package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class Resell_listing_Fragment extends Fragment {

    CollapsingToolbarLayout title;
    TextView price;
    TextView description;
    ImageView image;
    private ResellListingViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resell_listing, container, false);
        price = view.findViewById(R.id.resell_listing_priceTv);
        description = view.findViewById(R.id.resell_listing_descriptionTV);
        title = view.findViewById(R.id.resell_collapsing_toolbar);
        image = view.findViewById(R.id.resell_listing_image);

        String id = (String) getArguments().get("listingID");
        if(id != null){
            Model.instance.getResellListing(id,(listing1 -> {
                if(listing1 != null) {
                    ResellListing listing = (ResellListing) listing1;

                    description.setText(listing.getDescription());
                    price.setText("" + listing.getPrice());
                    title.setTitle(listing.getTitle());
                    if(listing.getImages() != null && listing.getImages().size() != 0 &&
                            listing.getImages().get(0) != null && !listing.getImages().get(0).isEmpty())
                        Picasso.get().load(listing.getImages().get(0)).placeholder(R.drawable.placeholder).into(image);
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