package me.shkschneider.skeleton.demo

import org.koin.dsl.module
import java.util.UUID

object Koin {

    val mainComponent = module {
        factory<UUID> { UUID.randomUUID() }
    }

}
