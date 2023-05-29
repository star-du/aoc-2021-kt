const val NEW_BABY_FISH_TIMER = 8
const val NEW_MOTHER_FISH_TIMER = 6
const val MAX_FISH_TIMER = NEW_BABY_FISH_TIMER

fun main() {
    fun totalFishAfterRecursive(days: Int, initial: Int): Long {
        return when {
            days <= initial -> 1
            else -> totalFishAfterRecursive(days - initial - 1, 8) + totalFishAfterRecursive(days - initial - 1, 6)
        }
    }

    fun totalFishAfterMemoization(days: Int, initial: Int, cache: Array<Array<Long>>) : Long {
        if (cache[days][initial] != -1L) {
            return cache[days][initial]
        }

        val result =  when (days <= initial) {
            true -> {
               1L
            }
            else -> {
                val day = (days - initial - 1).coerceAtLeast(0)
                totalFishAfterMemoization(day, NEW_MOTHER_FISH_TIMER, cache) + totalFishAfterMemoization(day, NEW_BABY_FISH_TIMER, cache)
            }
        }
        cache[days][initial] = result
        return result
    }

    fun totalFishAfterTabulation(days: Int, initial: Int): Long {
        /*
        let h = f(x, 0), the result is f(days, initials) = f(days-initials, 0) = h(days-initials)
        h(x + 9) = h(x) + h(x + 2)
        also...
        h(0) = 1
        h(1) = 2
        ...
        h(7) = h(0) + h(-2) = 2
        h(8) = h(1) + h(-1) = 3
        h(9) = h(2) + h(0) = 3
        h(10) = h(3) + h(1) = 4
        */
        fun h(x: Int): Long {
            when (x) {
                0 -> return 1L
                in 1..7 -> return 2L
                8 -> return 3L
            }

            val cache = LongArray(x + 1) { index ->
                when (index) {
                    0 -> 1L
                    in 1..7 -> 2L
                    8 -> 3L
                    else -> 0L
                }
            }

            for (i in 0..x-9) {
                cache[i + 9] = cache[i] + cache[i + 2]
            }

            return cache[x]
        }

        return h(days - initial)
    }

    fun totalFishAfter(days: Int, initial: Int): Long {
        return totalFishAfterTabulation(days, initial)
    }

    fun part1(input: List<String>): Long {
        return input[0].split(",")
            .asSequence()
            .map { it.toInt() }
            .map { totalFishAfter(80, it) }
//            .toList().also { println(it) }
            .sum()
    }

    fun part2(input: List<String>): Long {
        // TODO
        return input[0].split(",")
            .asSequence()
            .map { it.toInt() }
            .map { totalFishAfter(256, it) }
//            .toList().also { println(it) }
            .sum()
    }

    fun testTotalFishAfter() {
        check(totalFishAfter(3, 3) == 1L)
        check(totalFishAfter(4, 3) == 2L)
        check(totalFishAfter(10, 3) == 2L)
        check(totalFishAfter(11, 3) == 3L)
    }

    // test if implementation meets criteria from the description, like:
    testTotalFishAfter()
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
