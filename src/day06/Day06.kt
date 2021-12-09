package day06

import utils.readInput

const val testInputFilename = "day06/Day06_test"
const val inputFilename = "day06/Day06"

fun main() {
    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = null
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