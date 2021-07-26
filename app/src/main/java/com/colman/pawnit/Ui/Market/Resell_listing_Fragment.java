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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;
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

public class Resell_listing_Fragment extends Fragment implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap mMap;

    String id;
    ResellListingViewModel mViewModel;
    CollapsingToolbarLayout title;
    TextView price;
    TextView description;
    ImageView image;
    MapView mMapView = null;
    ProgressBar progressBar;
    ImageButton popupMenu;
    FloatingActionButton fab;

    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resell_listing, container, false);

        mMapView = (MapView) view.findViewById(R.id.resell_listing_mapView);
        initGoogleMap(savedInstanceState);

        price = view.findViewById(R.id.resell_listing_priceTv);
        description = view.findViewById(R.id.resell_listing_descriptionTV);
        title = view.findViewById(R.id.resell_collapsing_toolbar);
        image = view.findViewById(R.id.resell_listing_image);
        popupMenu = view.findViewById(R.id.resell_popup_menu);
        fab = view.findViewById(R.id.resell_listing_fab);
        progressBar = view.findViewById(R.id.resell_listing_pb);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();


        id = (String) getArguments().get("listingID");
        if (id != null) {
            Model.instance.getResellListing(id, (listing1 -> {
                if (listing1 != null) {
                    ResellListing listing = (ResellListing) listing1;
                    description.setText(listing.getDescription());
                    price.setText("" + listing.getPrice());
                    title.setTitle(listing.getTitle());
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
                                Resell_listing_FragmentDirections.ActionResellListFragmentToAddResellListingFragment action =
                                        Resell_listing_FragmentDirections.actionResellListFragmentToAddResellListingFragment(id);
                                Navigation.findNavController(v).navigate(action);
                                break;
                            case R.id.listing_popup_delete:
                                Model.instance.deleteResellListing(id, () -> {
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
        mViewModel = new ViewModelProvider(this).get(ResellListingViewModel.class);
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