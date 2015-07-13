package me.shkschneider.skeleton.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

// <https://github.com/DWorkS/VolleyPlus/blob/master/library/src/com/android/volley/request/GZipRequest.java>
public class GZipRequest extends StringRequest {

    public GZipRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public GZipRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
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
