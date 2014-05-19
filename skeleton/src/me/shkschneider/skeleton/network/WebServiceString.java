package me.shkschneider.skeleton.network;

import com.koushikdutta.ion.Ion;
import me.shkschneider.skeleton.SkeletonApplication;

public class WebServiceString extends WebService<String> {

    public WebServiceString(final int id, final String url, final Callback callback) {
        super(id, url, callback);
        this.request = Ion.with(SkeletonApplication.CONTEXT, this.url).asString().withResponse();
    }

    public WebServiceString(final int id, final String url) {
        this(id, url, null);
    }

}
