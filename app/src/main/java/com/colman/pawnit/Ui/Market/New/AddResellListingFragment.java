package com.colman.pawnit.Ui.Market.New;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;

import java.util.Calendar;

public class AddResellListingFragment extends Fragment {

    private AddResellListingViewModel mViewModel;

    public static AddResellListingFragment newInstance() {
        return new AddResellListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_resell_listing_fragment, container, false);

        HorizontalScrollView scrollView= view.findViewById(R.id.add_resell_addimage);
        EditText title = view.findViewById(R.id.add_resell_title);
        EditText price = view.findViewById(R.id.add_resell_price);
        EditText desc = view.findViewById(R.id.add_resell_description);
        Button addBtn = view.findViewById(R.id.add_resell_add_btn);
        ProgressBar progressBar = view.findViewById(R.id.add_resell_progressbar);
        progressBar.setVisibility(View.GONE);
        ImageButton addImages = view.findViewById(R.id.add_resell_imageV);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                ResellListing listing = new ResellListing();

                listing.setTitle(title.getText().toString().trim());
                listing.setPrice(Double.parseDouble(price.getText().toString().trim()));
                listing.setDescription(desc.getText().toString().trim());
                listing.setDateOpened(Calendar.getInstance().getTime());
                listing.setOwnerId(Model.instance.getLoggedUser().getUid());

                Model.instance.saveListing(listing,()->{

                });
                Navigation.findNavController(v).navigateUp();
            }
        });
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "dor is cured", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}