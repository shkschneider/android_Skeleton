package me.shkschneider.skeleton.android.content

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.graphics.BitmapHelper
import me.shkschneider.skeleton.android.os.LocaleHelper
import me.shkschneider.skeleton.kotlin.data.MimeTypes
import me.shkschneider.skeleton.android.network.UrlHelper
import me.shkschneider.skeleton.android.os.AndroidHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.android.provider.AndroidFeature
import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.File

// <http://developer.android.com/reference/android/content/Intent.html>
object IntentHelper {

    const val FLAGS_HOME = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    const val FLAGS_CLEAR = (Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP)

    object GooglePlay {

        fun application(packageName: String): Intent {
            return external(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        }

        fun publisher(pub: String): Intent {
            return external(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://search?pub:$pub")
            })
        }

        fun search(q: String): Intent {
            return external(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://search?q=$q")
            })
        }

    }

    private const val REQUEST_CODE_CAMERA = 111
    private const val REQUEST_CODE_GALLERY = 222
    private const val REQUEST_CODE_RINGTONE = 333
    private const val REQUEST_CODE_PERMISSIONS = AndroidHelper.ANDROID_6

    fun main(): Intent? {
        ApplicationHelper.packageManager.getLaunchIntentForPackage(ApplicationHelper.packageName)?.let { intent ->
            return Intent.makeMainActivity(intent.component).addFlags(FLAGS_HOME)
        }
        Logger.warning("Intent was NULL")
        return null
    }

    fun restart(): Intent? {
        ApplicationHelper.packageManager.getLaunchIntentForPackage(ApplicationHelper.packageName)?.let { intent ->
            return Intent.makeRestartActivityTask(intent.component).addFlags(FLAGS_CLEAR)
        }
        Logger.warning("Intent was NULL")
        return null
    }

    fun view(uri: Uri): Intent =
        external(Intent(Intent.ACTION_VIEW, uri))

    fun web(url: String): Intent? {
        if (!UrlHelper.valid(url)) {
            Logger.warning("Url was invalid")
            return null
        }
        return external(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun share(subject: String?, text: String?): Intent =
        Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = MimeTypes.TEXT_PLAIN
            // Email: data = Uri.parse("mailto:"))
            //        putExtra(Intent.EXTRA_EMAIL, new String[] { ... })
            if (!subject.isNullOrBlank()) {
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            if (!text.isNullOrBlank()) {
                putExtra(Intent.EXTRA_TEXT, text)
            }
        }, null)

    fun directions(fromLatitude: Long, fromLongitude: Long, toLatitude: Long, toLongitude: Long): Intent =
        external(Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format(LocaleHelper.Device.locale(),
                        "http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                        fromLatitude, fromLongitude,
                        toLatitude, toLongitude))))

    fun ringtone(existingUri: String?): Intent =
        Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(existingUri.orEmpty()))
        }

    fun applicationSettings(): Intent =
        external(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:" + ApplicationHelper.packageName)
        })

    fun systemSettings(): Intent =
        external(Intent(Settings.ACTION_SETTINGS))

    fun text(uri: Uri): Intent =
        external(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, MimeTypes.TEXT_PLAIN)
        })

    fun audio(uri: Uri): Intent =
        external(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, MimeTypes.AUDIO)
        })

    fun video(uri: Uri): Intent =
        external(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, MimeTypes.VIDEO)
        })

    fun picture(uri: Uri): Intent =
        external(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, MimeTypes.IMAGE)
        })

    fun gallery(): Intent =
        external(Intent(Intent.ACTION_PICK).apply {
            type = MimeTypes.IMAGE
        })

    fun camera(file: File): Intent? {
        if (!AndroidFeature.Camera.isAvailable) {
            Logger.warning("Camera was unavailable")
            return null
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                .putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, true)
        if (!canHandle(intent)) {
            Logger.warning("Cannot handle Intent")
            return null
        }
        return external(intent)
    }

    fun file(): Intent =
        external(Intent(Intent.ACTION_GET_CONTENT).apply {
            type = MimeTypes.FILE
        })

    fun dial(phone: String): Intent =
        external(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        })

    fun call(phone: String): Intent =
        external(Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phone")
        })

    fun sms(phone: String, message: String): Intent =
        external(Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phone")
            putExtra("sms_body", message)
        })

    fun contact(): Intent =
        external(Intent(Intent.ACTION_PICK, Uri.parse("content://com.android.contacts/contacts")).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        })

    fun canHandle(intent: Intent): Boolean {
        // return (intent.resolveActivity(ApplicationHelper.packageManager()) != null)
        val resolveInfos = ApplicationHelper.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfos.size > 0
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent? = null): Any? {
        if (resultCode != Activity.RESULT_OK) {
            Logger.debug("ResultCode was not OK")
            return null
        }
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                intent?.extras?.get("data")?.let { data ->
                    return data
                }
                Logger.warning("Intent's data was NULL")
                return null
            }
            REQUEST_CODE_GALLERY -> {
                intent?.data?.let { uri ->
                    return BitmapHelper.decodeUri(uri)
                }
                Logger.warning("Intent's data was NULL")
                return null
            }
            REQUEST_CODE_RINGTONE -> {
                intent?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)?.let { uri ->
                    return RingtoneManager.getRingtone(ContextProvider.applicationContext(), uri)
                }
                Logger.warning("Intent's extra was NULL")
                return null
            }
            else -> return null
        }
    }

    // <http://developer.android.com/training/implementing-navigation/descendant.html#external-activities>
    @Suppress("DEPRECATION")
    private fun external(intent: Intent): Intent =
        with(intent) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }

}