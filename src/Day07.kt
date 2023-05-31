import kotlin.math.abs

fun main() {
    fun leastDistance(positions: List<Int>): Int {
        // first half of the list, floor to integer
        val half = positions.size / 2
        var sum = 0
        for (i in 0 until half) {
            sum += positions[positions.lastIndex - i] - positions[i]
        }

        return sum
    }

    /* Almost brute-force, since I cannot think of a better way with math or dp;
    Use an array to avoid repeated computation. */
    fun leastDistance2(positions: List<Int>): Int {
        val costs = IntArray(positions.last() - positions.first() + 1) { it*(it+1)/2 }
        val sumOfCosts = { x:Int -> positions.sumOf { costs[abs(x - it)] } }
        return (positions.first()..positions.last())
            .minOf(sumOfCosts)
    }

    fun part1(input: List<String>): Int {
        return input[0].split(",")
            .map { it.toInt() }
            .sorted()
            .let { leastDistance(it) }
    }

    fun part2(input: List<String>): Int {
        return input[0].split(",")
            .map { it.toInt() }
            .sorted()
            .let { leastDistance2(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
