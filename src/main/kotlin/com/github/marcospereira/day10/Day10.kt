package com.github.marcospereira.day10

import com.github.marcospereira.Day

/**
 * Created by francois on 2016-02-21.
 */
class Day10 : Day() {
    val pattern = "((\\d)(\\2*))".toPattern(0)
    val challenge = "3113322113"

    override fun part1(): Any? {
        var answer = challenge

        repeat(40, {
            val matcher = pattern.matcher(answer)
            var newAnswer = ""
            while (matcher.find()) {
                val group = matcher.group()
                val numDigits = group.length
                val digit = group[0].toString()

                newAnswer += numDigits
                newAnswer += digit
            }
            answer = newAnswer
        })

        return answer.length
    }

    override fun part2(): Any? {
        throw UnsupportedOperationException()
    }
}