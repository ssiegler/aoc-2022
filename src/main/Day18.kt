package day18

import utils.readInput

fun readCubes(filename: String) =
    readInput(filename)
        .map {
            val (x, y, z) = it.split(",").map { it.toInt() }
            V3(x, y, z)
        }
        .toSet()

typealias V3 = Triple<Int, Int, Int>

fun Set<V3>.surface(): Int = flatMap(V3::neighbors).count { it !in this }

private fun V3.neighbors() =
    listOf(
        copy(first = first - 1),
        copy(first = first + 1),
        copy(second = second - 1),
        copy(second = second + 1),
        copy(third = third - 1),
        copy(third = third + 1),
    )

fun Set<V3>.exteriorSurface(): Int {
    val boundingBox =
        BoundingBox(
            minOf { it.first } - 1..maxOf { it.first } + 1,
            minOf { it.second } - 1..maxOf { it.second } + 1,
            minOf { it.third } - 1..maxOf { it.third } + 1
        )
    val seen = mutableSetOf<V3>()
    val queue = ArrayDeque<V3>()
    queue.addLast(boundingBox.first())
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val neighbors = current.neighbors().filter { it in boundingBox } - this - seen
        seen.addAll(neighbors)
        queue.addAll(neighbors)
    }

    return flatMap(V3::neighbors).count(seen::contains)
}

typealias BoundingBox = Triple<IntRange, IntRange, IntRange>

private fun BoundingBox.first() = V3(first.first, second.first, third.first)

operator fun BoundingBox.contains(point: V3): Boolean =
    point.first in first && point.second in second && point.third in third

fun part1(filename: String) = readCubes(filename).surface()

fun part2(filename: String) = readCubes(filename).exteriorSurface()

private const val filename = "Day18"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
