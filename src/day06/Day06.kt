package day06

import utils.readInput

const val testInputFilename = "day06/Day06_test"
const val inputFilename = "day06/Day06"

fun main() {
    fun part1(input: List<String>, days: Int = 80): Int {
        var lanternFish = input.flatMap { it.split(",").map { LanternFish(it.toInt()) } }

        for (day in 1..days) {
            lanternFish = lanternFish.map { fish ->
                fish.growOlder()
            }
        }

        val allDescendants = lanternFish.sumOf {
            it.countDescendants()
        }

        return allDescendants + lanternFish.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    check(part1(testInput, 4) == 9)
    check(part1(testInput, 5) == 10)
    check(part1(testInput, 8) == 10)
    check(part1(testInput, 9) == 11)
    check(part1(testInput, 18) == 26)

    val part1TestAnswer = 5934
    val part1Answer = 374927

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