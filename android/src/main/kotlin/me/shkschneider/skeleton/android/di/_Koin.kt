package me.shkschneider.skeleton.android.di

import me.shkschneider.skeleton.android.core.SkeletonApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

// SkeletonApplication.onCreate()
// module { factory/single }

fun SkeletonApplication.koin(modules: List<Module>) {
    startKoin {
        androidLogger(Level.DEBUG)
        androidContext(this@koin)
        modules(modules)
    }
}

fun SkeletonApplication.koin(vararg modules: Module) {
    koin(modules.toList())
}

// Simply retrieve anywhere using: by inject()
