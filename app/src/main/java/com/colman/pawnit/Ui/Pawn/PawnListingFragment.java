package com.colman.pawnit.Ui.Pawn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class PawnListingFragment extends Fragment {

    private PawnListingViewModel mViewModel;
    TextView requested;
    TextView due;
    TextView interest;
    TextView description;
    CollapsingToolbarLayout title;
    ImageButton popupMenu;

    public static PawnListingFragment newInstance() {
        return new PawnListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pawn_listing_fragment, container, false);
        requested = view.findViewById(R.id.pawn_listing_requested);
        due = view.findViewById(R.id.pawn_listing_due);
        interest = view.findViewById(R.id.pawn_listing_interest);
        description = view.findViewById(R.id.pawn_listing_description);
        popupMenu = view.findViewById(R.id.pawn_popup_menu);
        title = view.findViewById(R.id.pawn_collapsing_toolbar);

        String id = (String) getArguments().get("listingID");
        if(id != null){
            Model.instance.getPawnListing(id,(listing1 -> {
                PawnListing listing = (PawnListing)listing1;

                requested.setText(""+listing.getLoanAmountRequested());
                description.setText(listing.getDescription());
                Calendar cal = Calendar.getInstance();
                cal.setTime(listing.getWhenToGet());
                due.setText(cal.get(Calendar.DAY_OF_MONTH)+"\\"+cal.get(Calendar.MONTH)+"\\"+cal.get(Calendar.YEAR));
                title.setTitle(listing.getTitle());
                interest.setText(""+listing.getInterestRate());

            }));
        }

        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(),popupMenu);
                popup.getMenuInflater().inflate(R.menu.pawn_popup_menu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pawn_popup_edit:
                                //Navigation.findNavController(view).navigate(R.id.action_marketFragment_to_addResellListingFragment);
                                break;
                            case R.id.pawn_popup_delete:
                                // to do delete func
                                Navigation.findNavController(view).navigate(R.id.action_pawnListingFragment_to_userPawnFragments);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PawnListingViewModel.class);
        // TODO: Use the ViewModel
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        List<PawnListing> data = new LinkedList<>();

        public MyAdapter(){
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pawn_list_row,parent,false);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<PawnListing> list){
            this.data = list;
            notifyDataSetChanged();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
            }
        }
    }

}