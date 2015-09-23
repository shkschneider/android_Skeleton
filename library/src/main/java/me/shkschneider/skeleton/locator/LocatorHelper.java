package me.shkschneider.skeleton.locator;

import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.TimeUnit;

public class LocatorHelper {

    public static float metersFromDegrees(@FloatRange(from=0.0) final float degree) {
        return (degree * 111111);
    }

    public static float degreesFromMeters(@FloatRange(from=0.0) final float meters) {
        return (meters / 111111);
    }

    public static float distanceBetween(final double startLatitude, final double startLongitude, final double endLatitude, final double endLongitude) {
        float[] results = new float[1];
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
        final float distance = results[0];
        return distance;
    }

    public static Boolean betterLocation(@Nullable final Location location, @Nullable final Location currentLocation, final long refreshRate) {
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
        final Boolean isFromSameProvider = ((location.getProvider() == null) ? (currentLocation.getProvider() == null) : location.getProvider().equals(currentLocation.getProvider()));

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

    public static Boolean betterLocation(@Nullable final Location location, @Nullable final Location currentLocation) {
        return betterLocation(location, currentLocation, TimeUnit.MINUTES.toMillis(1));
    }

    public static LatLng latLng(@NonNull final Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static Location location(@NonNull final LatLng latLng) {
        final Location location = new Location(LocationManager.PASSIVE_PROVIDER);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

}
