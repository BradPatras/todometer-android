package io.github.bradpatras.todometer.utilities

import io.reactivex.functions.BiFunction

class BiFun {
    companion object {
        fun empty(): BiFunction<Any, Any, Unit> {
            return BiFunction { _, _ -> return@BiFunction }
        }
    }
}
