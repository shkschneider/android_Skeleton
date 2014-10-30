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
import me.shkschneider.skeleton.ImageManipulator;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.WebServiceIon;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.GsonParser;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class MainFragment extends SkeletonFragment {

    private static final String AUTHOR = "ShkSchneider";

    public MainFragment() {
        title("Main");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
        // imageview.setImageDrawable(IndeterminateDrawable.get());
        final TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        final TextView textView2 = (TextView) view.findViewById(android.R.id.text2);

        final String url = String.format("http://gravatar.com/%s.json", AUTHOR.toLowerCase());
        new WebServiceIon().getJsonObject(url, new WebServiceIon.Callback() {
            @Override
            public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                if (e != null) {
                    ActivityHelper.croutonRed(skeletonActivity(), e.getMessage());
                    return ;
                }
                final JsonObject jsonObject = (JsonObject) result;
                final JsonArray entries = GsonParser.array(jsonObject, "entry");
                final JsonObject entry = entries.get(0).getAsJsonObject();
                final String thumbnailUrl = GsonParser.string(entry, "thumbnailUrl");
                new WebServiceIon().getImage(thumbnailUrl + "?size=480", new WebServiceIon.Callback() {
                    @Override
                    public void webServiceCallback(final WebServiceIon.WebServiceException e, final Object result) {
                        if (e != null) {
                            return ;
                        }
                        final Bitmap bitmap = (Bitmap) result;
                        if (bitmap == null) {
                            LogHelper.warning("Bitmap was NULL");
                            return ;
                        }
                        imageview.setImageBitmap(ImageManipulator.circular(bitmap));
                    }
                });
                final String displayName = GsonParser.string(entry, "displayName");
                textView1.setText(displayName);
                final String currentLocation = GsonParser.string(entry, "currentLocation");
                textView2.setText(currentLocation);
            }
        });

        final Button website = (Button) view.findViewById(R.id.website);
        website.setText(getResources().getString(R.string.website));
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(IntentHelper.url(getResources().getString(R.string.website)));
            }
        });
    }

}
