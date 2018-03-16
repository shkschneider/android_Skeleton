package me.shkschneider.skeleton.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.java.AlphanumComparator
import me.shkschneider.skeleton.java.SkHide
import java.lang.reflect.Modifier
import java.util.*

class SkFragment : SkeletonFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, R.layout.fragment_sk, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fill(view.findViewById<View>(R.id.data) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.data.CharsetHelper::class.java,
                me.shkschneider.skeleton.data.DatabaseHelper::class.java,
                me.shkschneider.skeleton.data.DiskCache.Cache::class.java,
                me.shkschneider.skeleton.data.ExternalDataHelper::class.java,
                me.shkschneider.skeleton.data.FileHelper::class.java,
                me.shkschneider.skeleton.data.GsonParser::class.java,
                me.shkschneider.skeleton.data.InternalDataHelper::class.java,
                me.shkschneider.skeleton.data.JsonParser::class.java,
                me.shkschneider.skeleton.data.SerializeHelper::class.java,
                me.shkschneider.skeleton.data.SharedPreferencesHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.helper) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.helper.ActivityHelper::class.java,
                me.shkschneider.skeleton.helper.ActivityTransitionHelper::class.java,
                me.shkschneider.skeleton.helper.AndroidHelper::class.java,
                me.shkschneider.skeleton.helper.ApplicationHelper::class.java,
                me.shkschneider.skeleton.helper.AssetsHelper::class.java,
                me.shkschneider.skeleton.helper.BundleHelper::class.java,
                me.shkschneider.skeleton.helper.CountdownHelper::class.java,
                me.shkschneider.skeleton.helper.DateTimeHelper::class.java,
                me.shkschneider.skeleton.helper.DeviceHelper::class.java,
                me.shkschneider.skeleton.helper.IdHelper::class.java,
                me.shkschneider.skeleton.helper.IntentHelper::class.java,
                me.shkschneider.skeleton.helper.KeyboardHelper::class.java,
                me.shkschneider.skeleton.helper.LocaleHelper::class.java,
                me.shkschneider.skeleton.helper.LogHelper::class.java,
                me.shkschneider.skeleton.helper.NotificationHelper::class.java,
                me.shkschneider.skeleton.helper.RunnableHelper::class.java,
                me.shkschneider.skeleton.helper.ScreenHelper::class.java,
                me.shkschneider.skeleton.helper.SoundHelper::class.java,
                me.shkschneider.skeleton.helper.SpannableStringHelper::class.java,
                me.shkschneider.skeleton.helper.SystemHelper::class.java,
                me.shkschneider.skeleton.helper.ThreadHelper::class.java,
                me.shkschneider.skeleton.helper.VibratorHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.java) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.java.JobManager::class.java,
                me.shkschneider.skeleton.java.ObjectHelper::class.java,
                me.shkschneider.skeleton.java.RandomHelper::class.java,
                me.shkschneider.skeleton.java.StringHelper::class.java,
                me.shkschneider.skeleton.java.Tasker::class.java
        ))
        fill(view.findViewById<View>(R.id.network) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.network.MyRequest::class.java,
                me.shkschneider.skeleton.network.MyResponse::class.java,
                me.shkschneider.skeleton.network.NetworkHelper::class.java,
                me.shkschneider.skeleton.network.Proxy::class.java,
                me.shkschneider.skeleton.network.UrlHelper::class.java,
                me.shkschneider.skeleton.network.WebService::class.java
        ))
        fill(view.findViewById<View>(R.id.security) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.security.Base64Helper::class.java,
                me.shkschneider.skeleton.security.ComplexCrypt::class.java,
                me.shkschneider.skeleton.security.HashHelper::class.java,
                me.shkschneider.skeleton.security.HmacHelper::class.java,
                me.shkschneider.skeleton.security.SimpleCrypt::class.java
        ))
        fill(view.findViewById<View>(R.id.ui) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.ui.AnimationHelper::class.java,
                me.shkschneider.skeleton.ui.BitmapHelper::class.java,
                me.shkschneider.skeleton.ui.BottomSheet::class.java,
                me.shkschneider.skeleton.ui.DrawableHelper::class.java,
                me.shkschneider.skeleton.ui.EditTextHelper::class.java,
                me.shkschneider.skeleton.ui.FloatingActionButtonCompat::class.java,
                me.shkschneider.skeleton.ui.OverlayLoader::class.java,
                me.shkschneider.skeleton.ui.PaletteHelper::class.java,
                me.shkschneider.skeleton.ui.ScrollHelper::class.java,
                me.shkschneider.skeleton.ui.ScrollViewHelper::class.java,
                me.shkschneider.skeleton.ui.Snack::class.java,
                me.shkschneider.skeleton.ui.TextViewHelper::class.java,
                me.shkschneider.skeleton.ui.ThemeHelper::class.java,
                me.shkschneider.skeleton.ui.Toaster::class.java,
                me.shkschneider.skeleton.ui.UiHelper::class.java,
                me.shkschneider.skeleton.ui.ViewHelper::class.java,
                me.shkschneider.skeleton.ui.WebViewHelper::class.java
        ))
    }

    private fun fill(linearLayout: LinearLayout?, cs: Array<Class<*>>) {
        for (c in cs) {
            val ui = LayoutInflater.from(context).inflate(R.layout.ui, linearLayout, false)
            (ui.findViewById<View>(R.id.textView1) as TextView).text = c.name
                    .replaceFirst("^.+\\.".toRegex(), "")
                    .replaceFirst("\\$.+$".toRegex(), "")
                    .replace("([a-z])([A-Z])".toRegex(), "$1\n$2")
            val textView2 = ui.findViewById<TextView>(R.id.textView2)
            textView2.text = ""
            // fields
            val fields = ArrayList<String>()
            for (field in c.declaredFields) {
                val modifiers = field.modifiers
                try {
                    if (Modifier.isPrivate(modifiers)
                            || field.getAnnotation<Deprecated>(Deprecated::class.java) != null
                            || field.getAnnotation<SkHide>(SkHide::class.java) != null) {
                        continue
                    }
                } catch (e: NoClassDefFoundError) {
                    continue
                }
                val name = field.name
                if (! name.equals("serialVersionUID", true) && !name.contains("$")) {
                    fields.add(field.type.simpleName + " " + name)
                }
            }
            fields.sortWith(AlphanumComparator())
            for (field in fields) {
                textView2.append(if (! textView2.text.isNullOrEmpty()) "\n" else "")
                textView2.append(field)
            }
            // methods
            val methods = ArrayList<String>()
            for (method in c.declaredMethods) {
                val modifiers = method.modifiers
                try {
                    if (Modifier.isPrivate(modifiers)
                            || method.getAnnotation<Deprecated>(Deprecated::class.java) != null
                            || method.getAnnotation<SkHide>(SkHide::class.java) != null) {
                        continue
                    }
                } catch (e: NoClassDefFoundError) {
                    continue
                }
                val name = method.name
                if (! name.contains("$")) {
                    val signature = method.returnType.simpleName + " " + name + "()"
                    if (methods.contains(signature)) {
                        continue
                    }
                    methods.add(signature)
                }
            }
            methods.sortWith(AlphanumComparator())
            methods.forEach {
                textView2.append(if (! textView2.text.isNullOrEmpty()) "\n" else "")
                textView2.append(it)
            }
            linearLayout?.addView(ui)
        }
    }

}
