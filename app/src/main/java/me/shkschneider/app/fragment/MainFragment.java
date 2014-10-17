package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.SkeletonFragment;

public class MainFragment extends SkeletonFragment {

    public MainFragment() {
        title("Main");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ImageView authorAvatar = (ImageView) view.findViewById(R.id.authorAvatar);
        Ion.with(authorAvatar)
                .load("https://shkschneider.me/shkschneider.png");

        final TextView applicationName = (TextView) view.findViewById(R.id.applicationName);
        applicationName.setText(ApplicationHelper.name());

        final TextView authorName = (TextView) view.findViewById(R.id.authorName);
        authorName.setText("ShkSchneider");

        return view;
    }

}
