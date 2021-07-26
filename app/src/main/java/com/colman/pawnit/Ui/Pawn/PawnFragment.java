package com.colman.pawnit.Ui.Pawn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PawnFragment extends Fragment {

    private PawnViewModel mViewModel;
    MyAdapter adapter;
    RecyclerView list;

    public static PawnFragment newInstance() {
        return new PawnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PawnViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.notifyDataSetChanged();
        });

        View view = inflater.inflate(R.layout.pawn_fragment, container, false);

        FloatingActionButton addBtn = view.findViewById(R.id.pawn_fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_pawnFragment_to_addPawnListingFragment);
            }
        });
        addBtn.setVisibility(View.GONE);
        if(Model.instance.isLoggedIn()){
            addBtn.setVisibility(View.VISIBLE);
        }

        list = view.findViewById(R.id.PawnList_recyclerView);
        adapter = new MyAdapter();

        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list.setHasFixedSize(true);
        list.setAdapter(adapter);


        ProgressBar progressBar = view.findViewById(R.id.pawn_list_pb);
        progressBar.setVisibility(View.GONE);

        Model.instance.pawnListingLoadingState.observe(getViewLifecycleOwner(),(state)->{
            switch (state){
                case loaded:
                    progressBar.setVisibility(View.GONE);
                    break;
                case loading:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private final View.OnClickListener mOnClickListener = new MyOnClickListener();

        public MyAdapter(){
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pawn_list_row,parent,false);
            itemView.setOnClickListener(mOnClickListener);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            String title = mViewModel.getData().getValue().get(position).getTitle();
            String requested = Double.valueOf(mViewModel.getData().getValue().get(position).getLoanAmountRequested()).toString();
            holder.title.setText(title);
            holder.requested.setText(requested);

            List<String> imagesList = mViewModel.getData().getValue().get(position).getImages();
            String image = "";
            if(imagesList != null && imagesList.size() != 0)
                image = mViewModel.getData().getValue().get(position).getImages().get(0);
            if(!image.isEmpty())
                Picasso.get().load(image).placeholder(R.drawable.placeholder).into(holder.image);
        }

        @Override
        public int getItemCount() {
            if(mViewModel.getData().getValue() == null){
                return 0;
            }else{
                return mViewModel.getData().getValue().size();
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView title;
            TextView requested;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.pawn_row_title);
                requested = itemView.findViewById(R.id.pawn_row_price);
                image = itemView.findViewById(R.id.pawn_row_picture);
            }

        }

        public class MyOnClickListener implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                int itemPosition = list.getChildLayoutPosition(v);
                String id = mViewModel.getData().getValue().get(itemPosition).getListingID();

                PawnFragmentDirections.ActionPawnFragmentToPawnListingFragment action =
                        PawnFragmentDirections.actionPawnFragmentToPawnListingFragment(id);
                Navigation.findNavController(v).navigate(action);
            }
        }
    }
}