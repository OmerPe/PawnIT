package com.colman.pawnit.Ui.Pawn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class PawnFragment extends Fragment {

    private PawnViewModel mViewModel;

    public static PawnFragment newInstance() {
        return new PawnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pawn_fragment, container, false);

        FloatingActionButton addBtn = view.findViewById(R.id.pawn_fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_pawnFragment_to_addPawnListingFragment);
            }
        });

        RecyclerView list = view.findViewById(R.id.PawnList_recyclerView);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(PawnViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });

        return view;
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
            String title = data.get(position).getTitle();
            String requested = Double.valueOf(data.get(position).getLoanAmountRequested()).toString();
            holder.title.setText(title);
            holder.requested.setText(requested);
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
                        Navigation.findNavController(view).navigate(R.id.action_pawnFragment_to_pawnListingFragment);
                    }
                });
            }
        }
    }
}