@file:Suppress("DEPRECATION")

package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.extensions.Intent
import me.shkschneider.skeleton.extensions.toStringOrEmpty
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.BroadcastHelper
import me.shkschneider.skeleton.helper.DateTimeHelper
import me.shkschneider.skeleton.helper.NotificationHelper
import me.shkschneider.skeleton.javax.AlphanumComparator
import me.shkschneider.skeleton.networkx.HttpURLConnectionWebService
import me.shkschneider.skeleton.ui.AnimationHelper
import me.shkschneider.skeleton.uix.Inflater
import me.shkschneider.skeleton.uix.Toaster
import java.lang.reflect.Modifier

class SkFragment : SkeletonFragment() {

    private val mBroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // val code = intent.getIntExtra(BROADCAST_SECRET_CODE, 0)
            network()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, R.layout.fragment_sk, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fill(view.findViewById<View>(R.id.data) as LinearLayout, arrayOf(
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
        fill(view.findViewById<View>(R.id.helper) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.helperx.FinalCountdown::class.java,
                me.shkschneider.skeleton.helperx.Logger::class.java,
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
        fill(view.findViewById<View>(R.id.java) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.javax.Randomizer::class.java,
                me.shkschneider.skeleton.javax.SemanticVersion::class.java,
                me.shkschneider.skeleton.javax.SkeletonWorker::class.java,
                me.shkschneider.skeleton.javax.Tasker::class.java,
                me.shkschneider.skeleton.java.StringHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.network) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.networkx.FuelWebService::class.java,
                me.shkschneider.skeleton.network.NetworkHelper::class.java,
                me.shkschneider.skeleton.network.UrlHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.security) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.securityx.ComplexCrypt::class.java,
                me.shkschneider.skeleton.securityx.SimpleCrypt::class.java,
                me.shkschneider.skeleton.security.Base64Helper::class.java,
                me.shkschneider.skeleton.security.HashHelper::class.java,
                me.shkschneider.skeleton.security.HmacHelper::class.java
        ))
        fill(view.findViewById<View>(R.id.ui) as LinearLayout, arrayOf(
                me.shkschneider.skeleton.uix.BottomSheet::class.java,
                me.shkschneider.skeleton.uix.Inflater::class.java,
                me.shkschneider.skeleton.uix.OverlayLoader::class.java,
                me.shkschneider.skeleton.uix.Toaster::class.java,
                me.shkschneider.skeleton.ui.AnimationHelper::class.java,
                me.shkschneider.skeleton.ui.BitmapHelper::class.java,
                me.shkschneider.skeleton.ui.DrawableHelper::class.java,
                me.shkschneider.skeleton.ui.EditTextHelper::class.java,
                me.shkschneider.skeleton.ui.ImageHelper::class.java,
                me.shkschneider.skeleton.ui.PaletteHelper::class.java,
                me.shkschneider.skeleton.ui.ScrollViewHelper::class.java,
                me.shkschneider.skeleton.ui.TextViewHelper::class.java,
                me.shkschneider.skeleton.ui.ThemeHelper::class.java,
                me.shkschneider.skeleton.ui.ViewHelper::class.java,
                me.shkschneider.skeleton.ui.WebViewHelper::class.java
        ))

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setImageResource(android.R.drawable.ic_dialog_info)
        floatingActionButton.setOnClickListener {
            val intent = Intent(ShkMod.BROADCAST_SECRET).putExtra(ShkMod.BROADCAST_SECRET_CODE, 42)
            LocalBroadcastManager.getInstance(floatingActionButton.context).sendBroadcast(intent)
        }
        AnimationHelper.revealOn(floatingActionButton)
    }

    private fun fill(linearLayout: LinearLayout?, cs: Array<Class<*>>) {
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
                    if (field.type.simpleName != "Companion" && field.name != "INSTANCE" && ! field.name.contains("$")) {
                        fields.add(field.type.simpleName + " " + field.name)
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
                    textView2.append(if (! textView2.text.isNullOrEmpty()) "\n" else "")
                    textView2.append(it)
                }
                linearLayout.addView(this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        BroadcastHelper.register(mBroadcastReceiver, IntentFilter(ShkMod.BROADCAST_SECRET))
    }

    override fun onStop() {
        super.onStop()
        BroadcastHelper.unregister(mBroadcastReceiver)
    }

    private fun network() {
        HttpURLConnectionWebService(HttpURLConnectionWebService.Method.GET, ShkMod.URL)
                .callback(object: HttpURLConnectionWebService.Callback {
                    override fun success(result: HttpURLConnectionWebService.Response?) {
                        result?.let {
                            notification(DateTimeHelper.timestamp(), ApplicationHelper.name().orEmpty(),
                                    it.message.orEmpty())
                        } ?: run {
                            Toaster.show(result.toString())
                        }
                    }
                    override fun failure(e: HttpURLConnectionWebService.Error) {
                        Toaster.show(e.toStringOrEmpty())
                    }
                })
                .run()
    }

    private fun notification(id: Int, title: String, message: String) {
        val context = context ?: return
        val intent = Intent(context, MainActivity::class)
                .setAction(ShkMod.BROADCAST_SECRET)
                .putExtra("title", title)
                .putExtra("message", message)
        val channel = NotificationHelper.Channel(id.toString(), id.toString(), true, true, true)
        // final NotificationChannel notificationChannel = channel.get();
        val notificationBuilder = NotificationHelper.Builder(channel)
                .setShowWhen(false)
                .setContentTitle("Skeleton")
                .setContentText("for Android")
                .setContentIntent(activity?.let { NotificationHelper.pendingIntent(it, intent) })
                .setTicker("Sk!")
                .setColor(ContextCompat.getColor(context, R.color.accentColor))
                .setSmallIcon(ApplicationHelper.DEFAULT_ICON)
                .setNumber(42)
        NotificationHelper.notify(0, notificationBuilder.build())
    }

}
