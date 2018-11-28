package me.shkschneider.skeleton.di

import android.os.Bundle
import org.kodein.di.KodeinAware

open class SkeletonFragmentActivity : me.shkschneider.skeleton.SkeletonFragmentActivity(), KodeinAware {

    override val kodein by lazy {
        (application as SkeletonApplication).kodein
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
