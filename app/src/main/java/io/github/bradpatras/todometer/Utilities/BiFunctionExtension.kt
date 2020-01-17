package io.github.bradpatras.todometer.Utilities

import io.reactivex.functions.BiFunction

class BiFun {
    companion object {
        fun empty(): BiFunction<Any, Any, Unit> {
            return BiFunction { _, _ -> return@BiFunction }
        }
    }
}
