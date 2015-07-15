package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.data.GsonParser;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.network.GsonObjectRequest;
import me.shkschneider.skeleton.network.Proxy;
import me.shkschneider.skeleton.ui.MyScrollView;

public class MainFragment extends SkeletonFragment {

    private static final String AUTHOR = "ShkSchneider";
    private static final String URL = "https://github.com/shkschneider/android_Skeleton";

    public MainFragment() {
        title("Main");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSkeletonActivity().loading(+1);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ((MyScrollView) view.findViewById(R.id.myScrollView)).parallax(imageView);
        final TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        final TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        final Button github = (Button) view.findViewById(R.id.github);
        github.setText(URL.replaceFirst("https://github.com/", ""));
        github.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                startActivity(IntentHelper.web(URL));
            }

        });

        final String url = String.format("http://gravatar.com/%s.json", AUTHOR.toLowerCase());
        Proxy.getInstance().addToRequestQueue(new GsonObjectRequest(url, new Response.Listener<JsonObject>() {

            @Override
            public void onResponse(final JsonObject jsonObject) {
                getSkeletonActivity().loading(-1);
                final JsonArray entries = GsonParser.array(jsonObject, "entry");
                if (entries == null) {
                    LogHelper.w("No entries");
                    return;
                }
                final JsonObject entry = entries.get(0).getAsJsonObject();
                final String thumbnailUrl = GsonParser.string(entry, "thumbnailUrl");
                Proxy.getInstance().getImageLoader().get(thumbnailUrl + "?size=" + (ScreenHelper.width() / 4), new ImageLoader.ImageListener() {

                    @Override
                    public void onResponse(final ImageLoader.ImageContainer imageContainer, final boolean b) {
                        imageView.setImageBitmap(imageContainer.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(final VolleyError volleyError) {
                        ActivityHelper.snackBar(ActivityHelper.contentView(getSkeletonActivity()), volleyError.getMessage());
                    }

                });
                final String displayName = GsonParser.string(entry, "displayName");
                textView1.setText(displayName);
                final String currentLocation = GsonParser.string(entry, "currentLocation");
                textView2.setText(currentLocation);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                ActivityHelper.snackBar(ActivityHelper.contentView(getSkeletonActivity()), volleyError.getMessage());
            }

        }));
    }

}
