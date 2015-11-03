package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class ObjectHelper {

    protected ObjectHelper() {
        // Empty
    }

    public static String jsonify(@NonNull final Object object) {
        return new Gson().toJson(object, object.getClass());
    }

}
