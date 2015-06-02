package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class FloatingActionButtonFragment extends SkeletonFragment {

    public FloatingActionButtonFragment() {
        title("FloatingActionButton");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_floatingactionbutton, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                ActivityHelper.snackBar(view, "SnackBar");
            }

        });
        floatingActionButton.startAnimation(AnimationUtils.loadAnimation(ApplicationHelper.context(), R.anim.sk_scale_up));
    }

}
