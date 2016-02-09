package com.github.marcospereira.day9

import com.github.marcospereira.Day
import com.github.marcospereira.extensions.pairMap
import java.util.*

/**
 * Created by francois on 2016-02-09.
 */
class Day9 : Day() {
    val pattern = "(.*)\\sto\\s(.*)\\s=\\s(\\d*)".toPattern()

    val allRoutes by lazy {
        file.readLines().map {
            val matcher = pattern.matcher(it)
            return@map when {
                matcher.matches() -> Route(
                        City(matcher.group(1)),
                        City(matcher.group(2)),
                        matcher.group(3).toInt())
                else -> null
            }
        }.filterNotNull().toList()
    }

    val permutations by lazy {
        val allCities = allRoutes.map { it.sourceCity }.toSet()
        val completePermutations = LinkedList<List<City>>()

        allCities.forEach { startCity ->
            val currentPermutation = LinkedList<City>()
            currentPermutation += startCity

            getPermutation(currentPermutation, completePermutations)
        }

        completePermutations.map {
            it.pairMap { cityA, cityB ->
                allRoutes.filter { it.validFor(cityA, cityB) }.first()
            }
            }
        }

    fun getPermutation(currentPermutation: Deque<City>, completePermutations: MutableList<List<City>>) {
        val currentCity = currentPermutation.last
        val validNextCities = allRoutes
                .filter { it.validFor(currentCity) }
                .map {
                    when (currentCity) {
                        it.sourceCity -> it.destCity
                        else -> it.sourceCity
                    }
                }
                .filter { it !in currentPermutation }
        if (validNextCities.isEmpty()) {
            completePermutations += currentPermutation.toList()
            return
        }
        validNextCities.forEach { nextCity ->
            currentPermutation += nextCity
            getPermutation(currentPermutation, completePermutations)
            currentPermutation.removeLast()
        }
    }

    override fun part1(): Any? {
        return permutations.minBy { it.sumBy { it.distance } }?.sumBy { it.distance }
    }

    override fun part2(): Any? {
        return permutations.maxBy { it.sumBy { it.distance } }?.sumBy { it.distance }
    }
}

data class City(val name: String) {
    override fun toString(): String {
        return name
    }
}

data class Route(val sourceCity: City, val destCity: City, val distance: Int) {
    fun validFor(city: City): Boolean = sourceCity == city || destCity == city

    fun validFor(cityA: City, cityB: City): Boolean {
        return (sourceCity == cityA && destCity == cityB) || (sourceCity == cityB && destCity == cityA)
    }

    override fun toString(): String {
        return "$sourceCity to $destCity = $distance"
    }
}