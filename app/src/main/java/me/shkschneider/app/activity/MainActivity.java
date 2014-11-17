package me.shkschneider.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.helper.IntentHelper;

public class MainActivity extends SkeletonActivity {

    private static final String URL = "https://github.com/shkschneider/android-skeleton";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView1 = (TextView) findViewById(R.id.textview1);
        textView1.setText("Android Skeleton Showcase");

        final TextView textView2 = (TextView) findViewById(R.id.textview2);
        final String[] strings2 = new String[] {
                "Skeleton is an Android startup project that speeds up development (for API-14+).",
                "It provides a good Gradle setup and a large set of helper classes and other tools.",
        };
        for (final String string : strings2) {
            if (textView2.getText().length() > 0) {
                textView2.append("\n\n");
            }
            textView2.append(string);
        }

        final TextView textView3 = (TextView) findViewById(R.id.textview3);
        final String[] strings3 = new String[] {
                "SkeletonApplication",
                "SkeletonActivity",
                "SkeletonFragment",
                "SkeletonFragmentActivity",
                "SkeletonPreferenceFragment",
        };
        for (final String string : strings3) {
            if (textView3.getText().length() > 0) {
                textView3.append("\n");
            }
            textView3.append(string);
        }

        final Button github = (Button) findViewById(R.id.github);
        github.setText(URL.replaceFirst("https://github.com/", ""));
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(IntentHelper.url(URL));
            }
        });

        final Button go = (Button) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(DashboardActivity.getInstance(MainActivity.this));
            }
        });

        if (savedInstanceState == null) {
            new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(go))
                    .setContentTitle("Welcome")
                    .setContentText("Hit that button for me!")
                    .setStyle(R.style.AppTheme_ShowCaseView)
                    .hideOnTouchOutside()
                    .build();
        }
    }

}
