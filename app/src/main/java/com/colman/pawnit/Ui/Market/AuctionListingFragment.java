package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;
import com.colman.pawnit.Ui.ContactPopup;
import com.colman.pawnit.Ui.Pawn.PawnListingFragmentDirections;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AuctionListingFragment extends Fragment implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap mMap;

    String id;
    AuctionListingViewModel mViewModel;
    TextView price;
    TextView countDown;
    TextView description;
    CollapsingToolbarLayout title;
    ImageButton popupMenu;
    ImageView image;
    MapView mMapView = null;
    ProgressBar progressBar;
    FloatingActionButton fab;
    String email;

    public static AuctionListingFragment newInstance() {
        return new AuctionListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auction_listing_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.auction_listing_mapView);
        initGoogleMap(savedInstanceState);

        price = view.findViewById(R.id.auction_listing_priceTv);
        countDown = view.findViewById(R.id.auction_listing_countdowntv);
        description = view.findViewById(R.id.auction_listing_descriptionTV);
        title = view.findViewById(R.id.auction_collapsing_toolbar);
        popupMenu = view.findViewById(R.id.auction_popup_menu);
        image = view.findViewById(R.id.auction_listing_image);
        fab = view.findViewById(R.id.auction_listing_fab);
        progressBar = view.findViewById(R.id.auction_listing_pb);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        id = (String) getArguments().get("listingID");
        if (id != null) {
            Model.instance.getAuctionListing(id, (listing1 -> {
                if (listing1 != null) {
                    AuctionListing listing = (AuctionListing) listing1;
                    description.setText(listing.getDescription());
                    price.setText("" + listing.getCurrentPrice());
                    title.setTitle(listing.getTitle());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(listing.getEndDate());
                    countDown.setText(cal.get(Calendar.DAY_OF_MONTH) + "\\" + cal.get(Calendar.MONTH) + "\\" + cal.get(Calendar.YEAR));
                    if (listing.getImages() != null && listing.getImages().size() != 0 &&
                            listing.getImages().get(0) != null && !listing.getImages().get(0).isEmpty())
                        Picasso.get().load(listing.getImages().get(0)).placeholder(R.drawable.placeholder).into(image);

                    String uId = listing.getOwnerId();
                    if (Model.instance.isLoggedIn()) {
                        if (uId != null && Model.instance.getLoggedUser().getUid().equals(uId)) {
                            popupMenu.setVisibility(View.VISIBLE);
                        }
                    }

                    LatLng latLng = new LatLng(listing.getLocation().getLatitude(), listing.getLocation().getLongitude());
                    MarkerOptions a = new MarkerOptions().position(latLng);
                    Marker m = mMap.addMarker(a);
                    m.setPosition(latLng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    progressBar.setVisibility(View.GONE);
                    Model.instance.getUserByID(listing.getOwnerId(),(user) -> {
                        email = user.getEmail();
                    });
                } else {
                    Navigation.findNavController(view).navigateUp();
                }
            }));
        }

        popupMenu.setVisibility(View.GONE);
        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), popupMenu);
                popup.getMenuInflater().inflate(R.menu.listing_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        progressBar.setVisibility(View.VISIBLE);
                        switch (item.getItemId()) {
                            case R.id.listing_popup_edit:
                                AuctionListingFragmentDirections.ActionAuctionListingFragmentToAddAuctionListingFragment action =
                                        AuctionListingFragmentDirections.actionAuctionListingFragmentToAddAuctionListingFragment(id);
                                Navigation.findNavController(v).navigate(action);
                                break;
                            case R.id.listing_popup_delete:
                                Model.instance.deleteAuctionListing(id, () -> {
                                    Navigation.findNavController(view).navigateUp();
                                });
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContact(email);
            }
        });

        return view;
    }

    public void openContact(String email){
        ContactPopup popup = new ContactPopup(email);
        popup.show(getActivity().getSupportFragmentManager(), "Email Popup");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuctionListingViewModel.class);
        // TODO: Use the ViewModel
    }

    //-------------------------MapView-------------------------------------------------------
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}