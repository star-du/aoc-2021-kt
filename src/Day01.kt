fun main() {
    fun part1(input: List<String>): Int {
        val measurements = input.mapNotNull { s -> s.toIntOrNull() }
        if (measurements.size <= 1) {
            return 0
        }

        var count = 0
        for (i in 0 until measurements.size - 1) {
            if (measurements[i + 1] > measurements[i]) {
                count += 1
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val windowSize = 3
        val measurements = input.mapNotNull { s -> s.toIntOrNull() }
        if (measurements.size <= windowSize) {
            return 0
        }

        var count = 0
        for (i in 0 until measurements.size - windowSize) {
            if (measurements[i + 3] > measurements[i]) {
                count += 1
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
