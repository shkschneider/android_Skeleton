package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.ui.FloatingActionButton;
import me.shkschneider.skeleton.ui.FloatingActionMenu;

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
        // floatingActionButton.setColors()
        floatingActionButton.setIcon(R.drawable.ic_launcher);
        floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityHelper.toast("Button");
            }
        });
        floatingActionButton.startAnimation(AnimationUtils.loadAnimation(ApplicationHelper.context(), R.anim.sk_scale_up));

        final FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.floatingActionMenu);
        // floatingActionMenu.setButtonColor()
        // floatingActionMenu.setPlusColor()
        // floatingActionMenu.setExpandDirection()
        floatingActionMenu.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionMenu.OnFloatingActionMenuListener() {

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
        floatingActionMenu.startAnimation(AnimationUtils.loadAnimation(ApplicationHelper.context(), R.anim.sk_scale_up));

        final FloatingActionButton floatingActionButton1 = new FloatingActionButton(getActivity());
        floatingActionButton1.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton1.setIcon(R.drawable.ic_launcher);
        floatingActionButton1.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button1");
            }
        });
        floatingActionMenu.addButton(floatingActionButton1);

        final FloatingActionButton floatingActionButton2 = new FloatingActionButton(getActivity());
        floatingActionButton2.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton2.setIcon(R.drawable.ic_launcher);
        floatingActionButton2.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button2");
            }
        });
        floatingActionMenu.addButton(floatingActionButton2);

        final FloatingActionButton floatingActionButton3 = new FloatingActionButton(getActivity());
        floatingActionButton3.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton3.setIcon(R.drawable.ic_launcher);
        floatingActionButton3.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button3");
            }
        });
        floatingActionMenu.addButton(floatingActionButton3);

        final FloatingActionButton floatingActionButton4 = new FloatingActionButton(getActivity());
        floatingActionButton4.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton4.setIcon(R.drawable.ic_launcher);
        floatingActionButton4.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button4");
            }
        });
        floatingActionMenu.addButton(floatingActionButton4);

        final FloatingActionButton floatingActionButton5 = new FloatingActionButton(getActivity());
        floatingActionButton5.setColors(R.color.primaryColor, R.color.accentColor);
        floatingActionButton5.setIcon(R.drawable.ic_launcher);
        floatingActionButton5.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                floatingActionMenu.collapse();
                ActivityHelper.toast("Menu.Button5");
            }
        });
        floatingActionMenu.addButton(floatingActionButton5);

    }

}
