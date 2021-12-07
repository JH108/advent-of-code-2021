package utils.day04

import utils.readInput
import utils.toInts

const val testInputFilename = "day04/Day04_test"
const val inputFilename = "day04/Day04"

fun main() {
    fun part1(input: List<String>): Int {
        val submarineBingo = SubmarineBingo(input)
        println("calls: ${submarineBingo.calls}")
        println("boards: ${submarineBingo.boards}")

        val result = submarineBingo.play()

        println("Result: $result")

        return result
    }

    fun part2(input: List<String>): Int {

        return 1
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 4512
    val part1Answer = null

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = null
    val part2Answer = null
    val part2TestResult = part2(testInput)

    check(part2TestResult == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}