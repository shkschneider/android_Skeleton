package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.ui.FloatingActionButton;
import me.shkschneider.skeleton.ui.SnackBar;

public class SnackBarFragment extends SkeletonFragment {

    public SnackBarFragment() {
        title("SnackBar");
    }

    // Inflate

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snackbar, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton.setIcon(R.drawable.ic_launcher);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ActivityHelper.toast("FloatingActionButton");
            }
        });

        final Button toast = (Button) view.findViewById(R.id.toast);
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.toast("Toast");
            }
        });

        final Button snackbar1 = (Button) view.findViewById(R.id.snackbar1);
        snackbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SnackBar.with(skeletonActivity(), "Take some of that SnackBar!")
                        .duration(SnackBar.DURATION_SHORT)
                        .singleLine()
                        .attachToView(floatingActionButton)
                        .show();
            }
        });

        final Button snackbar2 = (Button) view.findViewById(R.id.snackbar2);
        snackbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SnackBar.with(skeletonActivity(), "Take some of that SnackBar too!")
                        .duration(SnackBar.DURATION_LONG)
                        .singleLine()
                        .action(getResources().getString(android.R.string.ok), null)
                        .attachToView(floatingActionButton)
                        .show();
            }
        });

        final Button snackbar3 = (Button) view.findViewById(R.id.snackbar3);
        snackbar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SnackBar.with(skeletonActivity(), "Take some of that way bigger SnackBar, for free!")
                        .duration(SnackBar.DURATION_INFINITE)
                        .multiLine()
                        .action(getResources().getString(android.R.string.ok), null)
                        .attachToView(floatingActionButton)
                        .show();
            }
        });

    }

}
