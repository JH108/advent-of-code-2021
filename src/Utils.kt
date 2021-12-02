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
    command.split(" ").let { (direction, distance) ->
        Command(
            direction = when (direction) {
                "forward" -> Direction.Forward
                "down" -> Direction.Down
                "up" -> Direction.Up
                else -> throw Exception("Not a valid direction")
            },
            distance = distance.toInt()
        )
    }
}

/**
 * Contains the Command for determining where the submarine is headed
 */
data class Command(val direction: Direction, val distance: Int)

fun List<Command>.calculateFinalPosition(initialPosition: Position) = this
    .fold(initialPosition) { current, (direction, distance) ->
        current.processCommand(
            direction = direction,
            distance = distance
        )
    }

data class Position(
    val useAim: Boolean,
    val horizontal: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0
)

fun Position.increaseDepthByDistance(distance: Int) = this.copy(depth = this.depth + distance)

fun Position.decreaseDepthByDistance(distance: Int) = this.copy(depth = this.depth - distance)

fun Position.increaseAimByDistance(distance: Int) = this.copy(aim = this.aim + distance)

fun Position.decreaseAimByDistance(distance: Int) = this.copy(aim = this.aim - distance)

fun Position.increaseDepthByAimTimesDistance(distance: Int) = this.copy(depth = this.depth + (this.aim * distance))

fun Position.increaseHorizontalByDistance(distance: Int) = this.copy(horizontal = this.horizontal + distance)

fun Position.processCommandWithAim(direction: Direction, distance: Int) = when (direction) {
    Direction.Forward -> this
        .increaseHorizontalByDistance(distance)
        .increaseDepthByAimTimesDistance(distance)
    Direction.Down -> this.increaseAimByDistance(distance)
    Direction.Up -> this.decreaseAimByDistance(distance)
}

fun Position.processCommandWithoutAim(direction: Direction, distance: Int) = when (direction) {
    Direction.Forward -> this.increaseHorizontalByDistance(distance)
    Direction.Down -> this.increaseDepthByDistance(distance)
    Direction.Up -> this.decreaseDepthByDistance(distance)
}

fun Position.processCommand(direction: Direction, distance: Int) = if (useAim) {
    this.processCommandWithAim(
        direction = direction,
        distance = distance
    )
} else {
    this.processCommandWithoutAim(
        direction = direction,
        distance = distance
    )
}

fun Position.plannedCourse() = this.horizontal * this.depth

enum class Direction {
    Forward,
    Down,
    Up
}
