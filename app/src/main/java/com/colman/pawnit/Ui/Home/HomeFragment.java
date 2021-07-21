package com.colman.pawnit.Ui.Home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnit.R;
import com.google.android.material.navigation.NavigationView;


public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView leftDrawer, rightDrawer;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        ImageButton menuBtn = view.findViewById(R.id.HomeFrag_MenuBtn);
        ImageButton profileBtn = view.findViewById(R.id.HomeFrag_UserIcon);
        leftDrawer = view.findViewById(R.id.nav_view_main_menu);
        rightDrawer = view.findViewById(R.id.nav_view_profile_menu);

        leftDrawer.bringToFront();
        rightDrawer.bringToFront();

        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        profileBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));

        leftDrawer.setNavigationItemSelectedListener(this);
        rightDrawer.setNavigationItemSelectedListener(this);


        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MainMenu_home:
                break;
            case R.id.MainMenu_market:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_marketFragment);
                break;
            case R.id.MainMenu_pawn:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_pawnFragment);
                break;
            case R.id.my_listings:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userListingsFragment);
                break;
            case R.id.my_pawns:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userPawnFragments);
                break;
            case R.id.history:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_historyFragment);
                break;
            case R.id.user_settings:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userSettingsFragment);
                break;
            case R.id.app_settings:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_appSettingsFragment);
                break;
            case R.id.MainMenu_share:
                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.MainMenu_rate:
                Toast.makeText(getActivity(), "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
                break;
        }

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }

        return true;
    }
}