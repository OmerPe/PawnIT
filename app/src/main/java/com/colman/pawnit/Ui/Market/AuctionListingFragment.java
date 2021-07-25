package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;

import java.util.Calendar;

public class AuctionListingFragment extends Fragment {

    TextView price;
    TextView countDown;
    TextView description;

    private AuctionListingViewModel mViewModel;

    public static AuctionListingFragment newInstance() {
        return new AuctionListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.auction_listing_fragment, container, false);

        price = view.findViewById(R.id.auction_listing_priceTv);
        countDown = view.findViewById(R.id.auction_listing_countdowntv);
        description = view.findViewById(R.id.auction_listing_descriptionTV);

        String id = (String) getArguments().get("listingID");
        if(id != null){
            Model.instance.getAuctionListing(id,(listing1 -> {
                if(listing1 !=null) {
                    AuctionListing listing = (AuctionListing) listing1;
                    description.setText(listing.getDescription());
                    price.setText("" + listing.getCurrentPrice());

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(listing.getEndDate());
                    countDown.setText(cal.get(Calendar.DAY_OF_MONTH) + "\\" + cal.get(Calendar.MONTH) + "\\" + cal.get(Calendar.YEAR));
                }
            }));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuctionListingViewModel.class);
        // TODO: Use the ViewModel
    }

}