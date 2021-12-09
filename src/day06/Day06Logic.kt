package day06

import utils.toInts

data class LanternFish(
    val timeUntilNextSpawn: Int,
    val descendants: List<LanternFish> = listOf()
)

fun LanternFish.growByDays(days: Int) = (1..days)
    .fold(this) { lanternFish, _ ->
        lanternFish.growOlder()
    }

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

data class LanternFishInTheOcean(val timeSinceTheBeginning: Int, var fish: LongArray) {
    companion object {
        fun parse(input: List<String>, timeSinceTheBeginning: Int): LanternFishInTheOcean {
            val initialFish = input.toInts().fold(LongArray(9)) { fishIndex, fish ->
                fishIndex.apply {
                    this[fish] += 1L
                }
            }

           return LanternFishInTheOcean(timeSinceTheBeginning, initialFish)
        }
    }
}

fun LanternFishInTheOcean.multiply(): Long {
    return (1..timeSinceTheBeginning).fold(fish) { totalFish, _ ->
        val fishHavingOffspring = totalFish.first()

        totalFish.drop(1).toLongArray().apply { this[6] += fishHavingOffspring } + longArrayOf(fishHavingOffspring)
    }.sum()
}
