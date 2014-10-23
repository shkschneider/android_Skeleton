package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

import me.shkschneider.app.R;
import me.shkschneider.app.model.AndroidSdk;
import me.shkschneider.skeleton.NavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class AndroidSdksFragment extends SkeletonFragment {

    private static final String URL = "https://dl-ssl.google.com/android/repository/repository-10.xml";

    private ArrayAdapter<AndroidSdk> mAdapter;

    public AndroidSdksFragment() {
        title("Android SDK");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listview, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<AndroidSdk>(skeletonActivity(), R.layout.listview_item2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.listview_item2, parent, false);
                }
                final AndroidSdk androidSdk = getItem(position);
                final String string1 = String.format("Android %s", androidSdk.version);
                final String string2 = String.format("API-%s r%s", androidSdk.apiLevel, androidSdk.revision);
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(string1);
                ((TextView) convertView.findViewById(android.R.id.text2)).setText(string2);
                return convertView;
            }
        };
        listView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    public void refresh() {
        skeletonActivity().loading(true);
        Ion.with(this)
                .load(URL)
                .asInputStream()
                .withResponse()
                .setCallback(new FutureCallback<Response<InputStream>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<InputStream> result) {
                        skeletonActivity().loading(false);
                        if (e != null) {
                            ActivityHelper.croutonRed(skeletonActivity(), e.getLocalizedMessage());
                            return ;
                        }

                        final int responseCode = result.getHeaders().getResponseCode();
                        final String responseMessage = result.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            ActivityHelper.croutonOrange(skeletonActivity(), responseMessage);
                            return ;
                        }

                        final InputStream inputStream = result.getResult();
                        try {
                            final ArrayList<AndroidSdk> androidSdks = new ArrayList<AndroidSdk>();
                            AndroidSdk androidSdk = null;

                            final XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                            final XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                            xmlPullParser.setInput(inputStream, null);
                            int eventType = xmlPullParser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                final String name = xmlPullParser.getName();
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        if (name.equals("sdk:platform")) {
                                            androidSdk = new AndroidSdk();
                                        }
                                        else if (androidSdk != null) {
                                            if (name.equals("sdk:version")) {
                                                androidSdk.version = xmlPullParser.nextText();
                                            }
                                            else if (name.equals("sdk:api-level")) {
                                                androidSdk.apiLevel = xmlPullParser.nextText();
                                            }
                                            else if (name.equals("sdk:revision")) {
                                                androidSdk.revision = xmlPullParser.nextText();
                                            }
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (androidSdk != null && androidSdk.valid()) {
                                            androidSdks.add(androidSdk);
                                            androidSdk = null;
                                        }
                                        break;
                                }
                                eventType = xmlPullParser.next();
                            }
                            mAdapter.clear();
                            mAdapter.addAll(androidSdks);
                            mAdapter.sort(new Comparator<AndroidSdk>() {
                                @Override
                                public int compare(final AndroidSdk androidSdk1, final AndroidSdk androidSdk2) {
                                    int diff = Integer.valueOf(androidSdk2.apiLevel) - Integer.valueOf(androidSdk1.apiLevel);
                                    if (diff != 0) {
                                        return diff;
                                    }
                                    diff = Integer.valueOf(androidSdk2.revision) - Integer.valueOf(androidSdk1.revision);
                                    if (diff != 0) {
                                        return diff;
                                    }

                                    return 0;
                                }
                            });
                            mAdapter.notifyDataSetChanged();
                        }
                        catch (final Exception ex) {
                            LogHelper.wtf(ex);
                            ActivityHelper.croutonRed(skeletonActivity(), ex.getLocalizedMessage());
                        }
                    }
                });
    }

}
