package com.colman.pawnit.Ui.User.Pawns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.R;
import com.colman.pawnit.Ui.Pawn.PawnFragment;
import com.colman.pawnit.Ui.Pawn.PawnViewModel;

import java.util.LinkedList;
import java.util.List;

public class UserPawnFragments extends Fragment {

    private UserPawnFragmentsViewModel mViewModel;

    public static UserPawnFragments newInstance() {
        return new UserPawnFragments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_pawn_fragment, container, false);
        RecyclerView list = view.findViewById(R.id.PawnList_recyclerView);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(UserPawnFragmentsViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserPawnFragmentsViewModel.class);
        // TODO: Use the ViewModel
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


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
            String title = mViewModel.getData().getValue().get(position).getTitle();
            String requested = Double.valueOf(mViewModel.getData().getValue().get(position).getLoanAmountRequested()).toString();
            holder.title.setText(title);
            holder.requested.setText(requested);
        }

        @Override
        public int getItemCount() {
            if(mViewModel.getData().getValue() == null)
                return 0;
            return mViewModel.getData().getValue().size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView requested;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.pawn_row_title);
                requested = itemView.findViewById(R.id.pawn_row_price);
                image = itemView.findViewById(R.id.pawn_row_picture);

                image.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(R.id.action_userPawnFragments_to_pawnListingFragment);
                    }
                });
            }
        }
    }
}