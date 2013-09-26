/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

@SuppressWarnings("unused")
public class LocationHelper implements LocationListener {

    protected LocationManager mLocationManager;
    protected LocationCallback mLocationCallback;
    protected android.location.Location mLocation;

    public LocationHelper(final Context context, final LocationCallback locationCallback) {
        if (context != null) {
            mLocationManager = (LocationManager) SystemHelper.systemService(context, SystemHelper.SYSTEM_SERVICE_LOCATION_SERVICE);
            if (locationCallback != null) {
                mLocationCallback = locationCallback;
            }
            else {
                LogHelper.d("LocationCallback was NULL");
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
    }

    public LocationHelper(final Context context) {
        this(context, null);
    }

    public android.location.Location start(final Boolean gps) {
        if (mLocationManager != null) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (gps) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            return mLocation;
        }
        else {
            LogHelper.w("LocationManager was NULL");
        }
        return null;
    }

    public void stop() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
        else {
            LogHelper.w("LocationManager was NULL");
        }
    }

    public android.location.Location location() {
        return mLocation;
    }

    @Override
    public void onLocationChanged(final android.location.Location location) {
        mLocation = location;

        if (mLocationCallback != null) {
            mLocationCallback.locationCallback(mLocation);
        }
    }

    @Override
    public void onStatusChanged(final String s, final int i, final Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(final String provider) {
        if (mLocationCallback != null) {
            mLocationCallback.providerCallback(provider, true);
        }
    }

    @Override
    public void onProviderDisabled(final String provider) {
        if (mLocationCallback != null) {
            mLocationCallback.providerCallback(provider, false);
        }
    }

    public static Float metersFromDegrees(final Float degree) {
        if (degree > 0) {
            return (degree * 111111);
        }
        return 0F;
    }

    public static Float degreesFromMeters(final Float meters) {
        if (meters > 0) {
            return (meters / 111111);
        }
        return 0F;
    }

    public static Boolean betterLocation(final android.location.Location location, final android.location.Location currentLocation, final Long refreshRate) {
        if (currentLocation == null) {
            return true;
        }
        else if (location == null) {
            return false;
        }

        final Long timeDelta = location.getTime() - currentLocation.getTime();
        final Boolean isSignificantlyNewer = timeDelta > refreshRate;
        final Boolean isSignificantlyOlder = timeDelta < -refreshRate;
        final Boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        }
        else if (isSignificantlyOlder) {
            return false;
        }

        final Integer accuracyDelta = (int) (location.getAccuracy() - currentLocation.getAccuracy());
        final Boolean isLessAccurate = accuracyDelta > 0;
        final Boolean isMoreAccurate = accuracyDelta < 0;
        final Boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        final Boolean isFromSameProvider = ((location.getProvider() == null) ?
                (currentLocation.getProvider() == null) : location.getProvider().equals(currentLocation.getProvider()));

        if (isMoreAccurate) {
            return true;
        }
        else if (isNewer && ! isLessAccurate) {
            return true;
        }
        else if (isNewer && ! isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    public static interface LocationCallback {

        public void providerCallback(final String provider, final Boolean enabled);
        public void locationCallback(final android.location.Location location);

    }

}
