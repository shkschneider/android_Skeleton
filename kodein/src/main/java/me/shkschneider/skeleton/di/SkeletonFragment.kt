package me.shkschneider.skeleton.di

import android.os.Bundle
import android.view.View
import com.github.salomonbrys.kodein.KodeinAware

open class SkeletonFragment : me.shkschneider.skeleton.SkeletonFragment(), KodeinAware {

    override val kodein by lazy {
        (activity?.application as SkeletonApplication).kodein
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
