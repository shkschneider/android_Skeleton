package me.shkschneider.skeleton.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import me.shkschneider.skeleton.SkeletonOverlayActivity;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityTransitionHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
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
        title("Overlay");

        final Bitmap bitmap = BitmapHelper.fromDrawable(getResources().getDrawable(R.drawable.newyork));
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        overlay(PaletteHelper.mutedColor(bitmap));

        ActivityTransitionHelper.tag(imageView, "NewYork");
    }

}
