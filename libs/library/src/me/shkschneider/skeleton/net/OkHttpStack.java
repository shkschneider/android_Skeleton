/**
 * Copyright 2013 JakeWharton
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

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;

import java.net.HttpURLConnection;
import java.net.URL;

import me.shkschneider.skeleton.android.LogHelper;

public class OkHttpStack extends HurlStack {

    protected final OkHttpClient mClient;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(final OkHttpClient client) {
        if (client == null) {
            LogHelper.w("Client was NULL");
        }
        mClient = client;
    }

    @Override
    protected HttpURLConnection createConnection(final URL url) {
        return mClient.open(url);
    }

}
