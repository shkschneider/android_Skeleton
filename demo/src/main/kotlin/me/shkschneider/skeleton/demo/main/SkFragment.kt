@file:Suppress("DEPRECATION")

package me.shkschneider.skeleton.demo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.demo.R
import me.shkschneider.skeleton.helperx.log.Logger
import me.shkschneider.skeleton.javax.AlphanumComparator
import me.shkschneider.skeleton.uix.Inflater
import java.lang.reflect.Modifier

class SkFragment : SkeletonFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, R.layout.fragment_sk, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fill(view.findViewById<View>(R.id.data) as LinearLayout, listOf(
                me.shkschneider.skeleton.datax.DiskCache.Cache::class.java,
                me.shkschneider.skeleton.datax.GsonParser::class.java,
                me.shkschneider.skeleton.datax.Serializer::class.java,
                me.shkschneider.skeleton.data.CharsetHelper::class.java,
                me.shkschneider.skeleton.data.DatabaseHelper::class.java,
                me.shkschneider.skeleton.data.DataHelper::class.java,
                me.shkschneider.skeleton.data.FileHelper::class.java,
                me.shkschneider.skeleton.data.SharedPreferencesHelper::class.java,
                me.shkschneider.skeleton.data.StreamHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.helper) as LinearLayout, listOf(
                me.shkschneider.skeleton.helperx.FinalCountdown::class.java,
                Logger::class.java,
                me.shkschneider.skeleton.helper.AccountHelper::class.java,
                me.shkschneider.skeleton.helper.ActivityTransitionHelper::class.java,
                me.shkschneider.skeleton.helper.AndroidHelper::class.java,
                me.shkschneider.skeleton.helper.ApplicationHelper::class.java,
                me.shkschneider.skeleton.helper.AssetsHelper::class.java,
                me.shkschneider.skeleton.helper.BroadcastHelper::class.java,
                me.shkschneider.skeleton.helper.BundleHelper::class.java,
                me.shkschneider.skeleton.helper.ClipboardHelper::class.java,
                me.shkschneider.skeleton.helper.ContextHelper::class.java,
                me.shkschneider.skeleton.helper.DateTimeHelper::class.java,
                me.shkschneider.skeleton.helper.DeviceHelper::class.java,
                me.shkschneider.skeleton.helper.HandlerHelper::class.java,
                me.shkschneider.skeleton.helper.IdHelper::class.java,
                me.shkschneider.skeleton.helper.IntentHelper::class.java,
                me.shkschneider.skeleton.helper.KeyboardHelper::class.java,
                me.shkschneider.skeleton.helper.LocaleHelper::class.java,
                me.shkschneider.skeleton.helper.NotificationHelper::class.java,
                me.shkschneider.skeleton.helper.RunnableHelper::class.java,
                me.shkschneider.skeleton.helper.ScreenHelper::class.java,
                me.shkschneider.skeleton.helper.ShortcutHelper::class.java,
                me.shkschneider.skeleton.helper.SoundHelper::class.java,
                me.shkschneider.skeleton.helper.SpannableStringHelper::class.java,
                me.shkschneider.skeleton.helper.SystemHelper::class.java,
                me.shkschneider.skeleton.helper.ThreadHelper::class.java,
                me.shkschneider.skeleton.helper.VibratorHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.java) as LinearLayout, listOf(
                me.shkschneider.skeleton.javax.Randomizer::class.java,
                me.shkschneider.skeleton.javax.SemanticVersion::class.java,
                me.shkschneider.skeleton.javax.Tasker::class.java,
                me.shkschneider.skeleton.java.StringHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.network) as LinearLayout, listOf(
                me.shkschneider.skeleton.networkx.WebService::class.java,
                me.shkschneider.skeleton.network.NetworkHelper::class.java,
                me.shkschneider.skeleton.network.UrlHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.security) as LinearLayout, listOf(
                me.shkschneider.skeleton.securityx.ComplexCrypt::class.java,
                me.shkschneider.skeleton.securityx.SimpleCrypt::class.java,
                me.shkschneider.skeleton.security.HashHelper::class.java,
                me.shkschneider.skeleton.security.HmacHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.ui) as LinearLayout, listOf(
                me.shkschneider.skeleton.uix.BottomSheet::class.java,
                me.shkschneider.skeleton.uix.FloatingActionButtonCompat::class.java,
                me.shkschneider.skeleton.uix.Inflater::class.java,
                me.shkschneider.skeleton.uix.Notify::class.java,
                me.shkschneider.skeleton.uix.OverlayLoader::class.java,
                me.shkschneider.skeleton.uix.Tooltips::class.java
        ))
    }

    private fun fill(linearLayout: LinearLayout?, cs: List<Class<out Any>>) {
        linearLayout ?: return
        for (c in cs.distinct()) {
            with(Inflater.inflate(linearLayout, R.layout.ui)) {
                (findViewById<View>(R.id.textView1) as TextView).text = c.name
                        .replaceFirst("^.+\\.".toRegex(), "")
                        .replaceFirst("\\$.+$".toRegex(), "")
                        .replace("([a-z])([A-Z])".toRegex(), "$1\n$2")
                val textView2 = findViewById<TextView>(R.id.textView2)
                textView2.text = ""
                // fields
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
                // methods
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
                linearLayout.addView(this)
            }
        }
    }

}
