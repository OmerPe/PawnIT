package com.colman.pawnit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.colman.pawnit.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestFragment extends Fragment {

    DrawerLayout drawerLayout;
    NavigationView leftDrawer;
    NavigationView rightDrawer;
    ImageButton menuBtn;
    ImageButton profileBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String Uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        leftDrawer = view.findViewById(R.id.nav_view_main_menu);
        rightDrawer = view.findViewById(R.id.nav_view_profile_menu);
        menuBtn = view.findViewById(R.id.MainAct_MenuBtn);
        profileBtn = view.findViewById(R.id.MainAct_UserIcon);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,me nuBtn,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        leftDrawer.bringToFront();
        rightDrawer.bringToFront();
        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        profileBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));

        leftDrawer.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.MainMenu_home:
                    break;
                case R.id.MainMenu_market:
                    startActivity(new Intent(getActivity(), marketActivity.class));
                    break;
                case R.id.MainMenu_pawn:
                    startActivity(new Intent(getActivity(),pawnActivity.class));
                    break;
                default:
            }

            if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.drawerLayout.closeDrawer(GravityCompat.START);
            } else if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                this.drawerLayout.closeDrawer(GravityCompat.END);
            }

            return true;
        });

        rightDrawer.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.history:
                    Navigation.findNavController(view).navigate(R.id.action_testFragment_to_history2);
                    break;
                case R.id.contacts:
                    Navigation.findNavController(view).navigate(R.id.action_testFragment_to_contacts2);
                    break;
                case R.id.my_listings:
                    Navigation.findNavController(view).navigate(R.id.action_testFragment_to_myListings);
                default:
            }

            if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.drawerLayout.closeDrawer(GravityCompat.START);
            } else if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                this.drawerLayout.closeDrawer(GravityCompat.END);
            }

            return true;
        });

        leftDrawer.setCheckedItem(R.id.MainMenu_home);

        if (user != null) {
            Uid = user.getUid();
            reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null) {
                        String fullname = userProfile.getUserName();
                        //  username.setText(fullname + "!");
                        // pdUname.setText(fullname);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
            showUserOptions();
        } else {
            hideUserOptions();
        }
        rightDrawer.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mAuth.signOut();

                Navigation.findNavController(view).navigate(R.id.action_testFragment_self2);
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        rightDrawer.getMenu().findItem(R.id.login).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), loginActivity.class));
                return true;
            }
        });
        return view;
    }

    private void hideUserOptions() {
        MenuItem listings = rightDrawer.getMenu().findItem(R.id.my_listings);
        listings.setVisible(false);
        MenuItem pawns = rightDrawer.getMenu().findItem(R.id.my_pawns);
        pawns.setVisible(false);
        MenuItem hist = rightDrawer.getMenu().findItem(R.id.history);
        hist.setVisible(false);
        MenuItem con = rightDrawer.getMenu().findItem(R.id.contacts);
        con.setVisible(false);
        MenuItem us = rightDrawer.getMenu().findItem(R.id.user_settings);
        us.setVisible(false);

        MenuItem login = rightDrawer.getMenu().findItem(R.id.login);
        login.setVisible(true);
        MenuItem logout = rightDrawer.getMenu().findItem(R.id.logout);
        logout.setVisible(false);

    }

    private void showUserOptions() {
        MenuItem listings = rightDrawer.getMenu().findItem(R.id.my_listings);
        listings.setVisible(true);
        MenuItem pawns = rightDrawer.getMenu().findItem(R.id.my_pawns);
        pawns.setVisible(true);
        MenuItem hist = rightDrawer.getMenu().findItem(R.id.history);
        ;
        hist.setVisible(true);
        MenuItem con = rightDrawer.getMenu().findItem(R.id.contacts);
        con.setVisible(true);
        MenuItem us = rightDrawer.getMenu().findItem(R.id.user_settings);
        us.setVisible(true);

        MenuItem login = rightDrawer.getMenu().findItem(R.id.login);
        login.setVisible(false);
        MenuItem logout = rightDrawer.getMenu().findItem(R.id.logout);
        logout.setVisible(true);
    }
}