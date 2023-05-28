import kotlin.math.abs
import kotlin.math.max

fun main() {
    data class Point(
        val x: Int,
        val y: Int,
    )


    data class Segment(
        val start: Point,
        val end: Point,
    ) {
        val pointSequence: Sequence<Point>
            get() {
                val direction = Point(end.x compareTo start.x, end.y compareTo start.y)
                val n = max(abs(start.x - end.x), abs(start.y - end.y))
                return (0..n).map { Point(start.x + it * direction.x, start.y + it * direction.y) }
                    .asSequence()
            }

        fun isHorizontalOrVertical(): Boolean {
            return (start.x == end.x) or (start.y == end.y)
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
                Segment(first, second)
            }
            .filter { it.isHorizontalOrVertical() }

        return segments.flatMap { it.pointSequence }
            .groupBy { it }
            .mapValues { entry -> entry.value.size }
            .filterValues { it >= 2 }
//            .also { println(it) }
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

        return segments.flatMap { it.pointSequence }
            .groupBy { it }
            .mapValues { entry -> entry.value.size }
            .filterValues { it >= 2 }
//            .also { println(it) }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
