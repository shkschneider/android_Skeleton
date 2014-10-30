package me.shkschneider.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.ImageManipulator;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.WebServiceIon;
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
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_iconitem2) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.listview_iconitem2, parent, false);
                }
                final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
                new WebServiceIon().getImage("https://developer.android.com/images/resource-card-android-studio.png", new WebServiceIon.Callback() {
                    @Override
                    public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                        if (e != null) {
                            return;
                        }

                        final Bitmap bitmap = (Bitmap) result;
                        imageView.setImageBitmap(ImageManipulator.circular(bitmap));
                    }
                });
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
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        skeletonActivity().loading(+1);
        final long start1 = System.currentTimeMillis();
        new WebServiceIon().getString("http://ipecho.net/plain", new WebServiceIon.Callback() {
            @Override
            public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                LogHelper.info("time:" + (System.currentTimeMillis() - start1));
                skeletonActivity().loading(-1);
                if (e != null) {
                    ActivityHelper.croutonRed(skeletonActivity(), e.getMessage());
                    return;
                }

                final String string = (String) result;
                mAdapter.add("ip " + string);
                mAdapter.notifyDataSetChanged();
            }
        });
        skeletonActivity().loading(+1);
        final long start2 = System.currentTimeMillis();
        new WebServiceIon().getJsonObject("http://ifconfig.me/all.json", new WebServiceIon.Callback() {
            @Override
            public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                skeletonActivity().loading(-1);
                LogHelper.info("time:" + (System.currentTimeMillis() - start2));
                if (e != null) {
                    ActivityHelper.croutonRed(skeletonActivity(), e.getMessage());
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
