package utils.day04

import utils.readInput

const val testInputFilename = "day04/Day04_test"
const val inputFilename = "day04/Day04"

fun main() {
    fun part1(input: List<String>): Int {
        val submarineBingo = SubmarineBingo(input)

        return submarineBingo.win()
    }

    fun part2(input: List<String>): Int {
        val submarineBingo = SubmarineBingo(input)

        return submarineBingo.lose()
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 4512
    val part1Answer = 69579

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = 1924
    val part2Answer = 14877
    val part2TestResult = part2(testInput)

    check(part2TestResult == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}