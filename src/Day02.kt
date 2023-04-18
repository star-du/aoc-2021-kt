fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        input.asSequence().forEach { command ->
            val commandAsList = command.split(" ")
            assert(commandAsList.size == 2)
            val direction = commandAsList[0]
            val distance = commandAsList[1].toInt()
            when (direction) {
                "forward" -> x += distance
                "down" -> y += distance
                "up" -> y -= distance
                else -> throw IllegalArgumentException(direction)
            }
        }
        if (debug) println("x = $x, y = $y")
        return x*y
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0
        input.asSequence().forEach { command ->
            val commandAsList = command.split(" ")
            assert(commandAsList.size == 2)
            val direction = commandAsList[0]
            val distance = commandAsList[1].toInt()
            when (direction) {
                "forward" -> {
                    x += distance
                    y += aim*distance
                }
                "down" -> {
                    aim += distance
                }
                "up" -> {
                    aim -= distance
                }
                else -> throw IllegalArgumentException(direction)
            }
        }
        if (debug) println("x = $x, y = $y")
        return x*y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
