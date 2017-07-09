package com.tregouet.messesapp.modules.church;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Church;
import com.tregouet.messesapp.model.Schedule;
import com.tregouet.messesapp.modules.filter.FilterFragment;
import com.tregouet.messesapp.modules.search.SearchEvent;
import com.tregouet.messesapp.modules.search.SearchFragment;
import com.tregouet.messesapp.modules.tuto.TutoFragment;
import com.tregouet.messesapp.util.dialog.GlobalAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChurchActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.picture)
    ImageView picture;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.zipcode)
    TextView zipcode;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.website)
    TextView website;

    @BindView(R.id.mail)
    TextView mail;

    @BindView(R.id.masses)
    RecyclerView masses;

    @BindView(R.id.openings)
    RecyclerView openings;

    @BindView(R.id.confessions)
    RecyclerView confessions;

    @BindView(R.id.receptions)
    RecyclerView receptions;

    @BindView(R.id.adorations)
    RecyclerView adorations;

    @BindView(R.id.praises)
    RecyclerView praises;

    @BindView(R.id.rosaries)
    RecyclerView rosaries;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.schedules_layout)
    LinearLayout schedulesLayout;

    @BindView(R.id.contact_layout)
    LinearLayout contactLayout;

    private Church church;
    private SchedulesListAdapter massesAdapter;
    private SchedulesListAdapter openingsAdapter;
    private SchedulesListAdapter confessionsAdapter;
    private SchedulesListAdapter receptionsAdapter;
    private SchedulesListAdapter adorationsAdapter;
    private SchedulesListAdapter praisesAdapter;
    private SchedulesListAdapter rosariesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church);

        ButterKnife.bind(this);

        initRecyclerview();

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (mapView != null) {
            mapView.onStop();
        }
        super.onStop();
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

    @OnClick(R.id.contact)
    public void showContact() {
        if (contactLayout.getVisibility() == View.GONE){
            contactLayout.setVisibility(View.VISIBLE);
        } else {
            contactLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.schedules)
    public void showSchedules() {
        if (schedulesLayout.getVisibility() == View.GONE){
            schedulesLayout.setVisibility(View.VISIBLE);
        } else {
            schedulesLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.website)
    public void showWebsite() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(church.getWebsite()));
        startActivity(i);
    }

    @OnClick(R.id.phone)
    public void call() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);
        } else {
            final GlobalAlertDialog dialog = new GlobalAlertDialog(this);
            dialog.setTitle(getString(R.string.call));
            dialog.setMessage(church.getPhone());
            dialog.setPositiveButton(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + church.getPhone()));
                    startActivity(intent);
                }
            });
            dialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
    }

    @OnClick(R.id.mail)
    public void sendEmail() {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{church.getMail()});
        startActivity(Intent.createChooser(emailIntent, "Envoyer un email..."));
    }

    @Subscribe(sticky = true)
    public void onChurchEventReceived(ChurchEvent event) {
        church = event.getChurch();

        if (church != null) {
            setData();
        }
    }

    private void initRecyclerview() {
        masses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        massesAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        masses.setAdapter(massesAdapter);

        openings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        openingsAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        openings.setAdapter(openingsAdapter);

        confessions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        confessionsAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        confessions.setAdapter(confessionsAdapter);

        adorations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adorationsAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        adorations.setAdapter(adorationsAdapter);

        praises.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        praisesAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        praises.setAdapter(praisesAdapter);

        receptions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        receptionsAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        receptions.setAdapter(receptionsAdapter);

        rosaries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rosariesAdapter = new SchedulesListAdapter(this, new ArrayList<Schedule>());
        rosaries.setAdapter(rosariesAdapter);

    }

    private void setData() {
        title.setText(church.getName());
        address.setText(church.getAddress());
        zipcode.setText(String.format(getString(R.string.zipcode_city), church.getZipcode(), church.getCity()));
        phone.setText(church.getPhone());
        website.setText(church.getWebsite());

        if (church.getMail() != null && !church.getMail().equals("")) {
            mail.setVisibility(View.VISIBLE);
            mail.setText(church.getMail());
        } else {
            mail.setVisibility(View.GONE);
        }


        if (church.getImage() != null && !church.getImage().equals("")) {
            Glide.with(this)
                    .load(church.getImage())
                    .into(picture);
        }

        massesAdapter.refreshList(church.getActivity().getMasses());
        openingsAdapter.refreshList(church.getActivity().getOpenings());
        receptionsAdapter.refreshList(church.getActivity().getReceptions());
        praisesAdapter.refreshList(church.getActivity().getPraises());
        rosariesAdapter.refreshList(church.getActivity().getRosaries());
        adorationsAdapter.refreshList(church.getActivity().getAdorations());
        confessionsAdapter.refreshList(church.getActivity().getConfessions());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        marker.position(new LatLng(church.getLatitude(), church.getLongitude()));
        googleMap.addMarker(marker);

        zoomTo(googleMap, church.getLatitude(), church.getLongitude());
    }

    public void zoomTo(GoogleMap googleMap, double latitude, double longitude) {
        Log.i(getClass().getSimpleName(), "zoomTo(" + latitude + ", " + longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    final GlobalAlertDialog dialog = new GlobalAlertDialog(this);
                    dialog.setTitle(getString(R.string.call));
                    dialog.setMessage(church.getPhone());
                    dialog.setPositiveButton(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + church.getPhone()));
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
