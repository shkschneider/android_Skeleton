package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityHelper;

public class SnackBarFragment extends SkeletonFragment {

    public SnackBarFragment() {
        title("SnackBar");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final Button snackbar1 = (Button) view.findViewById(R.id.snackbar1);
        snackbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.snackBar(getActivity(), "Take some of that SnackBar!");
            }
        });

        final Button snackbar2 = (Button) view.findViewById(R.id.snackbar2);
        snackbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.snackBar(getActivity(), "Take some of that SnackBar!", getResources().getString(android.R.string.ok), new View.OnClickListener() {

                            @Override
                            public void onClick(final View v) {
                                Toast.makeText(getActivity(), "That's it!", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });

        final Button snackbar3 = (Button) view.findViewById(R.id.snackbar3);
        snackbar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.snackBar(getActivity(), "Take some of that way way way (way...) bigger SnackBar, for free!");
            }
        });

    }

}
