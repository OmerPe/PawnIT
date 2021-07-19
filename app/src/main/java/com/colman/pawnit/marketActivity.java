package com.colman.pawnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.colman.pawnit.Model.Listing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class marketActivity extends AppCompatActivity implements marketlistfragment.OnClick{

    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Market"));
        tabLayout.addTab(tabLayout.newTab().setText("Auction"));
        tabLayout.addTab(tabLayout.newTab().setText("Third Tab"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        addBtn = findViewById(R.id.fab);
        addBtn.setOnClickListener(v -> {
            tabSelector(tabLayout.getSelectedTabPosition());
        });


        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void tabSelector(int selectedTabPosition) {
        switch (selectedTabPosition){
            case 0:
                moveToActivity(addMarketItemFragment.class);
                break;
            case 1:
                moveToActivity(addAuctionItemFragment.class);
                break;
            case 2:
                moveToActivity(addAuctionItemFragment.class);
                break;


        }

    }

    private void moveToActivity(final Class<? extends Activity> ActivityToOpen){
        Intent intent = new Intent(this, ActivityToOpen);
        startActivity(intent);
    }


    @Override
    public void onDetails(Listing currentListing) {
        Intent i = new Intent(this,DetailItemListing.class);
        i.putExtra("listing", currentListing);
        startActivity(i);
    }



/*
    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        class aucViewHolder extends RecyclerView.ViewHolder {

            public aucViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        class resellViewHolder extends RecyclerView.ViewHolder {
            public resellViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }*/
}