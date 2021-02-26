package io.github.bradpatras.todometer.utilities

import java.util.function.BiFunction


class BiFun {
    companion object {
        fun empty(): BiFunction<Any, Any, Unit> {
            return BiFunction { _, _ -> return@BiFunction }
        }
    }
}
