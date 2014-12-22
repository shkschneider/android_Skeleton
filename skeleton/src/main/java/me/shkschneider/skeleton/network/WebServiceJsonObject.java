package me.shkschneider.skeleton.network;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import me.shkschneider.skeleton.SkeletonApplication;

public class WebServiceJsonObject extends WebService<JsonObject> {

    public WebServiceJsonObject(final int id, final String url, final Callback callback) {
        super(id, url, callback);
        this.request = Ion.with(SkeletonApplication.CONTEXT).load(this.url).asJsonObject().withResponse();
    }

    public WebServiceJsonObject(final int id, final String url) {
        this(id, url, null);
    }

}
