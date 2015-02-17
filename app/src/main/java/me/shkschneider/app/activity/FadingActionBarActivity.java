package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFadingActionBarActivity;
import me.shkschneider.skeleton.helper.ActivityTransitionHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.ui.MyScrollView;

public class FadingActionBarActivity extends SkeletonFadingActionBarActivity {

    public static Intent getIntent(final Activity activity) {
        return new Intent(activity, FadingActionBarActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadingactionbar);
        home(true);

        final Bitmap bitmap = BitmapHelper.fromDrawable(getResources().getDrawable(R.drawable.newyork));
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        fadingActionBar(new ColorDrawable(BitmapHelper.mutedColor(bitmap)));
        fadingActionBar((MyScrollView) findViewById(R.id.myScrollView), imageView);

        ActivityTransitionHelper.tag(imageView, "NewYork");
    }

}
