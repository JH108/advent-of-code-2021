package utils.day03

import utils.readInput

const val testInputFilename = "day03/Day03_test"
const val inputFilename = "day03/Day03"

fun main() {
    fun part1(input: List<String>): Int {
        val grid = BinaryGrid(input)
        val columnBitCounts = grid.part1(input)

        val gammaBin = columnBitCounts.toList().joinToString("") { (_, bitCount) ->
            if (bitCount.one > bitCount.zero) "1" else "0"
        }
        val epsilonBin = columnBitCounts.toList().joinToString("") { (_, bitCount) ->
            if (bitCount.one < bitCount.zero) "1" else "0"
        }

        val gamma = gammaBin.toInt(2)
        val epsilon = epsilonBin.toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val grid = BinaryGrid(input)

        return grid.part2(input)
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 198
    val part1Answer = 2250414

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = 230
    val part2Answer = 6085575
    val part2TestResult = part2(testInput)

    check(part2TestResult == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}