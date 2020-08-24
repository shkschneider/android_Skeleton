package me.shkschneider.skeleton.kotlin.kotlinx

import java.util.concurrent.TimeUnit

/**
 * @see TimeUnit
 */
enum class BitsUnit {

    B {
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toBits(d: Long): Long {
            return d
        }
        override fun toKiloBits(d: Long): Long {
            return d / 1000
        }
        override fun toMegaBits(d: Long): Long {
            return d / 1000 / 1000
        }
        override fun toGigaBits(d: Long): Long {
            return d / 1000 / 1000 / 1000
        }
        override fun toTeraBits(d: Long): Long {
            return d / 1000 / 1000 / 1000 / 1000
        }
    },

    KB {
        override fun toBits(d: Long): Long {
            return d * 1000
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toKiloBits(d: Long): Long {
            return d
        }
        override fun toMegaBits(d: Long): Long {
            return d / 1000
        }
        override fun toGigaBits(d: Long): Long {
            return d / 1000 / 1000
        }
        override fun toTeraBits(d: Long): Long {
            return d / 1000 / 1000 / 1000
        }
    },

    MB {
        override fun toBits(d: Long): Long {
            return d * 1000 * 1000
        }
        override fun toKiloBits(d: Long): Long {
            return d * 1000
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toMegaBits(d: Long): Long {
            return d
        }
        override fun toGigaBits(d: Long): Long {
            return d / 1000
        }
        override fun toTeraBits(d: Long): Long {
            return d / 1000 / 1000
        }
    },

    GB {
        override fun toBits(d: Long): Long {
            return d * 1000 * 1000 * 1000
        }
        override fun toKiloBits(d: Long): Long {
            return d * 1000 * 1000
        }
        override fun toMegaBits(d: Long): Long {
            return d * 1000
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toGigaBits(d: Long): Long {
            return d
        }
        override fun toTeraBits(d: Long): Long {
            return d / 1000
        }
    },

    TB {
        override fun toBits(d: Long): Long {
            return d * 1000 * 1000 * 1000 * 1000
        }
        override fun toKiloBits(d: Long): Long {
            return d * 1000 * 1000 * 1000
        }
        override fun toMegaBits(d: Long): Long {
            return d * 1000 * 1000
        }
        override fun toGigaBits(d: Long): Long {
            return d * 1000
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toTeraBits(d: Long): Long {
            return d
        }
    };

    abstract fun toBits(d: Long): Long
    abstract fun toKiloBits(d: Long): Long
    abstract fun toMegaBits(d: Long): Long
    abstract fun toGigaBits(d: Long): Long
    abstract fun toTeraBits(d: Long): Long

}
