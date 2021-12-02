package day02

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

data class Position(
    val useAim: Boolean,
    val horizontal: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0
)

fun List<Command>.calculateFinalPosition(initialPosition: Position) = this
    .fold(initialPosition) { current, (direction, distance) ->
        current.processCommand(
            direction = direction,
            distance = distance
        )
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

fun Position.increaseDepthByDistance(distance: Int) = this.copy(depth = this.depth + distance)

fun Position.decreaseDepthByDistance(distance: Int) = this.copy(depth = this.depth - distance)

fun Position.increaseAimByDistance(distance: Int) = this.copy(aim = this.aim + distance)

fun Position.decreaseAimByDistance(distance: Int) = this.copy(aim = this.aim - distance)

fun Position.increaseDepthByAimTimesDistance(distance: Int) = this.copy(depth = this.depth + (this.aim * distance))

fun Position.increaseHorizontalByDistance(distance: Int) = this.copy(horizontal = this.horizontal + distance)

fun Position.plannedCourse() = this.horizontal * this.depth

enum class Direction {
    Forward,
    Down,
    Up
}