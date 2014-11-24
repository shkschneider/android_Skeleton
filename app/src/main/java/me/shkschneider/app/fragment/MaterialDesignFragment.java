package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.ui.FloatingActionButton;
import me.shkschneider.skeleton.ui.SnackBar;

public class MaterialDesignFragment extends SkeletonFragment {

    public MaterialDesignFragment() {
        title("MaterialDesign");
    }

    // Inflate

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materialdesign, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new FloatingActionButton.Builder(skeletonActivity())
                .withDrawable(getResources().getDrawable(R.drawable.ic_launcher))
                .withOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        new SnackBar(skeletonActivity(), "Here some SnackBar for ya!")
                                .setButton(getResources().getString(android.R.string.ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View view) {
                                        ActivityHelper.toast("You're welcome!");
                                    }
                                })
                                .show();
                    }
                })
                .create()
                .show();

    }

}
