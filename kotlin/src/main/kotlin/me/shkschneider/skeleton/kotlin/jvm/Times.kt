package me.shkschneider.skeleton.kotlin.jvm

import java.time.Duration
import java.time.Period

// FIXME API_26?
val Long.nanos get() = Duration.ofNanos(this)!!
val Long.micros get() = Duration.ofNanos(this * 1000L)
val Long.millis get() = Duration.ofMillis(this)!!
val Long.seconds get() = Duration.ofSeconds(this)!!
val Long.minutes get() = Duration.ofMinutes(this)!!
val Long.hours get() = Duration.ofHours(this)!!

// FIXME API_26?
val Int.days get() = Period.ofDays(this)!!
val Int.weeks get() = Period.ofWeeks(this)!!
val Int.months get() = Period.ofMonths(this)!!
val Int.years get() = Period.ofYears(this)!!
