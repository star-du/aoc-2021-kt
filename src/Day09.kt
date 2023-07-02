fun main() {
    fun part1(input: List<String>): Int {
        val heightMap = input.map { row -> row.toList().map(Char::digitToInt) }
        val xRange = heightMap.indices
        val yRange = heightMap[0].indices

        var sum = 0
        for (x in xRange) {
            for (y in yRange) {
                val element = heightMap[x][y]
                val predicates = sequenceOf(
                    (x - 1 in xRange) && (heightMap[x - 1][y] <= element),
                    (x + 1 in xRange) && (heightMap[x + 1][y] <= element),
                    (y - 1 in yRange) && (heightMap[x][y - 1] <= element),
                    (y + 1 in yRange) && (heightMap[x][y + 1] <= element),
                )

                if (predicates.any { it }) continue

                sum += element + 1
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        data class Point(val x: Int, val y: Int)

        val nine = 9 // non-basin point
        val heightMap = input.map { row -> row.toList().map(Char::digitToInt) }
        val xRange = heightMap.indices
        val yRange = heightMap[0].indices

        val helperMap = heightMap.map { it.toMutableList() }.toMutableList()
        val sizes = mutableListOf<Int>() // can be optimized with max heap

        for (x in xRange) {
            for (y in yRange) {
                if (helperMap[x][y] == nine) continue

                var size = 0
                val deque = ArrayDeque<Point>()
                deque.addFirst(Point(x, y))
//                println("deque add First = $deque")

                while (deque.isNotEmpty()) {
                    val p = deque.removeFirst()
//                    println("p = $p")
                    if (helperMap[p.x][p.y] == nine) continue

                    size += 1
                    helperMap[p.x][p.y] = nine

                    if ((p.x - 1 in xRange) && (helperMap[p.x - 1][p.y] != nine)) deque.addLast(Point(p.x - 1, p.y))
                    if ((p.x + 1 in xRange) && (helperMap[p.x + 1][p.y] != nine)) deque.addLast(Point(p.x + 1, p.y))
                    if ((p.y - 1 in yRange) && (helperMap[p.x][p.y - 1] != nine)) deque.addLast(Point(p.x, p.y - 1))
                    if ((p.y + 1 in yRange) && (helperMap[p.x][p.y + 1] != nine)) deque.addLast(Point(p.x, p.y + 1))
                }

//                println("Point($x, $y) size = $size")
                sizes += size
            }
        }

        return sizes.asSequence()
            .sortedDescending()
            .take(3)
            .fold(1) { result, element -> result * element }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
