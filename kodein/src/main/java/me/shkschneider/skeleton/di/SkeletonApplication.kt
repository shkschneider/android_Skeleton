package me.shkschneider.skeleton.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware

open class SkeletonApplication : me.shkschneider.skeleton.SkeletonApplication(), KodeinAware {

    override val kodein = Kodein {}

}
