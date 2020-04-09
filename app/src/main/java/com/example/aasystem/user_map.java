package com.example.aasystem;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.aasystem.user.fragment.UserNav;
import com.example.aasystem.user.activities.FingerPrintActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;

public class user_map extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    // Connect all classes

    private GoogleMap mMap;
    private LocationManager lm;//GPS Library
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Circle mCircle;
    private Location location;
    private double myLat;
    private double myLon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        /**Obtain the SupportMapFragment and get notified when the map is ready to be used.*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        /**To obtain user location every time it's changed*/

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        location = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);


    }


    /**Call Maps */

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient" , Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
    }

    /**Callback location's updates*/

    private void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected" , Toast.LENGTH_SHORT).show();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(0.1F);

        LocationServices.getFusedLocationProviderClient(this);
    }

    /**Get the last known location of the device*/
    @Override
    protected void onPause(){
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this);
        }
    }

    /**When map is ready for use */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng center = new LatLng(24.472209, 39.568258); // Seu domain
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center ,15)); // move camera to the point
        Marker melporne = mMap.addMarker(new MarkerOptions().position(center).title("Saudi Electronic University")); //Set pin and title for location
        melporne.showInfoWindow();
        mMap.getUiSettings().setZoomControlsEnabled(true);// zoom in / out

        /**Display the location of user*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        {
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);

            circle();// Display the circle in which user exist


            float distance[] = new float[2];

            Location.distanceBetween(myLat, myLon, 24.472209, 39.568258, distance); // Compare between company and user location

            if (distance[0] >150) { // If user location in less than the specified circle , ask for fingerprint
                Intent intent= new Intent(getApplicationContext(), FingerPrintActivity.class); //ask for fingerprint
                startActivity(intent);
            }
            if (distance[0] <150) { // If user location in less than the specified circle , ask for fingerprint
                Intent intent= new Intent(getApplicationContext(), UserNav.class); //ask for fingerprint
                startActivity(intent);
                Toast.makeText(this, "You are not in the university" , Toast.LENGTH_LONG).show();
            }
        }
    }


    /**Company's circle/ domain */
    public void circle()
    {
        double radiusInMeters = 150;
        int strokeColor = 0xffff0000;
        int shadeColor = 0x44ff0000;

        mCircle = mMap.addCircle (new CircleOptions()
                .center(new LatLng(24.472209, 39.568258)) //longitude & latitude
                .radius(radiusInMeters) //circle direction
                .fillColor(shadeColor) //light red
                .strokeColor(strokeColor) //circle border color
                .strokeWidth(1)); // circle weight
    }

    /**Permission to access user location*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        if (requestCode == 0) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);

            }

        }
    }

    /** Get user location and compare it to the company's domain*/
    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLon = location.getLongitude();

    }



}

