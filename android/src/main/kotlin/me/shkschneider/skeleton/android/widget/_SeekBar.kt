package me.shkschneider.skeleton.android.widget

import android.widget.SeekBar

// <https://github.com/nowfalsalahudeen/KdroidExt>

fun SeekBar.onProgressChanged(callback: (Int, Boolean) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

        override fun onStopTrackingTouch(seekBar: SeekBar) = Unit

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            callback(progress, fromUser)
        }

    })
}
