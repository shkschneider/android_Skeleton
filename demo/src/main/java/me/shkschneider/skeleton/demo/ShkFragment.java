package me.shkschneider.skeleton.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.SpannableStringHelper;
import me.shkschneider.skeleton.network.Proxy;
import me.shkschneider.skeleton.ui.BitmapHelper;

public class ShkFragment extends SkeletonFragment {

    private static final String URL = "https://github.com/shkschneider/android_Skeleton";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return super.onCreateView(inflater, R.layout.fragment_shk, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView avatar = (ImageView) view.findViewById(R.id.id_avatar);
        avatar.setImageBitmap(BitmapHelper.circular(BitmapHelper.fromResource(R.drawable.avatar)));
        Proxy.getInstance().getImageLoader().get("https://avatars3.githubusercontent.com/u/298903?v=3&s=460", new ImageLoader.ImageListener() {
            @Override
            public void onResponse(final ImageLoader.ImageContainer response, final boolean isImmediate) {
                if (response.getBitmap() != null) {
                    avatar.setImageBitmap(BitmapHelper.circular(response.getBitmap()));
                }
            }

            @Override
            public void onErrorResponse(final VolleyError error) {
                ActivityHelper.toast(error.getMessage());
            }
        });

        fill(view.findViewById(R.id.id_id), "#", new SpannableString("42"));
        fill(view.findViewById(R.id.id_name), "Name", new SpannableString("Alan SCHNEIDER\n" +
                "a.k.a. ShkSchneider"));
        fill(view.findViewById(R.id.id_age), "Age", new SpannableString("Born in 1989"));
        fill(view.findViewById(R.id.id_nationality), "Nationality", new SpannableString("French"));
        fill(view.findViewById(R.id.id_city), "City", new SpannableString("Paris, FRANCE"));
        fill(view.findViewById(R.id.id_education), "Education", new SpannableStringHelper("Master (BAC+5)\n" +
                "Software Architect\n" +
                "[Epitech, ETNA]")
                .italize(16, 17).apply());
        fill(view.findViewById(R.id.id_position), "Position", new SpannableString("Android developer\n" +
                "Open-Source believer"));
        fill(view.findViewById(R.id.id_personal), "Personal", new SpannableString("RaspberryPi, Android, Writing, Reading, Economics, History..."));

        fill(view.findViewById(R.id.code_projects), "Projects", new SpannableString("Android 'ShkMod' ROM\n" +
                "RuntimePermissionsCompat\n" +
                "PreferenceFragmentCompat\n" +
                "Open Location Codes\n" +
                "Skeleton for Android\n" +
                "Git scripts\n" +
                "Linux scripts\n" +
                "C/UNIX"));
        fill(view.findViewById(R.id.code_languages), "Languages", new SpannableString("Java, C, Python, Bash, PHP, Objective-C, JavaScript, HTML & CSS, MySQL, RegExp..."));
        fill(view.findViewById(R.id.code_technologies), "Technologies", new SpannableString("GNU/Linux, Android, NodeJS, MongoDB, Firebase..."));
        fill(view.findViewById(R.id.code_likes), "Likes", new SpannableString("Android, Debian, Fedora, Gnome-Shell, Emacs, NginX, uWSGI, Terminator, Makefiles, Bash scripts, HTML5..."));
        fill(view.findViewById(R.id.code_dislikes), "Dislikes", new SpannableString("Apple, Microsoft, Oracle, Facebook (also Google in a way)..."));

        fill(view.findViewById(R.id.personal_music), "Music", new SpannableString("Ambient, Progressive, Chill-Out, Elektro-Dark, Trance, Drum'n'Bass..."));
        fill(view.findViewById(R.id.personal_movies), "Movies", new SpannableString("Old French movies + The Matrix, The Fifth Element, Old Boy, V For Vendetta, Fight Club, Seven Pounds, The Fountain, Sucker Punch..."));
        fill(view.findViewById(R.id.personal_games), "Games", new SpannableString("Half-Life, StarCraft, Minecraft, Portal, The Witness..."));
        fill(view.findViewById(R.id.personal_books), "Books", new SpannableString("Peter F. Hamilton, Mikio Kaku, Le Comte de Monte-Cristo..."));
        fill(view.findViewById(R.id.personal_other), "Other", new SpannableString("Hiking & camping, Bicycle & Rollers, Biology, Cosmology, Economics..."));

        fill(view.findViewById(R.id.skills_systems), "Systems", new SpannableStringHelper("GNU/Linux: Debian (and Ubuntu, LinuxMint), Fedora\n" +
                "Windows: XP, Seven\n" +
                "MacOS: MacOS X")
                .boldify(0, 9).boldify(49, 8).boldify(69, 5).apply());
        fill(view.findViewById(R.id.skills_coding), "Coding", new SpannableStringHelper("Object-oriented: Java, Python, PHP, Objective-C\n" +
                "Compiled: C (with Makefiles)\n" +
                "Shell: Bash (commands & shell scripting)\n" +
                "Web-oriented: JavaScript (NodeJS)\n" +
                "Databases: MySQL, MongoDB")
                .boldify(0, 15).boldify(48, 8).boldify(77, 5).boldify(118, 12).boldify(152, 9).apply());
        fill(view.findViewById(R.id.skills_softwares), "Softwares", new SpannableStringHelper("Daemons: NginX, SSHd, uWSGI, Apache2\n" +
                "Versioning: Git, Mercurial, Subversion\n" +
                "IDEs: Emacs, IntelliJ IDEA, Eclipse, Netbeans")
                .boldify(0, 7).boldify(38, 10).boldify(76, 4).apply());
        fill(view.findViewById(R.id.skills_languages), "Languages", new SpannableString("French (native)\n" +
                "English (fluent, TOIEC 800+)"));

        final Button github = (Button) view.findViewById(R.id.github);
        github.setText(URL.replaceFirst("https://github.com/", ""));
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(IntentHelper.web(URL));
            }
        });
    }

    private void fill(@NonNull final View view, final String string, final Spannable spannable) {
        ((TextView) view.findViewById(R.id.textView1)).setText(string);
        ((TextView) view.findViewById(R.id.textView2)).setText(spannable);
    }

}
