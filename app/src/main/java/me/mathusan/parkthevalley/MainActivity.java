package me.mathusan.parkthevalley;

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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        LocationListener {


    /**
     * Class members
    */

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private static String CLASS_NAME = "MAIN ACTIVITY";

    String name, email;

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

        mGoogleAPIClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        createLocationRequest();

        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        email = b.getString("email");

        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone("911");

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
        writeNewPost(user);

    }

    private void writeNewPost(User user)  {

        Log.d(CLASS_NAME, "WritingPost...");

        user.setEmail("testEmail");
        user.setName("testName");

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase(FIREBASE_URL);


// Generate a new push ID for the new post
//        DatabaseReference newPostRef = database.child("user").push();

        database.child(user.getEmail()).setValue(user);







//        String newPostKey = newPostRef.getKey();
//// Create the data we want to update
//        Map newPost = new HashMap();
//
//        newPost.put("email", "New Post");
//        newPost.put("name", "Here is my new post!");
//        newPost.put("phone", "Here is my new post!");
//        newPost.put("price", 10.5);
//        newPost.put("lat", spot.getLng());
//        newPost.put("lng", spot.getLat());
//        newPost.put("open",true);
//
//        Map updatedUserData = new HashMap();
//        updatedUserData.put("User/Spots/Open" + newPostKey, true);
//        updatedUserData.put("User/E mail" + newPostKey, "testemail");
//// Do a deep-path update
//        ref.updateChildren(updatedUserData, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError != null) {
//                    System.out.println("Error updating data: " + firebaseError.getMessage());
//                }
//                else{Log.d(CLASS_NAME, "WriteNewPost Successful!");}
//            }
//        });

//        String key = database.child("User").push().getKey();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        HashMap<String, Object> postValues = new HashMap<>();
//        HashMap<String, Object> postValues2 = new HashMap<>();
//        postValues.put("Email" , email);
//        postValues.put("Name" , name);
//        postValues.put("Phone" , phone);
//        postValues.put("Price" , price);
//
//        postValues2.put("LatLng", spot.getLatlng());
//        postValues2.put("Open", spot.getOpen());
//
//        postValues.put("Spot", postValues2);
//
//        childUpdates.put("/User/" + key, postValues);

//        childUpdates.put("/User-Name/" + key, name);
//        childUpdates.put("/User-Email/" + key, email);
//        childUpdates.put("/User-Phone/" + key, phone);
//        childUpdates.put("/User-Price/" + key, price);
//        childUpdates.put("/User-Spots-LatLng/" + key, spot.getLatlng());
//        childUpdates.put("/User-Spots-Open/" + key, spot.getOpen());

//        ref.setValue(childUpdates);


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

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_searchspots) {

        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
