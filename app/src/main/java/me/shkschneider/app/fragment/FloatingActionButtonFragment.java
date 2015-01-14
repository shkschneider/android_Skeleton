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
import me.shkschneider.skeleton.ui.FloatingActionMenu;

public class FloatingActionButtonFragment extends SkeletonFragment {

    public FloatingActionButtonFragment() {
        title("FloatingActionButton");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skeletonActivity().refreshable(false);
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
        // floatingActionButton.setColors()
        floatingActionButton.setIcon(R.drawable.ic_launcher);
        floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.toast("Button");
            }
        });

        final FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.floatingActionMenu);
        // floatingActionMenu.setButtonColor()
        // floatingActionMenu.setPlusColor()
        // floatingActionMenu.setExpandDirection()
        floatingActionMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionMenu.OnFloatingActionsMenuUpdateListener() {
            private int count = 0;

            @Override
            public void onMenuExpanded() {
                count++;
                if (count == 5) {
                    ActivityHelper.toast("Easter Egg");
                }
            }

            @Override
            public void onMenuCollapsed() {
                // Ignore
            }
        });

        final FloatingActionButton floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton1);
        floatingActionButton1.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton1.setIcon(R.drawable.ic_launcher);
        // floatingActionButton.setSize()
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button1");
            }
        });

        final FloatingActionButton floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton2.setIcon(R.drawable.ic_launcher);
        // floatingActionButton.setSize()
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button2");
            }
        });

    }

}
