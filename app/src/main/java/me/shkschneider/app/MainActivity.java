package me.shkschneider.app;

import android.os.Bundle;

import me.shkschneider.skeleton.SkeletonActivity;

public class MainActivity extends SkeletonActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home(false);
    }

}
