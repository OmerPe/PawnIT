package com.colman.pawnit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.pawnit.Model.Listing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class marketlistfragment extends Fragment {

    private RecyclerView recyclerList;
    private FloatingActionButton addBtn;
    private OnClick callback;

    public interface OnClick{
        void onAddClick();

        void onDetails(Listing currentListing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_marketlist, container, false);
        callback = (OnClick)getActivity();


        List<Listing> demo = new ArrayList<>();
        Listing listing1 = new Listing();
        listing1.setTitle("eden");
        Listing listing2 = new Listing();
        listing2.setTitle("Dor");
        demo.add(listing1);
        demo.add(listing2);


        addBtn = view.findViewById(R.id.fab);
        addBtn.setOnClickListener(v -> {
            callback.onAddClick();
        });

        recyclerList = view.findViewById(R.id.market_reclist);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setHasFixedSize(true);
        recyclerList.setAdapter(new MyAdapter(demo));


        return view;
    }

    public void goToDetails(Listing currentListing){
            if (callback != null)
                callback.onDetails(currentListing);
    }



     class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private List<Listing> mList;


        public MyAdapter(List<Listing> data){
            mList = data;
        }

         @NonNull
         @Override
         public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view =  LayoutInflater.from(getContext()).inflate(R.layout.market_list_row, parent, false);
             return new ViewHolder(view);
         }

         @Override
         public void onBindViewHolder(@NonNull marketlistfragment.MyAdapter.ViewHolder holder, int position) {

            Listing currentListing = mList.get(position);

            holder.title.setText(currentListing.getTitle());
           // holder.requested.setText(currentListing.p);


         }

         @Override
         public int getItemCount() {
             return mList.size();
         }

         class ViewHolder extends RecyclerView.ViewHolder {

            ConstraintLayout row;
            ImageView imageView;
            TextView title, price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.market_row_picture);
                title =itemView.findViewById(R.id.market_row_title);
                price =itemView.findViewById(R.id.market_row_Requested);
                row = itemView.findViewById(R.id.constraintLayout_row);


                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position  = getLayoutPosition();
                        Listing currentListing = mList.get(position);
                        goToDetails(currentListing);

                    }
                });
            }




        }


     }


}