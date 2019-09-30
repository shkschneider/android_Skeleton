package me.shkschneider.skeleton.demo

import android.widget.ImageView
import androidx.annotation.ColorInt
import com.squareup.picasso.Picasso

fun ImageView.load(url: String) {
    Picasso.get().load(url).into(this)
}
