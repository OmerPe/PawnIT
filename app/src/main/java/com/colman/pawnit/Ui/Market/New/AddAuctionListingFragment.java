package com.colman.pawnit.Ui.Market.New;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.R;

public class AddAuctionListingFragment extends Fragment {

    private AddAuctionListingViewModel mViewModel;

    public static AddAuctionListingFragment newInstance() {
        return new AddAuctionListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_auction_listing_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAuctionListingViewModel.class);
        // TODO: Use the ViewModel
    }

}