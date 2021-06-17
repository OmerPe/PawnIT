package com.colman.pawnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView leftDrawer;
    NavigationView rightDrawer;
    ImageButton menuBtn;
    ImageButton profileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*------------------- hooks ---------------------------- */
        drawerLayout = findViewById(R.id.drawer_layout);
        leftDrawer = findViewById(R.id.nav_view_main_menu);
        rightDrawer = findViewById(R.id.nav_view_profile_menu);
        menuBtn = findViewById(R.id.MainAct_MenuBtn);
        profileBtn = findViewById(R.id.MainAct_UserIcon);

        /*------------------- Nav drawer menu ---------------------------- */
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,menuBtn,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}