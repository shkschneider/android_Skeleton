package me.shkschneider.skeleton.network;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

// <https://github.com/DWorkS/VolleyPlus>
public class GZipRequest extends StringRequest {

    public GZipRequest(final int method, @NonNull final String url, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public GZipRequest(@NonNull final String url, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(@NonNull final NetworkResponse response) {
        String output = "";
        try {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
            final InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String read;
            while ((read = bufferedReader.readLine()) != null) {
                output += read;
            }
            inputStreamReader.close();
            bufferedReader.close();
            gzipInputStream.close();
        }
        catch (final Exception e) {
            return Response.error(new ParseError(e));
        }
        return Response.success(output, HttpHeaderParser.parseCacheHeaders(response));
    }

}
