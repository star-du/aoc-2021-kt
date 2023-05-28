fun main() {
    data class Point(
        val x: Int,
        val y: Int,
    )


    data class Segment(
        val start: Point,
        val end: Point,
    ) {
        fun asPointSequence(): Sequence<Point> {
            return sequenceOf(start, end)
        }

        fun containsPoint(point: Point): Boolean {
            return when {
                point.x == start.x -> point.y in (start.y..end.y)
                point.y == start.y -> point.x in (start.x..end.x)
                else -> false
            }
        }

        fun containsPoint2(point: Point): Boolean {
            /*
            * y2 - y1 / x2 - x1 = y - y1 / x - x1
            * with math manipulation
            * */

            val yRange = if (start.y < end.y) (start.y..end.y) else (end.y..start.y)
            if (point.y !in yRange) return false
            val xRange = if (start.x < end.x) (start.x..end.x) else (end.x..start.x)
            if (point.x !in xRange) return false

            return (end.y - start.y) * (point.x - start.x) == (point.y - start.y) * (end.x - start.x)
        }
    }

    fun String.asPosition(): Point {
        val l = this.split(",", limit = 2)
        return Point(l[0].toInt(), l[1].toInt())
    }

    fun part1(input: List<String>): Int {
        val segments = input.asSequence()
            .map { it.split("->", limit = 2) }
            .map { line ->
                assert(line.size == 2)
                val first = line[0].trim().asPosition()
                val second = line[1].trim().asPosition()
                return@map when {
                    first.x == second.x -> if (first.y < second.y) Segment(first, second) else Segment(second, first)
                    first.y == second.y -> if (first.x < second.x) Segment(first, second) else Segment(second, first)
                    else -> null
                }
            }
            .filterNotNull()
            .toList()
//            .also { println(it) }

        var xMax = 0
        var yMax = 0
        segments
            .flatMap { it.asPointSequence() }
            .let {
                xMax = it.maxOf { p -> p.x }
                yMax = it.maxOf { p -> p.y }
            }

        val allCandidates = sequence {
            for (x in (0..xMax)) {
                for (y in (0..yMax)) {
                    yield(Point(x, y))
                }
            }
        }

        println("xMax = $xMax, yMax = $yMax")

        return allCandidates.map { point ->
            segments.count { it.containsPoint(point) }
//                .also { count -> println("Point $point with count $count") }
        }
            .filter { it >= 2 }
            .count()
    }

    fun part2(input: List<String>): Int {
        val segments = input.asSequence()
            .map { it.split("->", limit = 2) }
            .map { line ->
                assert(line.size == 2)
                val first = line[0].trim().asPosition()
                val second = line[1].trim().asPosition()
                Segment(first, second)
            }
            .toList()
//            .also { println(it) }

        var xMax = 0
        var yMax = 0
        segments
            .flatMap { it.asPointSequence() }
            .let {
                xMax = it.maxOf { p -> p.x }
                yMax = it.maxOf { p -> p.y }
            }

        println("xMax = $xMax, yMax = $yMax")

        val allCandidates = sequence {
            for (x in (0..xMax)) {
                for (y in (0..yMax)) {
                    yield(Point(x, y))
                }
            }
        }

        return allCandidates.map { point ->
            segments.count { it.containsPoint2(point) }
//                .also { count -> println("Point $point with count $count") }
        }
            .filter { it >= 2 }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
