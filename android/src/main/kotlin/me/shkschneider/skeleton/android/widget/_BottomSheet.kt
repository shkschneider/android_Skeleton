package me.shkschneider.skeleton.android.widget

import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

val BottomSheetDialog.bottomSheetBehavior: BottomSheetBehavior<View>
    get() {
        val bottomSheet = (this as AppCompatDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        return BottomSheetBehavior.from(bottomSheet)
    }

fun BottomSheetDialog.expand() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetDialog.collapse() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
}
