package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

public class MarketFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    public TextInputEditText searchBox;

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Market"));
        tabLayout.addTab(tabLayout.newTab().setText("2nd Hand"));
        tabLayout.addTab(tabLayout.newTab().setText("Auctions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        viewPager = view.findViewById(R.id.market_view_pager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(),getLifecycle()));


        searchBox = view.findViewById(R.id.market_Searchbox);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        FloatingActionButton addBtn = view.findViewById(R.id.market_fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(),addBtn);
                popup.getMenuInflater().inflate(R.menu.market_add_menu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.market_menu_addResell:
                                Navigation.findNavController(view).navigate(R.id.action_marketFragment_to_addResellListingFragment);
                                break;
                            case R.id.market_menu_addAuction:
                                Navigation.findNavController(view).navigate(R.id.action_marketFragment_to_addAuctionListingFragment);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        addBtn.setVisibility(View.GONE);

        if(Model.instance.isLoggedIn()){
            addBtn.setVisibility(View.VISIBLE);
        }

        ProgressBar progressBar = view.findViewById(R.id.Market_progressbar);
        progressBar.setVisibility(View.GONE);
        Model.instance.auctionLoadingState.observe(getViewLifecycleOwner(),(state)->{
            switch (state){
                case loaded:
                    progressBar.setVisibility(View.GONE);
                    break;
                case loading:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });
        Model.instance.resellLoadingState.observe(getViewLifecycleOwner(),(state)->{
            switch (state){
                case loaded:
                    progressBar.setVisibility(View.GONE);
                    break;
                case loading:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });

        return view;
    }

    private class MyAdapter extends FragmentStateAdapter {
        public MyAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
            super(fm,lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new Market_list_fragment();
                case 1:
                    return new Resell_list_fragment();
                case 2:
                    return new Auction_list_fragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return tabLayout.getTabCount();
        }
    }
}