package me.shkschneider.skeleton.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.java.AlphanumComparator;
import me.shkschneider.skeleton.java.SkHide;

public class SkFragment extends SkeletonFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return super.onCreateView(inflater, R.layout.fragment_sk, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fill((LinearLayout) view.findViewById(R.id.data), new Class[] {
                me.shkschneider.skeleton.data.CharsetHelper.class,
                me.shkschneider.skeleton.data.DiskCache.Internal.class,
                me.shkschneider.skeleton.data.DiskCache.External.class,
                me.shkschneider.skeleton.data.ExternalDataHelper.class,
                me.shkschneider.skeleton.data.FileHelper.class,
                me.shkschneider.skeleton.data.GsonParser.class,
                me.shkschneider.skeleton.data.InternalDataHelper.class,
                me.shkschneider.skeleton.data.JsonParser.class,
                me.shkschneider.skeleton.data.MemoryBitmapCache.class,
                me.shkschneider.skeleton.data.MemoryCache.class,
                me.shkschneider.skeleton.data.SerializeHelper.class
        });
        fill((LinearLayout) view.findViewById(R.id.helper), new Class[] {
                me.shkschneider.skeleton.helper.ActivityHelper.class,
                me.shkschneider.skeleton.helper.ActivityTransitionHelper.class,
                me.shkschneider.skeleton.helper.AndroidHelper.class,
                me.shkschneider.skeleton.helper.ApplicationHelper.class,
                me.shkschneider.skeleton.helper.AssetsHelper.class,
                me.shkschneider.skeleton.helper.BundleHelper.class,
                me.shkschneider.skeleton.helper.DateTimeHelper.class,
                me.shkschneider.skeleton.helper.DeviceHelper.class,
                me.shkschneider.skeleton.helper.IdHelper.class,
                me.shkschneider.skeleton.helper.IntentHelper.class,
                me.shkschneider.skeleton.helper.KeyboardHelper.class,
                me.shkschneider.skeleton.helper.LocaleHelper.class,
                me.shkschneider.skeleton.helper.LogHelper.class,
                me.shkschneider.skeleton.helper.NotificationHelper.class,
                me.shkschneider.skeleton.helper.RunnableHelper.class,
                me.shkschneider.skeleton.helper.ScreenHelper.class,
                me.shkschneider.skeleton.helper.SharedPreferencesHelper.class,
                me.shkschneider.skeleton.helper.SoundHelper.class,
                me.shkschneider.skeleton.helper.SpannableStringHelper.class,
                me.shkschneider.skeleton.helper.SystemHelper.class,
                me.shkschneider.skeleton.helper.VibratorHelper.class
        });
        fill((LinearLayout) view.findViewById(R.id.java), new Class[] {
                me.shkschneider.skeleton.java.ArrayHelper.class,
                me.shkschneider.skeleton.java.ClassHelper.class,
                me.shkschneider.skeleton.java.ListHelper.class,
                me.shkschneider.skeleton.java.MapHelper.class,
                me.shkschneider.skeleton.java.ObjectHelper.class,
                me.shkschneider.skeleton.java.RandomHelper.class,
                me.shkschneider.skeleton.java.StringHelper.class
        });
        fill((LinearLayout) view.findViewById(R.id.network), new Class[] {
                me.shkschneider.skeleton.network.MyRequest.class,
                me.shkschneider.skeleton.network.MyResponse.class,
                me.shkschneider.skeleton.network.NetworkHelper.class,
                me.shkschneider.skeleton.network.Proxy.class,
                me.shkschneider.skeleton.network.UrlHelper.class
        });
        fill((LinearLayout) view.findViewById(R.id.security), new Class[] {
                me.shkschneider.skeleton.security.Base64Helper.class,
                me.shkschneider.skeleton.security.ComplexCrypt.class,
                me.shkschneider.skeleton.security.HashHelper.class,
                me.shkschneider.skeleton.security.HmacHelper.class,
                me.shkschneider.skeleton.security.SimpleCrypt.class
        });
        fill((LinearLayout) view.findViewById(R.id.ui), new Class[] {
                me.shkschneider.skeleton.ui.BitmapHelper.class,
                me.shkschneider.skeleton.ui.DrawableHelper.class,
                me.shkschneider.skeleton.ui.EditTextHelper.class,
                me.shkschneider.skeleton.ui.FloatingActionButtonCompat.class,
                me.shkschneider.skeleton.ui.PaletteHelper.class,
                me.shkschneider.skeleton.ui.ScrollHelper.class,
                me.shkschneider.skeleton.ui.ScrollViewHelper.class,
                me.shkschneider.skeleton.ui.TextViewHelper.class,
                me.shkschneider.skeleton.ui.ThemeHelper.class,
                me.shkschneider.skeleton.ui.UiHelper.class,
                me.shkschneider.skeleton.ui.ViewHelper.class,
                me.shkschneider.skeleton.ui.WebViewHelper.class
        });
    }

    private void fill(@NonNull final LinearLayout linearLayout, final Class[] cs) {
        for (final Class c : cs) {
            final View ui = LayoutInflater.from(getContext()).inflate(R.layout.ui, linearLayout, false);
            ((TextView) ui.findViewById(R.id.textView1)).setText(c.getName()
                    .replaceFirst("^.+\\.", "")
                    .replaceFirst("\\$.+$", "")
                    .replaceAll("([a-z])([A-Z])", "$1\n$2"));
            final TextView textView2 = (TextView) ui.findViewById(R.id.textView2);
            textView2.setText("");
            // fields
            final List<String> fields = new ArrayList<>();
            for (final Field field : c.getDeclaredFields()) {
                final int modifiers = field.getModifiers();
                try {
                    if (Modifier.isPrivate(modifiers)
                            || field.getAnnotation(Deprecated.class) != null
                            || field.getAnnotation(SkHide.class) != null) {
                        continue;
                    }
                }
                catch (final NoClassDefFoundError e) {
                    continue;
                }
                final String name = field.getName();
                if (! name.contains("$")) {
                    fields.add(name);
                }
            }
            Collections.sort(fields, new AlphanumComparator());
            for (final String field : fields) {
                if (! TextUtils.isEmpty(textView2.getText())) {
                    textView2.append("\n");
                }
                textView2.append(field);
            }
            // methods
            final List<String> methods = new ArrayList<>();
            for (final Method method : c.getDeclaredMethods()) {
                final int modifiers = method.getModifiers();
                try {
                    if (Modifier.isPrivate(modifiers)
                            || method.getAnnotation(Deprecated.class) != null
                            || method.getAnnotation(SkHide.class) != null) {
                        continue;
                    }
                }
                catch (final NoClassDefFoundError e) {
                    continue;
                }
                final String name = method.getName();
                if (! name.contains("$")) {
                    if (methods.contains(name + "()")) {
                        continue;
                    }
                    methods.add(name + "()");
                }
            }
            Collections.sort(methods, new AlphanumComparator());
            for (final String method : methods) {
                if (!TextUtils.isEmpty(textView2.getText())) {
                    textView2.append("\n");
                }
                textView2.append(method);
            }
            linearLayout.addView(ui);
        }
    }

}
