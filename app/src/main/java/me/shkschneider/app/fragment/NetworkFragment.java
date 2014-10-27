package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.GsonParser;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;

public class NetworkFragment extends SkeletonFragment {

    private ArrayAdapter<String> mAdapter;

    public NetworkFragment() {
        title("Network");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_item2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.listview_item2, parent, false);
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
        refresh();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listview, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
        return view;
    }

    public void refresh() {
        skeletonActivity().loading(true);
        Ion.with(this)
                .load("http://ifconfig.me/all.json")
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonObject> result) {
                        skeletonActivity().loading(false);
                        if (e != null) {
                            LogHelper.wtf(e);
                            ActivityHelper.croutonRed(skeletonActivity(), e.getLocalizedMessage());
                            return ;
                        }

                        final int responseCode = result.getHeaders().getResponseCode();
                        final String responseMessage = result.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            ActivityHelper.croutonRed(skeletonActivity(), String.format("%d: %s", responseCode, responseMessage));
                        }
                        else {
                            ActivityHelper.croutonGreen(skeletonActivity(), String.format("%d: %s", responseCode, responseMessage));
                        }
                        final JsonObject response = result.getResult();

                        for (final String key : GsonParser.keys(response)) {
                            final String value = GsonParser.string(response, key);
                            if (! StringHelper.nullOrEmpty(value)) {
                                final String string = String.format("%s %s", key, value);
                                mAdapter.add(string);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
