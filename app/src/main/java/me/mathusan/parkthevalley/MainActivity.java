package me.mathusan.parkthevalley;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        LocationListener
{


    enum STATE{
        ADDING,
        SEARCHING,
        NORMAL // nothing
}
    /**
     * Class members
    */

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private static String CLASS_NAME = "MAIN ACTIVITY";
    private STATE state;

    private String name, email;
    private HashMap<String, String> userListHashMap;

    /**
     * Connection members
     */
    private GoogleApiClient mGoogleAPIClient;
    private LocationRequest locationRequest;

    private DatabaseReference database;
    final public static String FIREBASE_URL = "https://fir-parkthevalley.firebaseio.com/";

    /**
     * User member
     */

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        state = STATE.NORMAL;
        userListHashMap = new HashMap<>();

        mGoogleAPIClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        createLocationRequest();

        Bundle b = getIntent().getExtras();
        if(b != null){
            name = b.getString("name");
            email = b.getString("email");

            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPhone("--- --- ----");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        
          mapFragment.getMapAsync(this);

        // Write a message to the database
         database = FirebaseDatabase.getInstance().getReference();

//        Spot spot = new Spot();
//        spot.setLat(43.843295);
//        spot.setLng(-79.27461);
//        spot.setOpen(true);
        //writeNewPost(user);

    }

    private void writeNewPost(final User user)  {

        Log.d(CLASS_NAME, "WritingPost...");

//        user.setEmail("testEmail");
//        user.setName("testName");

        Firebase.setAndroidContext(this);

        Firebase firebase = new Firebase(FIREBASE_URL);

        // remove existing databasereference
        {
            if(userListHashMap.containsKey(user.getEmail())){
                database.child(userListHashMap.get(user.getEmail()));
                database.getRef().removeValue();
                Log.d(CLASS_NAME, "removed value");
            }
        }

// Generate a new push ID for the new post
        final DatabaseReference newPostRef = database.push();
        newPostRef.setValue(user);
        userListHashMap.put(user.getEmail(), newPostRef.getKey());

        newPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) return;
                User user = dataSnapshot.getValue(User.class);

                Log.d(CLASS_NAME, "user email is " + user.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(CLASS_NAME, "Failed to read value");
            }
        });

//        database.child(user.getEmail()).setValue(user);
    }

    private void readPost(User user){
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maps) {

        } else if (id == R.id.nav_addlisting) {
            state = STATE.ADDING;

            startPlacePicker();
        } else if (id == R.id.nav_searchspots) {
            state = STATE.SEARCHING;
            startPlacePicker();

        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    int PLACE_PICKER_REQUEST = 1;
    private void startPlacePicker(){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                changeCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14));
                if(state == STATE.ADDING){
                    Spot spot = new Spot();
                    spot.setOpen(true);
                    spot.setLat(place.getLatLng().latitude);
                    spot.setLng(place.getLatLng().longitude);

                    List<Spot> tempCollection = user.getSpots() == null ? new ArrayList<Spot>() : user.getSpots();
                    Log.d(CLASS_NAME, "Spot is null? : " + (spot == null));
                    tempCollection.add(spot);
                    Log.d(CLASS_NAME, "tempCollection is null? : " + (tempCollection.size() == 0));
                    user.setSpots(tempCollection);

                    writeNewPost(user);
                }
            }
        }
    }

    private void changeCamera(CameraUpdate cameraUpdate){
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(80000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(CLASS_NAME, "onMapReady");
        mMap = googleMap;
        if(mMap != null){
            Log.d(CLASS_NAME, "map is not null");
        }
        askForPermission();

        mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);


        try {
            mMap.setMyLocationEnabled(true);
        }catch(SecurityException e){
            Log.e(CLASS_NAME, e.getMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(CLASS_NAME, "onConnected");
        startLocationUpdates();
    }

    private void connectGoogleAPI(){
        if(!mGoogleAPIClient.isConnected()){
            mGoogleAPIClient.connect();
        }
    }

    private void disconnectGoogleAPI(){
        mGoogleAPIClient.disconnect();
    }

    private void startLocationUpdates() {
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient, locationRequest, this);
        }catch(SecurityException e){
            Log.e(CLASS_NAME, e.getMessage());
        }
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleAPIClient, this);
        Log.d(CLASS_NAME, "stopLocationUpdates");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(CLASS_NAME, "onConnectionSuspended");
        stopLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(CLASS_NAME, "location Changed " + location.toString());
    }
/*
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }*/

    private void askForPermission(){
        // if (ContextCompat.checkSelfPermission(this, Manifest.permission.))
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)){

            //user has previously seen permission dialogue
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
