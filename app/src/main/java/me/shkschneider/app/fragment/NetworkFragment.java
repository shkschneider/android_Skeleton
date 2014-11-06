package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.WebService;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.GsonParser;
import me.shkschneider.skeleton.helper.StringHelper;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

public class NetworkFragment extends SkeletonFragment {

    private MySwipeRefreshLayout mMySwipeRefreshLayout;
    private ArrayAdapter<String> mAdapter;

    public NetworkFragment() {
        title("Network");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_iconitem2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.listview_iconitem2, parent, false);
                }
                ((ImageView) convertView.findViewById(R.id.imageview)).setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
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
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void updateLoading() {
        if (! skeletonActivity().loading()) {
            mMySwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void refresh() {
        mAdapter.clear();
        skeletonActivity().loading(+1);
        new WebService().getJsonObject("http://ip.jsontest.com", new WebService.Callback() {
            @Override
            public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                skeletonActivity().loading(-1);
                updateLoading();
                if (e != null) {
                    ActivityHelper.toast(e.getMessage());
                    return ;
                }
                final JsonObject jsonObject = (JsonObject) result;
                final String ip = GsonParser.string(jsonObject, "ip");
                mAdapter.add("ip " + ip);
                mAdapter.notifyDataSetChanged();
            }
        });
        skeletonActivity().loading(+1);
        new WebService().getJsonObject("http://headers.jsontest.com", new WebService.Callback() {
            @Override
            public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                skeletonActivity().loading(-1);
                updateLoading();
                if (e != null) {
                    ActivityHelper.toast(e.getMessage());
                    return ;
                }
                final JsonObject jsonObject = (JsonObject) result;
                for (final String key : GsonParser.keys(jsonObject)) {
                    final String value = GsonParser.string(jsonObject, key);
                    if (!StringHelper.nullOrEmpty(value)) {
                        final String string = String.format("%s %s", key, value);
                        mAdapter.add(string);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        skeletonActivity().loading(+1);
        new WebService().getJsonObject("http://date.jsontest.com", new WebService.Callback() {
            @Override
            public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                skeletonActivity().loading(-1);
                updateLoading();
                if (e != null) {
                    ActivityHelper.toast(e.getMessage());
                    return ;
                }
                final JsonObject jsonObject = (JsonObject) result;
                for (final String key : GsonParser.keys(jsonObject)) {
                    final String value = GsonParser.string(jsonObject, key);
                    if (!StringHelper.nullOrEmpty(value)) {
                        final String string = String.format("%s %s", key, value);
                        mAdapter.add(string);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
