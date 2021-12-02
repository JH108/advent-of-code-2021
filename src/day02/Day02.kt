package day02

import utils.*

const val testInputFilename = "day02/Day02_test"
const val inputFilename = "day02/Day02"

fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.toCommands()

        return Position(0, 0).calculateCourseWithoutAim(commands)
    }

    fun part2(input: List<String>): Int {
        val commands = input.toCommands()

        return Position(0, 0, 0).calculateCourseWithAim(commands)
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 150
    val part1Answer = 1989265

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = 900
    val part2Answer = 2089174012

    check(part2(testInput) == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}