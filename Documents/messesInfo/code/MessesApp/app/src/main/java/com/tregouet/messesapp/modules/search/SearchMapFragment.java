package com.tregouet.messesapp.modules.search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.SearchResult;
import com.tregouet.messesapp.modules.church.ChurchActivity;
import com.tregouet.messesapp.modules.church.ChurchEvent;
import com.tregouet.messesapp.util.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mctregouet on 02/11/2016.
 */

public class SearchMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.picture)
    ImageView picture;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.zipcode)
    TextView zipcode;

    @BindView(R.id.schedule)
    TextView schedule;

    private GoogleMap googleMap;
    private HashMap<Marker, SearchResult> hashMap = new HashMap<>();
    private ArrayList<Marker> markers = new ArrayList<>();
    private float radius = 50;
    private float zoom = 8;
    private float minZoom = 18;
    private double latitude = 0.0f;
    private double longitude = 0.0f;
    private BitmapDescriptor pin;
    private BitmapDescriptor pinSelected;
    private Marker selectedMarker;
    BottomSheetBehavior bottomSheetBehavior = new BottomSheetBehavior();
    private long mOptionsLastClickTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_map, container, false);

        ButterKnife.bind(this, view);

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());

        return view;
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onSearchEventReceived(SearchEvent event) {
        for (SearchResult result : event.getResults()){
            MarkerOptions marker = new MarkerOptions();
            marker.icon(pin);
            marker.title(result.getChurch().getName());
            marker.snippet(result.getChurch().getAddress());
            marker.position(new LatLng(result.getChurch().getLatitude(), result.getChurch().getLongitude()));

            Marker createdMarker = googleMap.addMarker(marker);
            hashMap.put(createdMarker, result);
            markers.add(createdMarker);
        }

        if (!markers.isEmpty()) {
            zoomToSeeAllMarkers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            try {
                mapView.onDestroy();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pin = BitmapDescriptorFactory.fromResource(R.drawable.pin);
        pinSelected = BitmapDescriptorFactory.fromResource(R.drawable.pin_selected);
        this.googleMap = googleMap;
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
            }
        });
        googleMap.setTrafficEnabled(false);
        googleMap.setOnMarkerClickListener(this);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            //TODO zoom
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    124);
        }

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                //TODO search again
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i(getClass().getSimpleName(), "onMarkerClick markerId=" + marker);
        if (selectedMarker != null) {
            System.out.println("selectedMarker.getId=" + selectedMarker.getId());
            System.out.println("marker.getId=" + marker.getId());
            if (selectedMarker.getId().equals(marker.getId())){
                selectedMarker.setIcon(pin);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                selectedMarker = null;
                return true;
            } else {
                selectedMarker.setIcon(pin);
            }
        }
        marker.setIcon(pinSelected);
        selectedMarker = marker;

        if (hashMap.get(marker) != null) {
            System.out.println("onMarkerClick : " + hashMap.get(marker));
            System.out.println("onMarkerClick getName(): " + hashMap.get(marker).getChurch().getName());
            name.setText(hashMap.get(marker).getChurch().getName());
            address.setText(hashMap.get(marker).getChurch().getAddress());
            if (hashMap.get(marker).getChurch().getZipcode() != null && !hashMap.get(marker).getChurch().getZipcode().equals("") && hashMap.get(marker).getChurch().getCity() != null && !hashMap.get(marker).getChurch().getCity().equals("")) {
                zipcode.setText(String.format(getActivity().getString(R.string.zipcode_city), hashMap.get(marker).getChurch().getZipcode(), hashMap.get(marker).getChurch().getCity()));
            } else if (hashMap.get(marker).getChurch().getZipcode() != null) {
                zipcode.setText(hashMap.get(marker).getChurch().getZipcode());
            } else if (hashMap.get(marker).getChurch().getCity() != null) {
                zipcode.setText(hashMap.get(marker).getChurch().getCity());
            } else {
                zipcode.setText("");
            }
            if (hashMap.get(marker).getChurch().getImage() != null && !hashMap.get(marker).getChurch().getImage().equals("")) {
                Glide.with(getActivity())
                        .load(hashMap.get(marker).getChurch().getImage())
                        .into(picture);
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        return true;
    }

    public void zoomToSeeAllMarkers() {
        Log.i(getClass().getSimpleName(), "zoomToSeeAllMarkers");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);

        this.latitude = googleMap.getCameraPosition().target.latitude;
        this.longitude = googleMap.getCameraPosition().target.longitude;
        this.zoom = googleMap.getCameraPosition().zoom;
        if (zoom > minZoom) {
            this.zoom = minZoom;
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(minZoom));
        }

        this.zoom = googleMap.getCameraPosition().zoom;
    }

    @OnClick(R.id.background)
    public void showChurch(){
        if (SystemClock.elapsedRealtime() - mOptionsLastClickTime < 1000) {
            return;
        }
        mOptionsLastClickTime = SystemClock.elapsedRealtime();

        if (Tools.isNetworkAvailable(getActivity())) {
            EventBus.getDefault().postSticky(new ChurchEvent(hashMap.get(selectedMarker).getChurch()));
            getActivity().startActivity(new Intent(getActivity(), ChurchActivity.class));
        }
    }

    @OnClick(R.id.localize)
    public void localize() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            goToMyPosition();
        } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
            }
    }

    private void goToMyPosition() {
        Location location = googleMap.getMyLocation();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

    }
}
