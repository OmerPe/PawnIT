package com.colman.pawnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        leftDrawer.bringToFront();
        rightDrawer.bringToFront();
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

        leftDrawer.setNavigationItemSelectedListener(this);
        rightDrawer.setNavigationItemSelectedListener(this);

        leftDrawer.setCheckedItem(R.id.MainMenu_home);

        // hide items


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.MainMenu_home:
                break;
            case R.id.MainMenu_2ndHand:
                startActivity(new Intent(MainActivity.this,secondHandActivity.class));
                break;
            case R.id.MainMenu_auction:
                startActivity(new Intent(MainActivity.this,auctionActivity.class));
                break;
            case R.id.MainMenu_pawn:
                startActivity(new Intent(MainActivity.this,pawnActivity.class));
                break;
            case R.id.my_listings:
                startActivity(new Intent(MainActivity.this,myListingsActivity.class));
                break;
            case R.id.my_pawns:
                startActivity(new Intent(MainActivity.this,myPawnsActivity.class));
                break;
            case R.id.history:
                startActivity(new Intent(MainActivity.this,userHistoryActivity.class));
                break;
            case R.id.contacts:
                startActivity(new Intent(MainActivity.this,contactsActivity.class));
                break;
            case R.id.user_settings:
                startActivity(new Intent(MainActivity.this,userSettingsActivity.class));
                break;
            case R.id.app_settings:
                startActivity(new Intent(MainActivity.this,appSettingsActivity.class));
                break;
            case R.id.MainMenu_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.MainMenu_rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
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