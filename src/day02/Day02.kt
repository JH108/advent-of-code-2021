package day02

import utils.*

const val testInputFilename = "day02/Day02_test"
const val inputFilename = "day02/Day02"

fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.toCommands()
        val initialPosition = Position(0, 0)

        // Turn the commands into a final position
        val position = commands.fold(initialPosition) { current, command ->
            when (command.direction) {
                Direction.Forward -> current.copy(
                    horizontal = current.horizontal + command.distance
                )
                Direction.Down -> current.copy(
                    depth = current.depth + command.distance
                )
                Direction.Up -> current.copy(
                    // Do we need to account for a negative depth?
                    depth = current.depth - command.distance
                )
            }
        }

        return position.plannedCourse()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(testInputFilename)
    check(part1(testInput) == 150)

    val input = readInput(inputFilename)
    val part1Result = part1(input)
    val part1Answer = 1989265

    println("Part1: $part1Result")

    check(part1Result == part1Answer)

    check(part2(testInput) == 5)

    val part2Result = part2(input)
    println("Part2: $part2Result")
}