package me.shkschneider.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.network.WebService;
import me.shkschneider.skeleton.data.GsonParser;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.ui.DrawableHelper;

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

        skeletonActivity().loading(+1);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
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
        new WebService().getJsonObject(url, new WebService.Callback() {
            @Override
            public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                skeletonActivity().loading(-1);
                if (e != null) {
                    ActivityHelper.toast(e.getMessage());
                    return;
                }
                final JsonObject jsonObject = (JsonObject) result;
                final JsonArray entries = GsonParser.array(jsonObject, "entry");
                final JsonObject entry = entries.get(0).getAsJsonObject();
                final String thumbnailUrl = GsonParser.string(entry, "thumbnailUrl");
                new WebService().getBitmap(thumbnailUrl + "?size=360", new WebService.Callback() {
                    @Override
                    public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                        if (e != null) {
                            return;
                        }
                        final Bitmap bitmap = (Bitmap) result;
                        if (bitmap == null) {
                            LogHelper.warning("Bitmap was NULL");
                            return;
                        }
                        imageView.setImageDrawable(DrawableHelper.circular(bitmap));
                    }
                });
                final String displayName = GsonParser.string(entry, "displayName");
                textView1.setText(displayName);
                final String currentLocation = GsonParser.string(entry, "currentLocation");
                textView2.setText(currentLocation);
            }
        });
    }

}
