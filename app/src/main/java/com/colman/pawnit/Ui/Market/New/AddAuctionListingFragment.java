package com.colman.pawnit.Ui.Market.New;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;

import java.util.Calendar;

public class AddAuctionListingFragment extends Fragment {

    private AddAuctionListingViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_auction_listing_fragment, container, false);

        HorizontalScrollView images = view.findViewById(R.id.add_auction_addimage);
        EditText title = view.findViewById(R.id.add_auction_title);
        EditText startDate = view.findViewById(R.id.add_auction_sdate);
        EditText endDate = view.findViewById(R.id.add_auction_edate);
        EditText startingPrice = view.findViewById(R.id.add_auction_sprice);
        EditText description = view.findViewById(R.id.add_auction_description);
        ProgressBar progressBar = view.findViewById(R.id.add_auction_progressbar);

        Button addBtn = view.findViewById(R.id.add_auction_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);

                AuctionListing auctionListing = new AuctionListing();
                auctionListing.setTitle(title.getText().toString().trim());
                auctionListing.setStartingPrice(Double.parseDouble(startingPrice.getText().toString().trim()));
                auctionListing.setDescription(description.getText().toString().trim());
                auctionListing.setDateOpened(Calendar.getInstance().getTime());

                Model.instance.saveListing(auctionListing,()->{

                });
                Navigation.findNavController(v).navigateUp();
            }
        });

        return view;
    }

}