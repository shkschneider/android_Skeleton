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
package me.shkschneider.skeleton.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class VolleyHelper {

    protected RequestQueue mVolley;
    protected Object mTag;
    protected List<Request> mRequests;

    public VolleyHelper(final Context context, final Object tag) {
        mVolley = Volley.newRequestQueue(context);
        mTag = tag;
        mRequests = new ArrayList<Request>();
    }

    public VolleyHelper(final Context context) {
        mVolley = Volley.newRequestQueue(context);
        mTag = getClass().getCanonicalName();
        mRequests = new ArrayList<Request>();
    }

    public void getString(final String url, final Object tag, final VolleyCallback callback) {
        final Request request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(final String string) {
                LogHelper.d("Response: " + string);
                if (callback != null) {
                    callback.volleyCallback(tag, true, string);
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                LogHelper.e("VolleyError: " + error.getMessage());
                if (callback != null) {
                    callback.volleyCallback(tag, false, error.getMessage());
                }
            }

        });
        LogHelper.d("StringRequest: " + request.toString());
        request.setTag(tag);
        mVolley.add(request);
        mRequests.add(request);
    }

    public void getString(final String url, final VolleyCallback callback) {
        getString(url, mTag, callback);
    }

    public void getJson(final String url, final Object tag, final VolleyCallback callback) {
        final Request request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(final JSONObject jsonObject) {
                LogHelper.d("Response: " + jsonObject.toString());
                if (callback != null) {
                    callback.volleyCallback(tag, true, jsonObject);
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                LogHelper.e("VolleyError: " + error.getMessage());
                if (callback != null) {
                    callback.volleyCallback(tag, false, error.getMessage());
                }
            }

        });
        LogHelper.d("JsonObjectRequest: " + request.toString());
        request.setTag(tag);
        mVolley.add(request);
        mRequests.add(request);
    }

    public void getJson(final String url, final VolleyCallback callback) {
        getJson(url, mTag, callback);
    }

    public void getImage(final String url, final Object tag, final VolleyCallback callback) {
        final Request request = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(final Bitmap bitmap) {
                LogHelper.d("Response: " + bitmap.toString());
                if (callback != null) {
                    callback.volleyCallback(tag, true, bitmap);
                }
            }

        }, -1, -1, null, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                LogHelper.e("VolleyError: " + error.getMessage());
                if (callback != null) {
                    callback.volleyCallback(tag, false, error.getMessage());
                }
            }

        });
        LogHelper.d("ImageRequest: " + request.toString());
        request.setTag(tag);
        mVolley.add(request);
        mRequests.add(request);
    }

    public void getImage(final String url, final VolleyCallback callback) {
        getImage(url, mTag, callback);
    }

    public void cancel(final Object tag) {
        mVolley.cancelAll(tag);
    }

    public void cancel() {
        mVolley.cancelAll(mTag);
    }

    public interface VolleyCallback {

        public void volleyCallback(final Object tag, final Boolean success, final Object object);

    }

}
