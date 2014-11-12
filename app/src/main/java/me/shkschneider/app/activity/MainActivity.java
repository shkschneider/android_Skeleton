package me.shkschneider.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.ImageManipulator;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.WebService;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.GsonParser;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.ui.LoadingImageView;

public class MainActivity extends SkeletonActivity {

    private static final String AUTHOR = "ShkSchneider";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CardView cardView1 = (CardView) findViewById(R.id.cardview1);
        final LoadingImageView loadingImageView = (LoadingImageView) findViewById(R.id.loadingimageview);
        final TextView textView1 = (TextView) findViewById(R.id.textview1);
        final TextView textView2 = (TextView) findViewById(R.id.textview2);

        final CardView cardView2 = (CardView) findViewById(R.id.cardview2);
        final TextView textView3 = (TextView) findViewById(R.id.textview3);
        textView3.setText("This is a demo skeleton for any android application targeting API-14 +...");

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(DashboardActivity.getInstance(MainActivity.this));
            }
        });
        new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(button))
                .setContentTitle("Welcome")
                .setContentText("Hit that button for me!")
                .setStyle(R.style.AppTheme_ShowCaseView)
                .hideOnTouchOutside()
                .build();

        final String url = String.format("http://gravatar.com/%s.json", AUTHOR.toLowerCase());
        new WebService().getJsonObject(url, new WebService.Callback() {
            @Override
            public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                if (e != null) {
                    ActivityHelper.toast(e.getMessage());
                    return ;
                }
                final JsonObject jsonObject = (JsonObject) result;
                final JsonArray entries = GsonParser.array(jsonObject, "entry");
                final JsonObject entry = entries.get(0).getAsJsonObject();
                final String thumbnailUrl = GsonParser.string(entry, "thumbnailUrl");
                new WebService().getImage(thumbnailUrl + "?size=360", new WebService.Callback() {
                    @Override
                    public void webServiceCallback(final WebService.WebServiceException e, final Object result) {
                        if (e != null) {
                            return ;
                        }
                        final Bitmap bitmap = (Bitmap) result;
                        if (bitmap == null) {
                            LogHelper.warning("Bitmap was NULL");
                            return ;
                        }
                        loadingImageView.getImageView().setImageBitmap(ImageManipulator.circular(bitmap));
                        loadingImageView.showImageView();
                    }
                });
                final String displayName = GsonParser.string(entry, "displayName");
                textView1.setText(displayName);
                final String currentLocation = GsonParser.string(entry, "currentLocation");
                textView2.setText(currentLocation);
            }
        });
    }

}
