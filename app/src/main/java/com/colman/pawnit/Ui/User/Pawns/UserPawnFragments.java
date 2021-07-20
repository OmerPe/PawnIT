package com.colman.pawnit.Ui.User.Pawns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.R;

public class UserPawnFragments extends Fragment {

    private UserPawnFragmentsViewModel mViewModel;

    public static UserPawnFragments newInstance() {
        return new UserPawnFragments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_pawn_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserPawnFragmentsViewModel.class);
        // TODO: Use the ViewModel
    }

}