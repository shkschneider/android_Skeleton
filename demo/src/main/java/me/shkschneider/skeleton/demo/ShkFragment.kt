package me.shkschneider.skeleton.demo

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewSwitcher

import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader

import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.SpannableStringHelper
import me.shkschneider.skeleton.java.StringHelper
import me.shkschneider.skeleton.network.Proxy
import me.shkschneider.skeleton.ui.BitmapHelper
import me.shkschneider.skeleton.ui.ImageHelper
import me.shkschneider.skeleton.ui.Toaster

class ShkFragment : SkeletonFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, R.layout.fragment_shk, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Personal
        Proxy.imageLoader.get(AVATAR, object: ImageLoader.ImageListener {
            override fun onResponse(response: ImageLoader.ImageContainer, isImmediate: Boolean) {
                response.bitmap?.let { bitmap ->
                    val avatar = ImageHelper.circular(bitmap)
                    (view.findViewById<View>(R.id.id_avatar) as ImageView).setImageDrawable(avatar)
                    (view.findViewById<View>(R.id.viewSwitcher) as ViewSwitcher).showNext()
                }
            }
            override fun onErrorResponse(error: VolleyError) {
                Toaster.show(error.message.orEmpty())
            }
        })
        fill(view.findViewById(R.id.id_id), "#", SpannableString("42"))
        fill(view.findViewById(R.id.id_name), "Name", SpannableString("Alan SCHNEIDER\n" + "a.k.a. ShkSchneider"))
        fill(view.findViewById(R.id.id_age), "Age", SpannableString("Born in 1989"))
        fill(view.findViewById(R.id.id_nationality), "Nationality", SpannableString("French"))
        fill(view.findViewById(R.id.id_city), "City", SpannableString("Paris, FRANCE"))
        fill(view.findViewById(R.id.id_education), "Education", SpannableStringHelper("Master (BAC+5)\n" +
                "Software Architect\n" +
                "[Epitech, ETNA]")
                .italize(16, 17).apply())
        fill(view.findViewById(R.id.id_position), "Position", SpannableString("Android developer\n" + "Open-Source believer"))
        fill(view.findViewById(R.id.id_personal), "Personal", SpannableString("Blockchains, RaspberryPi, Android, Writing, Reading, Economics, History..."))
        // Professional
        fill(view.findViewById(R.id.code_projects), "Projects", SpannableString("BitNode\n" +
                "Android 'ShkMod' ROM\n" +
                "RuntimePermissionsCompat\n" +
                "PreferenceFragmentCompat\n" +
                "Open Location Codes\n" +
                "Skeleton for Android\n" +
                "Git scripts\n" +
                "Linux scripts\n" +
                "C/UNIX"))
        fill(view.findViewById(R.id.code_languages), "Languages", SpannableString("Kotlin, Java, C, Python, Bash, JavaScript, SQL, RegExp..."))
        fill(view.findViewById(R.id.code_technologies), "Technologies", SpannableString("UNIX, Android, Blockchains, NodeJS, MongoDB, Firebase..."))
        fill(view.findViewById(R.id.code_likes), "Likes", SpannableString("Android, Linux, Gnome-Shell, Emacs, NginX, uWSGI, Terminator, Makefiles, Bash scripts, HTML5..."))
        fill(view.findViewById(R.id.code_dislikes), "Dislikes", SpannableString("Apple, Microsoft, Oracle, Facebook (also Google in a way)..."))
        // Personal
        fill(view.findViewById(R.id.personal_music), "Music", SpannableString("Ambient, Progressive, Chill-Out, Elektro-Dark, Trance, Drum'n'Bass..."))
        fill(view.findViewById(R.id.personal_movies), "Movies", SpannableString("Old French movies, The Matrix, The Fifth Element, Old Boy, V For Vendetta, Fight Club, The Fountain, Sucker Punch, Ex Machina..."))
        fill(view.findViewById(R.id.personal_games), "Games", SpannableString("Half-Life, StarCraft, Minecraft, Portal, The Witness, World of Warships..."))
        fill(view.findViewById(R.id.personal_books), "Books", SpannableString("Peter F. Hamilton, Arthur C. Clarke, Alexandre Dumas (Le Comte de Monte-Cristo...)"))
        fill(view.findViewById(R.id.personal_other), "Other", SpannableString("Economics, Biology, Cosmology, Hiking & camping, Bicycle & Roller..."))
        // Professional
        fill(view.findViewById(R.id.skills_systems), "Systems", SpannableStringHelper("GNU/Linux: Debian, Arch, Fedora\n" +
                "Windows: XP, Seven\n" +
                "MacOS: MacOS X")
                .boldify(0, 9).boldify(49, 8).boldify(69, 5).apply())
        fill(view.findViewById(R.id.skills_coding), "Coding", SpannableStringHelper("Object-oriented: Kotlin, Java, Python\n" +
                "Compiled: C (with Makefiles)\n" +
                "Shell: Bash (commands & shell scripting)\n" +
                "Web-oriented: JavaScript (NodeJS)\n" +
                "Databases: SQLite, MySQL, MongoDB")
                .boldify(0, 15).boldify(48, 8).boldify(77, 5).boldify(118, 12).boldify(152, 9).apply())
        fill(view.findViewById(R.id.skills_softwares), "Softwares", SpannableStringHelper("Daemons: NginX, SSHd, uWSGI, Apache2\n" +
                "Versioning: Git, Mercurial, Subversion\n" +
                "IDEs: IntellijIDEA (Android Studio), Emacs, Atom")
                .boldify(0, 7).boldify(37, 10).boldify(76, 4).apply())
        fill(view.findViewById(R.id.skills_languages), "Languages", SpannableString("French (native)\n" + "English (TOIEC 850+, CEFR B2)"))
        // Footer
        val github = view.findViewById<Button>(R.id.github)
        github.text = GITHUB.replaceFirst("https://github.com/".toRegex(), "")
        github.setOnClickListener { startActivity(IntentHelper.web(GITHUB)) }
    }

    private fun fill(view: View, string: String, spannable: Spannable) {
        view.findViewById<TextView>(R.id.textView1).text = string
        view.findViewById<TextView>(R.id.textView2).text = spannable
    }

    companion object {

        private const val AVATAR = "https://raw.githubusercontent.com/shkschneider/shkschneider.github.io/master/shkschneider.png"
        private const val GITHUB = "https://github.com/shkschneider/android_Skeleton"

    }

}
