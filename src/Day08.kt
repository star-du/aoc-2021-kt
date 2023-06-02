fun main() {
    fun part1(input: List<String>): Int {
        val uniqueLength = listOf(2, 3, 4, 7)
        val countByUniqueLength = { x: List<String> -> x.count { it.length in uniqueLength } }

        return input.asSequence()
            .map { it.split("|")[1].trim() } // Sequence<String>
            .map { it.split(" ") } // Sequence<List<String>>
            .sumOf(countByUniqueLength)
    }

    fun part2(input: List<String>): Int {
        // Decode each seven-segment display
        // contract: all input string is alphabetical sorted
        fun decodeEntry(uniquePatterns: List<String>, outputs: List<String>): Int {
//            println("uniquePatterns $uniquePatterns, outputs: $outputs")
            val candidates = uniquePatterns.toMutableList()

            // helper functions
            fun String.containsAllChars(other: String): Boolean = other.all { this.contains(it) }
            val allChars = "abcdefg".toCharArray()
            fun commonChars(list: List<String>): String {
                return allChars.filter { char -> list.all { it.contains(char) } }.joinToString("")
            }

            // solution
            val eight = candidates.first { it.length == 7 }
            val one = candidates.first{ it.length == 2 }
            val seven = candidates.first{ it.length == 3 }
            val four = candidates.first{ it.length == 4 }
            val three = candidates.first{ (it.length == 5) and it.containsAllChars(one) }
            val nine = candidates.first{ (it.length == 6) and it.containsAllChars(three) }

            val commonsOfFive = commonChars(candidates.filter { it.length == 5 })
            val six = (candidates.filter { (it.length == 6) } - nine).first{ it.containsAllChars(commonsOfFive) }
            val zero = (candidates.filter { it.length == 6 } - setOf(six, nine)).first()
            val five = candidates.first{ (it.length == 5) and six.containsAllChars(it) }
            val two = (candidates.filter { (it.length == 5) } - setOf(three, five)).first()

            // application
            val mapping = mapOf(
                zero to 0, one to 1, two to 2, three to 3, four to 4,
                five to 5, six to 6, seven to 7, eight to 8, nine to 9,
            )

            return outputs.map { mapping[it]!! }.fold(0) { acc: Int, i: Int ->
                acc * 10 + i
            }
        }

        val stringSorter = { s:String -> String(s.toCharArray().apply { sort() }) }
        val stringParser = { s: String -> s.trim().split(" ").map(stringSorter) }
        return input.asSequence()
            .map { it.split("|").map(stringParser) }
            .sumOf { decodeEntry(it[0], it[1]) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

/**
 * Documentation for part2:
0:      1:      2:      3:      4:
aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
gggg    ....    gggg    gggg    ....

5:      6:      7:      8:      9:
aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
gggg    gggg    ....    gggg    gggg

Given:
acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab
Solve:
`Map<String, Int>`
1. acedgfb -> 8
2. ab -> 1, dab -> 7, eafb -> 4
3. length == 5 (2, 3, 5) fbcad -> 3 (includes ab)
4. length == 6 (0, 6, 9) acedgfb -> 9 (includes fbcad)
5. common (all length = 5) is cdf, so cefabd -> 6 (includes cdf), cdfgeb -> 0 (does not include)
6. cdfbe -> 5 (cefabd includes cdfbe), gcdfa -> 2
 */
