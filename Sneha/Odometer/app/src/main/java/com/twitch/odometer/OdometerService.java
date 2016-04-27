package com.twitch.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private static double distanceInMeters;
    private static Location newLocation = null;
    Location home = new Location("home");


    public class OdometerBinder extends Binder
    {
        OdometerService getOdometer()
        {
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onCreate()
    {
        LocationListener listener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                home.setLatitude(22.575606948101417);
                home.setLongitude(88.42753773178994);
                if(newLocation==null)
                {
                    newLocation=location;
                }
                distanceInMeters=location.distanceTo(home);
                newLocation=location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);


    }


    public double getMeters()
    {
        return this.distanceInMeters;
    }




}
