package day12

import geometry.Direction
import geometry.Position
import geometry.move
import utils.characterPositions
import utils.readInput
import java.util.*

typealias Height = UByte

data class HeightMap(
    private val heights: Map<Position, Height>,
    private val start: Position,
    private val target: Position,
    private val width: Int,
    private val height: Int
) {
    private fun Position.neighbors(): List<Position> =
        Direction.values()
            .map(::move)
            .filter { heights[it] != null }
            .filter { heights[it]!! <= heights[this]!! + 1u }

    fun shortestPathLength(): Int {
        val distances = mutableMapOf(start to 0)
        val boundary =
            PriorityQueue<Position>(Comparator.comparing { distances[it] ?: Int.MAX_VALUE })
        boundary.add(start)
        while (boundary.isNotEmpty()) {
            val next = boundary.remove()
            val distance = distances[next]!! + 1
            for (neighbor in next.neighbors().filter { distances[it] == null }) {
                distances[neighbor] = distance
                boundary.add(neighbor)
            }
        }

        return distances[target] ?: error("No path to target found")
    }
}

fun Char.toHeight(): Height =
    when (this) {
        in 'a'..'z' -> minus('a').toUByte()
        'S' -> 0u
        'E' -> 25u
        else -> error("Unknown height for marker '$this'")
    }

fun List<String>.readHeightMap(): HeightMap {
    val characterPositions = characterPositions
    return HeightMap(
        characterPositions.mapValues { (_, value) -> value.toHeight() },
        characterPositions.entries.find { it.value == 'S' }?.key ?: error("start point not found"),
        characterPositions.entries.find { it.value == 'E' }?.key ?: error("end point not found"),
        maxOf { it.length },
        size
    )
}

fun part1(filename: String): Int = readInput(filename).readHeightMap().shortestPathLength()

private const val filename = "Day12"

fun main() {
    println(part1(filename))
}
