package com.colman.pawnitv2.Ui.Pawn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnitv2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        return view;
    }

}