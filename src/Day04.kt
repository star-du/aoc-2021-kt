fun main() {
    // parse a comma-separated string to list of integer
    fun parseOrder(str: String): List<Int> {
        return str.split(",").map { it.trim().toInt() }
    }

    // parse a 5*5 grid
    fun parseGrid(grid: List<String>): Array<Array<Int>> {
        val rowMapper: (String) -> Array<Int> = { x ->
            x.trim().split("\\s+".toRegex()).map { it.toInt() }.toTypedArray()
        }
        return grid.map(rowMapper).toTypedArray()
    }

    fun mark(element: Int, grid: Array<Array<Int>>): Pair<Int, Int> {
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == element) {
                    grid[i][j] = -1
                    return Pair(i, j)
                }
            }
        }

        return Pair(-1, -1)
    }

    fun checkRow(row: Int, grid: Array<Array<Int>>): Boolean {
        return grid[row].all { it == -1 }
    }

    fun checkColumn(column: Int, grid: Array<Array<Int>>): Boolean {
        return grid.indices.map { grid[it][column] }.all { it == -1 }
    }

    fun part1(input: List<String>): Int {
        val order = parseOrder(input[0])

        var winner: Int = -1
        var bestResult = order.size
        for (i in 1 until input.size step 6) {
            val grid = parseGrid(input.subList(i+1, i+6))
            for (j in 0 until bestResult) {
                val coordinate = mark(order[j], grid)
                if (coordinate.first == -1) continue
                if (checkRow(coordinate.first, grid) or checkColumn(coordinate.second, grid)) {
                    bestResult = j
                    winner = (i - 1) / 6
                    break
                }
            }
        }

        val lastPiece = order[bestResult]
        if (debug) println("winner = $winner, best result = $bestResult, lastPiece = $lastPiece")

        val start = winner * 6 + 1
        val grid = parseGrid(input.subList(start + 1, start + 6))
        for (i in 0..bestResult) {
            mark(order[i], grid)
        }
        val unmarked = grid.flatten().filter { it != -1 }.sum()
        if (debug) println("unmarked = $unmarked")
        return lastPiece * unmarked
    }

    fun part2(input: List<String>): Int {
        val order = parseOrder(input[0])

        var loser: Int = -1
        var worstResult = 0
        for (i in 1 until input.size step 6) {
            val grid = parseGrid(input.subList(i+1, i+6))
            var found = false
            for (j in order.indices) {
                val coordinate = mark(order[j], grid)
                if (coordinate.first == -1) continue
                if (checkRow(coordinate.first, grid) or checkColumn(coordinate.second, grid)) {
                    if (j > worstResult) {
                        worstResult = j
                        loser = (i - 1) / 6
                    }
                    found = true
                    break
                }
            }
            if (!found) {
                worstResult = order.size - 1
                loser = (i - 1) / 6
            }
        }
        val lastPiece = order[worstResult]
        if (debug) println("loser = $loser, worst result = $worstResult, lastPiece = $lastPiece")

        val start = loser * 6 + 1
        val grid = parseGrid(input.subList(start + 1, start + 6))
        for (i in 0..worstResult) {
            mark(order[i], grid)
        }
        val unmarked = grid.flatten().filter { it != -1 }.sum()
        if (debug) println("unmarked = $unmarked")

        return lastPiece * unmarked
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
