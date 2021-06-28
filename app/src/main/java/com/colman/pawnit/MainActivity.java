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
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView leftDrawer;
    NavigationView rightDrawer;
    ImageButton menuBtn;
    ImageButton profileBtn;
    TextView username,pdUname;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String Uid;

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
        username = (TextView) findViewById(R.id.MainAct_Username);
        pdUname = rightDrawer.getHeaderView(0).findViewById(R.id.profileDrawer_username);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");



        /*------------------- Nav drawer menu ---------------------------- */
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,menuBtn,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        leftDrawer.bringToFront();
        rightDrawer.bringToFront();
        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        profileBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));

        leftDrawer.setNavigationItemSelectedListener(this);
        rightDrawer.setNavigationItemSelectedListener(this);

        leftDrawer.setCheckedItem(R.id.MainMenu_home);

        // hide items


        //user settings
        if(user != null){
            Uid = user.getUid();
            reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile != null){
                        String fullname = userProfile.userName;
                        username.setText(fullname + "!");
                        pdUname.setText(fullname);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            });
            showUserOptions();
        }else{
            hideUserOptions();
        }

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
                mAuth.signOut();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                startActivity(new Intent(MainActivity.this,loginActivity.class));
                break;
        }

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }

        return true;
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

        pdUname.setText("Please log in");
    }
    private void showUserOptions() {
        MenuItem listings = rightDrawer.getMenu().findItem(R.id.my_listings);
        listings.setVisible(true);
        MenuItem pawns = rightDrawer.getMenu().findItem(R.id.my_pawns);
        pawns.setVisible(true);
        MenuItem hist = rightDrawer.getMenu().findItem(R.id.history);;
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