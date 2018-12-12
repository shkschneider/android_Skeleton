package me.shkschneider.skeleton.di

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

open class SkeletonApplication : me.shkschneider.skeleton.SkeletonApplication(), KodeinAware {

    override val kodein = Kodein {
        // Dependencies goes here
    }

}
