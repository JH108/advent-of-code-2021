package day06

data class LanternFish(
    val timeUntilNextSpawn: Int,
    val descendants: List<LanternFish> = listOf()
)

fun LanternFish.growOlder(): LanternFish {
    val timeToSpawn = timeUntilNextSpawn - 1 < 0
    val olderDescendants = descendants.map { it.growOlder() }

    return if (timeToSpawn) copy(
        timeUntilNextSpawn = 6,
        descendants = olderDescendants + listOf(LanternFish(timeUntilNextSpawn = 8))
    ) else copy(
        timeUntilNextSpawn = timeUntilNextSpawn - 1,
        descendants = olderDescendants
    )
}

fun LanternFish.countDescendants(): Int =
    descendants.size + descendants.sumOf { it.countDescendants() }
