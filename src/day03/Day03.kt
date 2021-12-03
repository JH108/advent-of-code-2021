package utils.day03

import utils.readInput

const val testInputFilename = "day03/Day03_test"
const val inputFilename = "day03/Day03"

fun main() {
    fun part1(input: List<String>): Int {
        var countOfBits = mapOf<Int, BitCounts>()

        input.forEach { binary ->
            binary.forEachIndexed { index, bit ->
                    val currentCount = countOfBits[index]

                    countOfBits = if (currentCount != null) {
                        val newCount =
                            if (bit.toString().toInt() == 0) currentCount.copy(zero = currentCount.zero + 1)
                            else currentCount.copy(one = currentCount.one + 1)

                        countOfBits + mapOf(index to newCount)
                    } else {
                        val newCount =
                            if (bit.toString().toInt() == 0) BitCounts(zero = 1) else BitCounts(
                                one = 1
                            )

                        countOfBits + mapOf(index to newCount)
                    }
                }
        }

        val gammaBin = countOfBits.toList().joinToString("") { (_, bitCount) ->
            if (bitCount.one > bitCount.zero) "1" else "0"
        }
        val epsilonBin = countOfBits.toList().joinToString("") { (_, bitCount) ->
            if (bitCount.one < bitCount.zero) "1" else "0"
        }

        println("Gamma Binary: $gammaBin")
        println("Epsilon Binary: $epsilonBin")

        val gamma = gammaBin.toInt(2)
        val epsilon = epsilonBin.toInt(2)

        println("Gamma: $gamma")
        println("Epsilon: $epsilon")

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput(testInputFilename)
    val input = readInput(inputFilename)

    val part1TestAnswer = 198
    val part1Answer = 2250414

    check(part1(testInput) == part1TestAnswer)

    val part1Result = part1(input)

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    val part2TestAnswer = null
    val part2Answer = null

    check(part2(testInput) == part2TestAnswer)

    val part2Result = part2(input)
    println("Part2: $part2Result")

    check(part2Result == part2Answer)
}