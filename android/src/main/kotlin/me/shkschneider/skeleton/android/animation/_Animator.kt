package me.shkschneider.skeleton.android.animation

import android.animation.Animator

// <https://github.com/nowfalsalahudeen/KdroidExt>

fun Animator.addListener(
        onEnd: (Animator) -> Unit = {},
        onStart: (Animator) -> Unit = {},
        onCancel: (Animator) -> Unit = {},
        onRepeat: (Animator) -> Unit = {}
) {
    addListener(object : Animator.AnimatorListener {

        override fun onAnimationRepeat(animator: Animator) {
            onRepeat(animator)
        }

        override fun onAnimationEnd(animator: Animator) {
            onEnd(animator)
        }

        override fun onAnimationCancel(animator: Animator) {
            onCancel(animator)
        }

        override fun onAnimationStart(animator: Animator) {
            onStart(animator)
        }

    })
}

fun Animator.onStart(onStart: (Animator) -> Unit) {
    addListener(onStart = onStart)
}

fun Animator.onEnd(onEnd: (Animator) -> Unit) {
    addListener(onEnd = onEnd)
}

fun Animator.onCancel(onCancel: (Animator) -> Unit) {
    addListener(onCancel = onCancel)
}

fun Animator.onRepeat(onRepeat: (Animator) -> Unit) {
    addListener(onRepeat = onRepeat)
}
