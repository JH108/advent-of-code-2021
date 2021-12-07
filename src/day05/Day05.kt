package day05

import utils.readInput

const val testInputFilename = "day05/Day05_test"
const val inputFilename = "day05/Day05"

fun main() {
    fun part1(input: List<String>): Int {
        val ventDetector = VentDetector.fromCoordinates(input)
        val dangerousAreas = ventDetector.findDangerousAreas()

        return dangerousAreas.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 5
    val part1Answer = 4745

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = 12
    val part2Answer = null
    val part2TestResult = part2(testInput)

    check(part2TestResult == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}