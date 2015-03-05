package me.shkschneider.skeleton.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import me.shkschneider.skeleton.SkeletonOverlayActivity;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityTransitionHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.ui.MyScrollView;
import me.shkschneider.skeleton.ui.PaletteHelper;

public class OverlayActivity extends SkeletonOverlayActivity {

    public static Intent getIntent(final Activity activity) {
        return new Intent(activity, OverlayActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        home(true);
        subtitle("Overlay");

        final Bitmap bitmap = BitmapHelper.fromDrawable(getResources().getDrawable(R.drawable.newyork));
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        statusBarColor(getWindow(), PaletteHelper.mutedColor(bitmap));
        overlay(new ColorDrawable(PaletteHelper.mutedColor(bitmap)));
        overlay((MyScrollView) findViewById(R.id.myScrollView), imageView);

        ActivityTransitionHelper.tag(imageView, "NewYork");
    }

}
