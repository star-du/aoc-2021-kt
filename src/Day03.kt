fun main() {
    fun mostCommonBinaryString(input: List<String>, width: Int): String {
        val mostCommon = StringBuilder(width)
        for (i in 0 until width) {
            var ones = 0
            for (row in input) {
                if (row[i] == '1') {
                    ones += 1
                } else {
                    ones -= 1
                }
            }
            val mostCommonCharInColumn: Char =
                if (ones >= 0) '1' else '0'
            mostCommon.append(mostCommonCharInColumn)
        }

        return mostCommon.toString()
    }

    fun leastCommonBinaryString(input: List<String>, width: Int): String {
        val leastCommon = StringBuilder(width)
        for (i in 0 until width) {
            var zeros = 0
            for (row in input) {
                if (row[i] == '1') {
                    zeros += 1
                } else {
                    zeros -= 1
                }
            }
            val leastCommonCharInColumn: Char =
                if (zeros >= 0) '0' else '1'
            leastCommon.append(leastCommonCharInColumn)
        }

        return leastCommon.toString()
    }

    fun findClosest(candidate: List<String>, target: String): String {
        val candidateSimilarityList = candidate.associateBy { s ->
            var score = 0
            for (i in target.indices) {
                if (target[i] == s[i]) {
                    score += 1
                } else {
                    break
                }
            }
            score
        }

        return candidateSimilarityList
            .maxWithOrNull(compareBy { it.key })
            ?.value
            ?: ""
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

        val myComparator = compareBy<Map.Entry<Char, Int>> { it.value }.thenBy { it.key == '1' }

        var mostCommonWinner = input
        for (i in input[0].indices) {
            val charWithCount: Map<Char, Int> =
                mostCommonWinner.map { it[i] }.groupingBy { it }.eachCount()
            val mostCommonChar = charWithCount.maxWith(myComparator).key
            mostCommonWinner = mostCommonWinner.filter { it[i] == mostCommonChar }
            if (mostCommonWinner.size == 1) break
        }
        println(mostCommonWinner[0])
        val oxygenGeneratorRating = Integer.parseInt(mostCommonWinner[0], 2)

        var leastCommonWinner = input
        for (i in input[0].indices) {
            val charWithCount: Map<Char, Int> =
                leastCommonWinner.map { it[i] }.groupingBy { it }.eachCount()
            val leastCommonChar = charWithCount.minWith(myComparator).key
            leastCommonWinner = leastCommonWinner.filter { it[i] == leastCommonChar }
            if (leastCommonWinner.size == 1) break
        }
        println(leastCommonWinner[0])
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
