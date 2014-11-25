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
                        .singleLine()
                        .show();
            }
        });

        final Button snackbar2 = (Button) view.findViewById(R.id.snackbar2);
        snackbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SnackBar.with(skeletonActivity(), "Take some of that SnackBar!")
                        .singleLine()
                        .action(getResources().getString(android.R.string.ok), null)
                        .show();
            }
        });

        final Button snackbar3 = (Button) view.findViewById(R.id.snackbar3);
        snackbar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SnackBar.with(skeletonActivity(), "Take some of that way bigger SnackBar, for free!")
                        .multiLine()
                        .action(getResources().getString(android.R.string.ok), null)
                        .show();
            }
        });

    }

}
