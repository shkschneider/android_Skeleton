package me.shkschneider.skeleton.demo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.shkschneider.skeleton.android.app.SkeletonFragment
import me.shkschneider.skeleton.android.view.Inflater
import me.shkschneider.skeleton.demo.R
import me.shkschneider.skeleton.kotlin.jvm.trySilently
import me.shkschneider.skeleton.kotlin.util.AlphanumComparator
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

class SkFragment : SkeletonFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, R.layout.fragment_sk, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sections = listOf(
                R.id.sk_app to ("app" to listOf(
                        me.shkschneider.skeleton.android.app.SkeletonApplication::class,
                        me.shkschneider.skeleton.android.app.SkeletonActivity::class,
                        me.shkschneider.skeleton.android.app.SkeletonFragmentActivity::class,
                        me.shkschneider.skeleton.android.app.SkeletonFragment::class,
                        me.shkschneider.skeleton.android.app.SkeletonDialog::class
                )),
                R.id.sk_arch to ("arch" to listOf(
                        me.shkschneider.skeleton.android.arch.SkeletonLiveData::class
                )),
                R.id.sk_cache to ("cache" to listOf(
                        me.shkschneider.skeleton.android.cache.MemoryBitmapCache::class,
                        me.shkschneider.skeleton.android.cache.MemoryCache::class
                )),
                R.id.sk_content to ("content" to listOf(
                        me.shkschneider.skeleton.android.content.BroadcastHelper::class,
                        me.shkschneider.skeleton.android.content.ClipboardHelper::class,
                        me.shkschneider.skeleton.android.content.IntentHelper::class,
                        me.shkschneider.skeleton.android.content.ShortcutHelper::class,
                        me.shkschneider.skeleton.android.content.SkeletonProvider::class,
                        me.shkschneider.skeleton.android.content.SkeletonReceiver::class
                )),
                R.id.sk_db to ("db" to listOf(
                        me.shkschneider.skeleton.android.db.SkeletonDao::class,
                        me.shkschneider.skeleton.android.db.SkeletonDatabaseBuilders::class,
                        me.shkschneider.skeleton.android.db.SkeletonTypeConverters::class
                )),
                R.id.sk_graphics to ("graphics" to listOf(
                        me.shkschneider.skeleton.android.graphics.BitmapHelper::class,
                        me.shkschneider.skeleton.android.graphics.DrawableHelper::class,
                        me.shkschneider.skeleton.android.graphics.ImageHelper::class
                )),
                R.id.sk_helper to ("helper" to listOf(
                        me.shkschneider.skeleton.android.helper.AccountHelper::class,
                        me.shkschneider.skeleton.android.helper.KeyboardHelper::class,
                        me.shkschneider.skeleton.android.helper.ScreenHelper::class,
                        me.shkschneider.skeleton.android.helper.SoundHelper::class,
                        me.shkschneider.skeleton.android.helper.VibratorHelper::class
                )),
                R.id.sk_io to ("io" to listOf(
                        me.shkschneider.skeleton.android.io.SharedPreferencesHelper::class
                )),
                R.id.sk_json to ("json" to listOf(
                        me.shkschneider.skeleton.android.json.IParser::class,
                        me.shkschneider.skeleton.android.json.GsonParser::class
                )),
                R.id.sk_json to ("jvm" to listOf(
                        me.shkschneider.skeleton.android.jvm.StrictModeHelper::class
                )),
                R.id.sk_log to ("log" to listOf(
                        me.shkschneider.skeleton.android.log.AndroidLogger::class
                )),
                R.id.sk_network to ("network" to listOf(
                        me.shkschneider.skeleton.android.network.NetworkHelper::class,
                        me.shkschneider.skeleton.android.network.UrlHelper::class,
                        me.shkschneider.skeleton.android.network.WebService::class
                )),
                R.id.sk_os to ("os" to listOf(
                        me.shkschneider.skeleton.android.os.AndroidHelper::class,
                        me.shkschneider.skeleton.android.os.BundleHelper::class,
                        me.shkschneider.skeleton.android.os.DeviceHelper::class,
                        me.shkschneider.skeleton.android.os.HandlerHelper::class,
                        me.shkschneider.skeleton.android.os.LocaleHelper::class,
                        me.shkschneider.skeleton.android.os.RunnableHelper::class,
                        me.shkschneider.skeleton.android.os.SystemHelper::class,
                        me.shkschneider.skeleton.android.os.ThreadHelper::class
                )),
                R.id.sk_provider to ("provider" to listOf(
                        me.shkschneider.skeleton.android.provider.AndroidFeature::class,
                        me.shkschneider.skeleton.android.provider.AndroidSystemProperty::class,
                        me.shkschneider.skeleton.android.provider.ConnectivityProvider::class,
                        me.shkschneider.skeleton.android.provider.ContextProvider::class,
                        me.shkschneider.skeleton.android.provider.IdProvider::class
                        // FIXME if running lower API level, resolution fails
                        // me.shkschneider.skeleton.android.provider.SystemServices::class
                )),
                R.id.sk_security to ("security" to listOf(
                        me.shkschneider.skeleton.android.security.ComplexCrypt::class,
                        me.shkschneider.skeleton.android.security.Keystore::class,
                        me.shkschneider.skeleton.android.security.Permissions::class
                )),
                R.id.sk_text to ("text" to listOf(
                        me.shkschneider.skeleton.android.text.SpannableStringHelper::class
                )),
                R.id.sk_util to ("util" to listOf(
                        me.shkschneider.skeleton.android.util.FinalCountdown::class,
                        me.shkschneider.skeleton.android.util.Metrics::class
                )),
                R.id.sk_view to ("view" to listOf(
                        me.shkschneider.skeleton.android.view.AutoCompletion::class,
                        me.shkschneider.skeleton.android.view.BottomSheet::class,
                        me.shkschneider.skeleton.android.view.FloatingActionButtonCompat::class,
                        me.shkschneider.skeleton.android.view.Inflater::class,
                        me.shkschneider.skeleton.android.view.Notify::class,
                        me.shkschneider.skeleton.android.view.OverlayLoader::class,
                        me.shkschneider.skeleton.android.view.Tooltips::class
                )),
                R.id.sk_work to ("work" to listOf(
                        me.shkschneider.skeleton.android.work.SkeletonWorker::class
                ))
        )

        for ((layoutId, pair) in sections) {
            view.findViewById<LinearLayout>(R.id.content).findViewById<ViewGroup>(layoutId).run {
                val (name, classes) = pair
                findViewById<TextView>(R.id.title).text = name
                fill(findViewById(R.id.content), classes)
            }
        }
    }

    private fun fill(linearLayout: LinearLayout?, cs: List<KClass<out Any>>) {
        linearLayout ?: return
        for (_c in cs.distinct()) {
            val c = _c.java
            with(Inflater.inflate(linearLayout, R.layout.ui)) {
                (findViewById<View>(R.id.textView1) as TextView).text = c.name
                        .replaceFirst("^.+\\.".toRegex(), "")
                        .replaceFirst("\\$.+$".toRegex(), "")
                        .replace("([a-z])([A-Z])".toRegex(), "$1\n$2")
                val textView2 = findViewById<TextView>(R.id.textView2)
                textView2.text = ""
                // fields
                trySilently {
                    val fields = ArrayList<String>()
                    for (field in c.declaredFields) {
                        if (Modifier.isPrivate(field.modifiers)
                                || field.isAnnotationPresent(Deprecated::class.java)) {
                            continue
                        }
                        if (field.type.simpleName != "Companion" && field.name != "INSTANCE" && !field.name.contains("$")) {
                            fields.add(field.type.simpleName + " " + field.name)
                        }
                    }
                    fields.sortWith(AlphanumComparator())
                    for (field in fields) {
                        textView2.append(if (!textView2.text.isNullOrEmpty()) "\n" else "")
                        textView2.append(field)
                    }
                }
                // methods
                trySilently {
                    val methods = ArrayList<String>()
                    for (method in c.declaredMethods) {
                        if (Modifier.isPrivate(method.modifiers)
                                || method.isAnnotationPresent(Deprecated::class.java)) {
                            continue
                        }
                        val signature = method.returnType.simpleName + " " + method.name + "()"
                        if (methods.contains(signature) || signature.contains("$")) {
                            continue
                        }
                        methods.add(signature)
                    }
                    methods.sortWith(AlphanumComparator())
                    methods.forEach {
                        textView2.append(if (!textView2.text.isNullOrEmpty()) "\n" else "")
                        textView2.append(it)
                    }
                }
                linearLayout.addView(this)
            }
        }
    }

}
