package roshni.com.placepicker;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class MainActivity extends AppCompatActivity  implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {


    /**
     * The desired interval for location upates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS=UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_PLACE_PICKER = 1;

    //UI component declaration
    TextView address;
    TextView mLatitudeText;
    TextView mLongitudeText;
    TextView distanceBetweenView;

    //test vaiable ringer mode
    float radius= (float) 0.0;

    double startLatitude=22.6023552,startLongitude=88.4331205;
    double endLatitude=0.0, endLongitude=0.0;
    float[] result=new float[1];

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        buildGoogleApiClient();
        address = (TextView) findViewById(R.id.editText);
        mLatitudeText=(TextView)findViewById(R.id.mLatitudeText);
        mLongitudeText=(TextView)findViewById(R.id.mLongitudeText);
        distanceBetweenView=(TextView)findViewById(R.id.distTraveledView);
    }

    //to establish connection Google play service
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * initialze locationRequest object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     *onClick button
     */
    public void startUpdatesButtonHandler(View view) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }

    private void updateUI(){
        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
    }

    public void setStartLatLan(View view) {
        startLatitude=mLastLocation.getLatitude();
        startLongitude=mLastLocation.getLongitude();
    }

    public void setRadius(View view) {
        EditText radiusInput=(EditText)findViewById(R.id.takeRadiusText);
        radius=Float.valueOf(radiusInput.getText().toString());
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
       /* if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        } */
        mGoogleApiClient.connect();
    }
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.connect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            updateUI();
        }
    }


    /**
     * update UI on getting result on location updation
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateUI();
        Toast.makeText(this, "location updated", Toast.LENGTH_SHORT).show();
        endLatitude=mLastLocation.getLatitude();
        endLongitude=mLastLocation.getLongitude();
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, result);
        distanceBetweenView.setText(Float.toString(result[0]));
        if(result[0]<=radius) {
            AudioManager myAudioManager;
            myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            Toast.makeText(this, "Now in Vibrate Mode##", Toast.LENGTH_SHORT).show();
        }
        else {
            AudioManager myAudioManager;
            myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(this, "Normal mode Enable **", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "connection suspended...", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
    }

    /*
     ******place Picker Works here
     */
    public void pickPlace(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent,REQUEST_PLACE_PICKER);
        }
        catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng mplace=place.getLatLng();
                startLatitude=mplace.latitude;
                startLongitude=mplace.longitude;
                String toastMsg = String.format("Place: %s", place.getLatLng());
                address.setText(toastMsg);
            }
        }
    }

}
