package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.network.GsonObjectRequest;
import me.shkschneider.skeleton.network.Proxy;
import me.shkschneider.skeleton.data.GsonParser;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.network.NetworkHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class NetworkFragment extends SkeletonFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayAdapter<String> mAdapter;

    public NetworkFragment() {
        title("Network");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adapter

        final LayoutInflater layoutInflater = LayoutInflater.from(ApplicationHelper.context());
        mAdapter = new ArrayAdapter<String>(ApplicationHelper.context(), R.layout.sk_listview_item2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.sk_listview_item2, parent, false);
                }
                final String string = getItem(position);
                final String string1 = string.substring(0, string.indexOf(" "));
                final String string2 = string.substring(string.indexOf(" ") + 1, string.length());
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(string1);
                ((TextView) convertView.findViewById(android.R.id.text2)).setText(string2);
                return convertView;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(final int position) {
                return false;
            }
        };
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        skeletonActivity().swipeRefreshLayoutCompat(listView);
    }

    @Override
    public void onResume() {
        super.onResume();

        skeletonActivity().refreshable(true, this);

        if (NetworkHelper.connectedOrConnecting()) {
            onRefresh();
        }
        else {
            ActivityHelper.snackBar(ActivityHelper.contentView(skeletonActivity()), "Offline");
        }
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();

        final Response.Listener<JsonObject> listener = new Response.Listener<JsonObject>() {

            @Override
            public void onResponse(final JsonObject jsonObject) {
                skeletonActivity().loading(-1);
                for (final String key : GsonParser.keys(jsonObject)) {
                    final String value = GsonParser.string(jsonObject, key);
                    if (!StringHelper.nullOrEmpty(value)) {
                        final String string = String.format("%s %s", key, value);
                        mAdapter.add(string);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                skeletonActivity().loading(-1);
                ActivityHelper.toast(volleyError.getMessage());
            }

        };
        skeletonActivity().loading(+1);
        Proxy.getInstance().addToRequestQueue(new GsonObjectRequest("http://ip.jsontest.com", listener, errorListener));
        skeletonActivity().loading(+1);
        Proxy.getInstance().addToRequestQueue(new GsonObjectRequest("http://headers.jsontest.com", listener, errorListener));
        skeletonActivity().loading(+1);
        Proxy.getInstance().addToRequestQueue(new GsonObjectRequest("http://date.jsontest.com", listener, errorListener));
    }

}
