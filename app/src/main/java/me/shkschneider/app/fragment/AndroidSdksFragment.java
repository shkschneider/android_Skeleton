package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.WebServiceIon;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

public class AndroidSdksFragment extends SkeletonFragment {

    private static final String URL = "https://dl-ssl.google.com/android/repository/repository-10.xml";

    private MySwipeRefreshLayout mMySwipeRefreshLayout;
    private ArrayAdapter<AndroidSdk> mAdapter;

    public AndroidSdksFragment() {
        title("Android SDK");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
        mAdapter = new ArrayAdapter<AndroidSdk>(skeletonActivity(), R.layout.listview_item2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.listview_item2, parent, false);
                }
                final AndroidSdk androidSdk = getItem(position);
                final String string1 = String.format("Android %s", androidSdk.version);
                final String string2 = String.format("API-%s r%s", androidSdk.apiLevel, androidSdk.revision);
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(string1);
                ((TextView) convertView.findViewById(android.R.id.text2)).setText(string2);
                return convertView;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }
        };
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myswiperefreshlayout, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMySwipeRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.myswiperefreshlayout);
        mMySwipeRefreshLayout.swipes(true);
        mMySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                ActivityHelper.toast(mAdapter.getItem(position).toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        mMySwipeRefreshLayout.setRefreshing(true);
        new WebServiceIon().getInputStream(URL, new WebServiceIon.Callback() {
                    @Override
                    public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                        mMySwipeRefreshLayout.setRefreshing(false);
                        if (e != null) {
                            ActivityHelper.croutonRed(skeletonActivity(), e.getMessage());
                            return ;
                        }
                        final InputStream inputStream = (InputStream) result;
                        parseXml(inputStream);
                    }
                });
    }

    private void parseXml(final InputStream inputStream) {
        AndroidSdk androidSdk = null;
        try {
            final ArrayList<AndroidSdk> androidSdks = new ArrayList<AndroidSdk>();
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
                        } else if (androidSdk != null) {
                            if (name.equals("sdk:version")) {
                                androidSdk.version = xmlPullParser.nextText();
                            } else if (name.equals("sdk:api-level")) {
                                androidSdk.apiLevel = xmlPullParser.nextText();
                            } else if (name.equals("sdk:revision")) {
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
            ActivityHelper.croutonRed(skeletonActivity(), ex.getMessage());
        }
    }

    private class AndroidSdk {

        public String version;
        public String apiLevel;
        public String revision;

        public boolean valid() {
            return (! StringHelper.nullOrEmpty(version)
                    && ! StringHelper.nullOrEmpty(apiLevel)
                    && ! StringHelper.nullOrEmpty(revision));
        }

        @Override
        public String toString() {
            return String.format("Android %s", version);
        }

    }

}
