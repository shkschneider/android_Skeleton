package me.shkschneider.skeleton.kotlinx

import java.util.concurrent.TimeUnit

/**
 * @see TimeUnit
 */
enum class BytesUnit {

    B {
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toBytes(d: Long): Long {
            return d
        }
        override fun toKilobytes(d: Long): Long {
            return d / 1024
        }
        override fun toMegabytes(d: Long): Long {
            return d / 1024 / 1024
        }
        override fun toGigabytes(d: Long): Long {
            return d / 1024 / 1024 / 1024
        }
        override fun toTerabytes(d: Long): Long {
            return d / 1024 / 1024 / 1024 / 1024
        }
    },

    KB {
        override fun toBytes(d: Long): Long {
            return d * 1024
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toKilobytes(d: Long): Long {
            return d
        }
        override fun toMegabytes(d: Long): Long {
            return d / 1024
        }
        override fun toGigabytes(d: Long): Long {
            return d / 1024 / 1024
        }
        override fun toTerabytes(d: Long): Long {
            return d / 1024 / 1024 / 1024
        }
    },

    MB {
        override fun toBytes(d: Long): Long {
            return d * 1024 * 1024
        }
        override fun toKilobytes(d: Long): Long {
            return d * 1024
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toMegabytes(d: Long): Long {
            return d
        }
        override fun toGigabytes(d: Long): Long {
            return d / 1024
        }
        override fun toTerabytes(d: Long): Long {
            return d / 1024 / 1024
        }
    },

    GB {
        override fun toBytes(d: Long): Long {
            return d * 1024 * 1024 * 1024
        }
        override fun toKilobytes(d: Long): Long {
            return d * 1024 * 1024
        }
        override fun toMegabytes(d: Long): Long {
            return d * 1024
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toGigabytes(d: Long): Long {
            return d
        }
        override fun toTerabytes(d: Long): Long {
            return d / 1024
        }
    },

    TB {
        override fun toBytes(d: Long): Long {
            return d * 1024 * 1024 * 1024 * 1024
        }
        override fun toKilobytes(d: Long): Long {
            return d * 1024 * 1024 * 1024
        }
        override fun toMegabytes(d: Long): Long {
            return d * 1024 * 1024
        }
        override fun toGigabytes(d: Long): Long {
            return d * 1024
        }
        @Deprecated("Useless conversion", ReplaceWith("d"))
        override fun toTerabytes(d: Long): Long {
            return d
        }
    };

    companion object {

        fun ordinalOf(ordinal: Int): BytesUnit? = BytesUnit.values().firstOrNull { it.ordinal == ordinal }

    }

    abstract fun toBytes(d: Long): Long
    abstract fun toKilobytes(d: Long): Long
    abstract fun toMegabytes(d: Long): Long
    abstract fun toGigabytes(d: Long): Long
    abstract fun toTerabytes(d: Long): Long

}
