package me.shkschneider.skeleton.di

import me.shkschneider.skeleton.SkeletonApplication
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext.startKoin

// SkeletonApplication.onCreate()
// module { factory/single }

fun SkeletonApplication.koin(modules: List<Module>) {
    startKoin(this, modules)
}

fun SkeletonApplication.koin(vararg modules: Module) {
    startKoin(this, modules.toList())
}

// Simply retrieve anywhere using: by inject()
