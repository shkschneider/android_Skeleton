package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.data.JsonParser;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.ui.SvgView;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.network.Proxy;
import me.shkschneider.skeleton.ui.LetterIcon;
import me.shkschneider.skeleton.ui.MyScrollView;
import me.shkschneider.skeleton.ui.ViewHelper;

public class MainFragment extends SkeletonFragment {

    private static final String AUTHOR = "ShkSchneider";
    private static final String URL = "https://github.com/shkschneider/android_Skeleton";

    private SvgView mSvgView;
    private ImageView mImageView;
    private TextView mTextView1;
    private TextView mTextView2;

    public MainFragment() {
        title("Main");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        final LetterIcon letterIcon = (LetterIcon) view.findViewById(R.id.letterIcon);
        letterIcon.setLetter(AndroidHelper.codename().substring(0, 1));

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSvgView = (SvgView) view.findViewById(R.id.svgView);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        ((MyScrollView) view.findViewById(R.id.myScrollView)).parallax(mImageView);
        mTextView1 = (TextView) view.findViewById(android.R.id.text1);
        mTextView2 = (TextView) view.findViewById(android.R.id.text2);

        final Button github = (Button) view.findViewById(R.id.github);
        github.setText(URL.replaceFirst("https://github.com/", ""));
        github.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                startActivity(IntentHelper.web(URL));
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        skeletonActivity().loading(+1);
        final String url = String.format("http://gravatar.com/%s.json", AUTHOR.toLowerCase());
        Proxy.getInstance().addToRequestQueue(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                skeletonActivity().loading(-1);

                final JSONArray entries = JsonParser.get(response, "entry", null);
                if (entries == null) {
                    LogHelper.warning("No entries");
                    return ;
                }
                final JSONObject entry = JsonParser.get(entries, 0, null);
                final String thumbnailUrl = JsonParser.get(entry, "thumbnailUrl", null);
                Proxy.getInstance().getImageLoader().get(thumbnailUrl + "?size=" + (ScreenHelper.width() / 4), new ImageLoader.ImageListener() {

                    @Override
                    public void onResponse(final ImageLoader.ImageContainer imageContainer, final boolean b) {
                        mImageView.setImageBitmap(imageContainer.getBitmap());
                        mSvgView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onErrorResponse(final VolleyError volleyError) {
                        ActivityHelper.snackBar(ViewHelper.content(skeletonActivity()), volleyError.getMessage());
                    }

                });
                final String displayName = JsonParser.get(entry, "displayName", null);
                mTextView1.setText(displayName);
                final String currentLocation = JsonParser.get(entry, "currentLocation", null);
                mTextView2.setText(currentLocation);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                skeletonActivity().loading(-1);
                ActivityHelper.snackBar(ViewHelper.content(skeletonActivity()), error.getMessage());
            }
        }));
    }
}
