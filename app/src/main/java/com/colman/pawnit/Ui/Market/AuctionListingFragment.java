package com.colman.pawnit.Ui.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AuctionListingFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap mMap;

    TextView price;
    TextView countDown;
    TextView description;
    CollapsingToolbarLayout title;
    ImageView image;
    MapView mMapView = null;

    private AuctionListingViewModel mViewModel;

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
        image = view.findViewById(R.id.auction_listing_image);
        String id = (String) getArguments().get("listingID");
        if(id != null){
            Model.instance.getAuctionListing(id,(listing1 -> {
                if(listing1 != null) {
                    AuctionListing listing = (AuctionListing) listing1;
                    description.setText(listing.getDescription());
                    price.setText("" + listing.getCurrentPrice());
                    title.setTitle(listing.getTitle());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(listing.getEndDate());
                    countDown.setText(cal.get(Calendar.DAY_OF_MONTH) + "\\" + cal.get(Calendar.MONTH) + "\\" + cal.get(Calendar.YEAR));
                    if(listing.getImages() != null && listing.getImages().size() != 0 &&
                            listing.getImages().get(0) != null && !listing.getImages().get(0).isEmpty())
                        Picasso.get().load(listing.getImages().get(0)).placeholder(R.drawable.placeholder).into(image);

                    LatLng latLng = new LatLng(listing.getLocation().getLatitude(),listing.getLocation().getLongitude());
                    MarkerOptions a = new MarkerOptions().position(latLng);
                    Marker m = mMap.addMarker(a);
                    m.setPosition(latLng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
            }));
        }
        return view;
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
        mMap=map;
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