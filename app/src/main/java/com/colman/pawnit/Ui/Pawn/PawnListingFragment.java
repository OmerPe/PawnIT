package com.colman.pawnit.Ui.Pawn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class PawnListingFragment extends Fragment {

    private PawnListingViewModel mViewModel;
    TextView requested;
    TextView due;
    TextView interest;
    TextView description;
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