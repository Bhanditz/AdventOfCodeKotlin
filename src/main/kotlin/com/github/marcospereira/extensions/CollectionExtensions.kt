package com.github.marcospereira.extensions

import java.util.*

/**
 * Created by francois on 2016-02-09.
 */
inline fun <T, R> Collection<T>.pairMap(transform: (T, T) -> R): List<R> {
    val accumulator = LinkedList<R>()

    val iterator = iterator()
    if (iterator.hasNext()) {
        var current = iterator.next()
        while (iterator.hasNext()) {
            val previous = current
            current = iterator.next()
            accumulator += transform(previous, current)
        }
    }
    return accumulator
}