package com.github.marcospereira.day10

import com.github.marcospereira.Day

/**
 * Created by francois on 2016-02-21.
 */
class Day10 : Day() {
    val pattern = "((\\d)(\\2*))".toPattern(0)
    val challenge = "3113322113"

    override fun part1(): Any? {
        return lookAndSay(challenge, 40).length
    }

    override fun part2(): Any? {
        return lookAndSay(challenge, 50).length
    }

    fun lookAndSay(input: String, iterations: Int): String {
        var answer = input
        repeat(iterations, { index ->
            val time = System.nanoTime()
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
            val deltaTime = System.nanoTime() - time
            println("Iteration: $index, Current length: ${answer.length}, Took ${deltaTime / 1000000000f} s")
        })
        return answer
    }
}