fun main() {
    fun mostCommonChar(l: List<Char>): Char {
        var ones = 0
        l.forEach { if (it == '1') ones += 1 else ones -= 1 }
        return if (ones >= 0) '1' else '0'
    }

    fun mostCommonBinaryString(input: List<String>, width: Int): String {
        val mostCommon = StringBuilder(width)
        for (i in 0 until width) {
            val mostCommonCharInColumn = mostCommonChar(input.map { it[i] })
            mostCommon.append(mostCommonCharInColumn)
        }

        return mostCommon.toString()
    }

    fun part1(input: List<String>): Int {
        if (input.isEmpty()) return 0

        val width = input[0].length
        if (width == 0) {
            return 0
        }

        val gammaString = mostCommonBinaryString(input, width)
        val gamma = Integer.parseInt(gammaString, 2)
        if (debug) println("Gamma = ${gamma}, (0b${gammaString})")

        val mask = Integer.parseInt("1".repeat(width), 2)
        val epsilon = gamma.inv() and mask
        if (debug) println("Epsilon = $epsilon")

        return gamma*epsilon
    }

    fun part2(input: List<String>): Int {
        if (input.isEmpty()) return 0

        // Scheme 1
        val candidate = input.toMutableList()
        val indices = input[0].indices

        for (i in indices) {
            val char = mostCommonChar(candidate.map { s -> s[i] })
            candidate.retainAll { it[i] == char }
            if (candidate.size == 1) break
        }

        assert(candidate.size == 1)
        val oxygenGeneratorRating = Integer.parseInt(candidate[0], 2)

        // Scheme 2
        val myComparator = compareBy<Map.Entry<Char, Int>> { it.value }.thenBy { it.key == '1' }
        var leastCommonWinner = input
        for (i in input[0].indices) {
            val charWithCount: Map<Char, Int> =
                leastCommonWinner.map { it[i] }.groupingBy { it }.eachCount()
            val leastCommonChar = charWithCount.minWith(myComparator).key
            leastCommonWinner = leastCommonWinner.filter { it[i] == leastCommonChar }
            if (leastCommonWinner.size == 1) break
        }
        val co2ScrubberRating = Integer.parseInt(leastCommonWinner[0], 2)

        if (debug) println("oxygenGeneratorRating = $oxygenGeneratorRating")
        if (debug) println("co2ScrubberRating = $co2ScrubberRating")
        return oxygenGeneratorRating*co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
