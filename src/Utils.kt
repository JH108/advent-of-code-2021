package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Converts a List<String> into List<Int>
 */
fun List<String>.toInts() = this.map { it.toInt() }

/**
 * Uses a list of strings following the format `direction x` and turns them into Commands
 */
fun List<String>.toCommands() = this.map { command ->
    command.split(" ").let { commandParts ->
        val direction = when (commandParts.first()) {
            "forward" -> Direction.Forward
            "down" -> Direction.Down
            "up" -> Direction.Up
            else -> throw Exception("Not a valid direction")
        }

        Command(
            direction = direction,
            distance = commandParts.last().toInt()
        )
    }
}

/**
 * Contains the Command for determining where the submarine is headed
 */
data class Command(val direction: Direction, val distance: Int)

data class Position(val horizontal: Int, val depth: Int, val aim: Int = 0)

fun Position.plannedCourse() = this.horizontal * this.depth

enum class Direction {
    Forward,
    Down,
    Up
}
