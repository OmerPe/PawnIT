package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.ResellListing;
import com.colman.pawnit.R;

import java.util.LinkedList;
import java.util.List;


public class Resell_list_fragment extends Fragment {

    private ResellListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resell_list_fragment, container, false);

        RecyclerView list = view.findViewById(R.id.Resell_list_rv);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(ResellListViewModel.class);

        mViewModel.getData().observe(getViewLifecycleOwner(),(data)->{
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        List<ResellListing> data = new LinkedList<>();

        public MyAdapter(){
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resell_list_row,parent,false);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            String title = data.get(position).getTitle();
            holder.title.setText(title);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<ResellListing> list){
            this.data = list;
            notifyDataSetChanged();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Resell_row_title);
            }
        }
    }
}