package me.shkschneider.skeleton.locator;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;

public class Locator implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static Locator INSTANCE;

    public static Locator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Locator();
        }
        return INSTANCE;
    }

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationListener mLocationListener;
    private Location mLocation;

    public Locator() {
        mGoogleApiClient = new GoogleApiClient.Builder(ApplicationHelper.context())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationRequest = defaultLocationRequest();
        mLocation = null;

        final LocationManager locationManager = SystemServices.locationManager();
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LogHelper.i("Network provider unavailable");
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LogHelper.i("GPS provider unavailable");
        }
    }

    private LocationRequest defaultLocationRequest() {
        return LocationRequest.create()
                .setInterval(TimeUnit.MINUTES.toMillis(1))
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public boolean start(final LocationRequest locationRequest, final LocationListener locationListener) {
        if (mGoogleApiClient.isConnected()) {
            LogHelper.i("GoogleApiClient was already connected");
            return false;
        }
        if (mGoogleApiClient.isConnecting()) {
            LogHelper.i("GoogleApiClient was connecting");
            return false;
        }

        mGoogleApiClient.connect();
        if (locationRequest == null) {
            LogHelper.v("LocationRequest was NULL");
            mLocationRequest = defaultLocationRequest();
        }
        else {
            mLocationRequest = locationRequest;
        }
        mLocationListener = locationListener;
        return true;
    }

    public Location location() {
        return mLocation;
    }

    public boolean stop() {
        if (! mGoogleApiClient.isConnected()) {
            LogHelper.d("GoogleApiClient was disconnected");
            return true;
        }

        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.unregisterConnectionFailedListener(this);
            mGoogleApiClient.disconnect();
            mLocationRequest = defaultLocationRequest();
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return false;
        }
    }

    // GoogleApiClient.ConnectionCallbacks

    @Override
    public void onConnected(final Bundle bundle) {
        if (! mGoogleApiClient.isConnected()) {
            LogHelper.w("GoogleApiClient was disconnected");
            return ;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(final Status status) {
                if (status.isSuccess()) {
                    LogHelper.d("LocationServices: SUCCESS");
                    if (LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient).isLocationAvailable()) {
                        onLocationChanged(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
                    }
                }
                else if (status.isCanceled()) {
                    LogHelper.d("LocationServices: CANCELED");
                }
                else if (status.isInterrupted()) {
                    LogHelper.d("LocationServices: INTERRUPTED");
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(final int reason) {
        switch (reason) {
            case CAUSE_NETWORK_LOST:
                LogHelper.w("GoogleApiClient: NETWORK_LOST");
                break ;
            case CAUSE_SERVICE_DISCONNECTED:
                LogHelper.w("GoogleApiClient: SERVICE_DISCONNECTED");
                break ;
        }
    }

    // GoogleApiClient.OnConnectionFailedListener

    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
        LogHelper.w("GoogleApiClient:" + connectionResult.getErrorCode());
    }

    // LocationListener

    @Override
    public void onLocationChanged(final Location location) {
        if (LocatorHelper.betterLocation(location, mLocation, mLocationRequest.getInterval())) {
            mLocation = location;
            if (mLocationListener != null) {
                mLocationListener.onLocationChanged(mLocation);
            }
        }
    }

}
