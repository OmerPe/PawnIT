package com.colman.pawnit.Ui.Pawn.New;

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
import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.R;

import java.util.Calendar;

public class AddPawnListingFragment extends Fragment {

    private AddPawnListingViewModel mViewModel;

    public static AddPawnListingFragment newInstance() {
        return new AddPawnListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_pawn_listing_fragment, container, false);

        HorizontalScrollView images = view.findViewById(R.id.add_pawn_addimage);
        EditText title = view.findViewById(R.id.add_pawn_title);
        EditText requested = view.findViewById(R.id.add_pawn_requested);
        EditText interestRate = view.findViewById(R.id.add_pawn_interest);
        EditText startingDate = view.findViewById(R.id.add_pawn_date);
        EditText description = view.findViewById(R.id.add_pawn_description);
        ProgressBar progressBar = view.findViewById(R.id.add_pawn_progressbar);

        Button addBtn = view.findViewById(R.id.add_pawn_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);

                PawnListing pawnListing = new PawnListing();
                pawnListing.setTitle(title.getText().toString().trim());
                pawnListing.setLoanAmountRequested(Double.parseDouble(requested.getText().toString().trim()));
                pawnListing.setInterestRate(Double.parseDouble(interestRate.getText().toString().trim()));
                pawnListing.setDescription(description.getText().toString().trim());
                pawnListing.setDateOpened(Calendar.getInstance().getTime());


                Model.instance.saveListing(pawnListing,()->{

                });
                Navigation.findNavController(v).navigateUp();
            }
        });

        return view;
    }

}