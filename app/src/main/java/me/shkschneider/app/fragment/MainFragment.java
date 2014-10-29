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

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.ImageManipulator;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ApplicationHelper;
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

        final ImageView authorAvatar = (ImageView) view.findViewById(R.id.authorAvatar);
        Ion.with(skeletonActivity())
                .load(String.format("https://%s.me/%s.png", AUTHOR.toLowerCase(), AUTHOR.toLowerCase()))
                .asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(final Exception e, final Bitmap result) {
                        if (e != null) {
                            LogHelper.wtf(e);
                            return;
                        }
                        if (result == null) {
                            LogHelper.warning("Bitmap was NULL");
                            return;
                        }

                        authorAvatar.setImageBitmap(ImageManipulator.circular(result));
                    }
                });

        final TextView applicationName = (TextView) view.findViewById(R.id.applicationName);
        applicationName.setText(ApplicationHelper.name());

        final TextView authorName = (TextView) view.findViewById(R.id.authorName);
        authorName.setText(AUTHOR);

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
