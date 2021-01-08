package com.margretcraft.weatherforecasterv2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Marker marker;
    private GoogleMap mMap;
    private String[] points;
    Snackbar snackbarOK;
    Snackbar snackbarNO;
    Button findMe;
    DecimalFormat df;
    private static final int PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle arguments = getIntent().getExtras();

        points = arguments.get("points").toString().split(",");
        FrameLayout ll = (FrameLayout) findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        snackbarOK = Snackbar.make(findViewById(R.id.map), R.string.chooseThis, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbarOK.setAction(R.string.OK, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writePointAndClose();
            }
        });
        snackbarNO = Snackbar.make(findViewById(R.id.map), R.string.NoDataForPoint, BaseTransientBottomBar.LENGTH_LONG);

        df = new DecimalFormat();
        df.setMaximumFractionDigits(3);

        findMe = new MaterialButton(this);
        findMe.setText(R.string.findMe);
        findMe.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        findMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findUser();
            }
        });
        ((FrameLayout) findViewById(R.id.map)).addView(findMe);

    }

    private void findUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
        }
    }

    private void requestLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {

            locationManager.requestLocationUpdates(provider, 1000, 1, new LocationListener() {

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat = location.getLatitude(); // Широта
                    double lng = location.getLongitude(); // Долгота

                    LatLng currentPosition = new LatLng(lat, lng);
                    getAddress(currentPosition);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, (float)12));
                    locationManager.removeUpdates(this);
                }


                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
            });
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void writePointAndClose() {

        if (marker != null) {
            SharedPreferences sharedPref = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("townName", marker.getTitle());
            editor.putString("townPoint", ("" + marker.getPosition().longitude).replace(",", ".") + "," + ("" + marker.getPosition().latitude).replace(",", "."));
            editor.apply();
        }

        Intent myIntent = new Intent(MapsActivity.this, MainNdActivity.class);
        startActivity(myIntent);
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng currentTown = new LatLng(Double.parseDouble(points[1]), Double.parseDouble(points[0]));
        mMap.addMarker(new MarkerOptions().position(currentTown));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentTown));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                getAddress(latLng);

            }
        });
    }

    private void getAddress(final LatLng location) {
        final Geocoder geocoder = new Geocoder(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(df.format(location.latitude)), Double.parseDouble(df.format(location.longitude)), 1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addMarker(location, addresses.get(0).getCountryName() + " " + (addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : addresses.get(0).getAdminArea()));
                            snackbarOK.show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    snackbarNO.show();
                }
            }
        }).start();
    }

    private void addMarker(LatLng location, String title) {
        mMap.clear();
        marker = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pointmap)));
        marker.showInfoWindow();
    }


    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(MapsActivity.this, MainNdActivity.class);
        startActivity(myIntent);
        finish();
        super.onBackPressed();
    }
}
