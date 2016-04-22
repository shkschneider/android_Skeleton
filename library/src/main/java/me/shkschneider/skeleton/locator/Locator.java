package me.shkschneider.skeleton.locator;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;

public class Locator implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static Locator INSTANCE;

    @RequiresPermission(anyOf={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION})
    public static Locator get() {
        if (INSTANCE == null) {
            INSTANCE = new Locator();
        }
        return INSTANCE;
    }

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationListener mLocationListener;
    private Location mLocation;

    private Locator() {
        mGoogleApiClient = new GoogleApiClient.Builder(ApplicationHelper.context())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationRequest = defaultLocationRequest();
        mLocation = null;

        final LocationManager locationManager = SystemServices.locationManager();
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LogHelper.info("Network provider unavailable");
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LogHelper.info("GPS provider unavailable");
        }
    }

    private LocationRequest defaultLocationRequest() {
        return LocationRequest.create()
                .setInterval(TimeUnit.MINUTES.toMillis(1))
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public boolean start(@Nullable final LocationRequest locationRequest, @Nullable final LocationListener locationListener) {
        int providers = 0;
        final LocationManager locationManager = SystemServices.locationManager();
        if (! locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LogHelper.info("Network provider unavailable");
        }
        else {
            providers++;
        }
        if (! locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LogHelper.info("GPS provider unavailable");
        }
        else {
            providers++;
        }
        if (providers == 0) {
            return false;
        }

        if (mGoogleApiClient.isConnected()) {
            LogHelper.info("GoogleApiClient was already connected");
            if (mLocation != null) {
                mLocationListener.onLocationChanged(mLocation);
            }
            return false;
        }
        if (mGoogleApiClient.isConnecting()) {
            LogHelper.info("GoogleApiClient was connecting");
            if (mLocation != null) {
                mLocationListener.onLocationChanged(mLocation);
            }
            return false;
        }

        mGoogleApiClient.connect();
        if (locationRequest == null) {
            LogHelper.verbose("LocationRequest was NULL");
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
            LogHelper.debug("GoogleApiClient was disconnected");
            return true;
        }

        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.unregisterConnectionFailedListener(this);
            mGoogleApiClient.disconnect();
            mLocationRequest = null;
            mLocationListener = null;
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    // GoogleApiClient.ConnectionCallbacks

    @RequiresPermission(anyOf={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION})
    @Override
    public void onConnected(final Bundle bundle) {
        if (! mGoogleApiClient.isConnected()) {
            LogHelper.warning("GoogleApiClient was disconnected");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
            @RequiresPermission(anyOf={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION})
            @Override
            public void onResult(@NonNull final Status status) {
                if (status.isSuccess()) {
                    LogHelper.debug("LocationServices: SUCCESS");
                    final LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
                    if (locationAvailability != null && locationAvailability.isLocationAvailable()) {
                        onLocationChanged(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
                    }
                }
                else if (status.isCanceled()) {
                    LogHelper.debug("LocationServices: CANCELED");
                }
                else if (status.isInterrupted()) {
                    LogHelper.debug("LocationServices: INTERRUPTED");
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(final int reason) {
        switch (reason) {
            case CAUSE_NETWORK_LOST:
                LogHelper.warning("GoogleApiClient: NETWORK_LOST");
                break ;
            case CAUSE_SERVICE_DISCONNECTED:
                LogHelper.warning("GoogleApiClient: SERVICE_DISCONNECTED");
                break ;
        }
    }

    // GoogleApiClient.OnConnectionFailedListener

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
        LogHelper.warning("GoogleApiClient:" + connectionResult.getErrorCode());
    }

    // LocationListener

    @Override
    public void onLocationChanged(final Location location) {
        if (location == null) {
            return;
        }
        if (LocatorHelper.betterLocation(location, mLocation, mLocationRequest.getInterval())) {
            mLocation = location;
            if (mLocationListener != null) {
                mLocationListener.onLocationChanged(mLocation);
            }
        }
    }

}
