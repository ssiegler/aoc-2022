package day15

import geometry.Position
import geometry.manhattenDistance
import geometry.x
import geometry.y
import utils.readInput
import kotlin.math.absoluteValue

data class Sensor(val position: Position, val beacon: Position) {
    private val radius = beacon.manhattenDistance(position)
    val left = position.x - radius
    val right = position.x + radius

    fun detected(other: Position): Boolean {
        return other != beacon && covers(other)
    }

    fun slice(y: Int): IntRange? {
        val halfWidth = radius - (y - position.y).absoluteValue
        return if (halfWidth >= 0) position.x - halfWidth..position.x + halfWidth else null
    }

    fun covers(other: Position) = other.manhattenDistance(position) <= radius
}

fun String.toSensor(): Sensor {
    val (xS, yS, xB, yB) =
        "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)"
            .toRegex()
            .matchEntire(this)
            ?.groupValues
            ?.drop(1)
            ?.map(String::toInt)
            ?: error("Invalid sensor: $this")

    return Sensor(xS to yS, xB to yB)
}

fun List<Sensor>.detected(y: Int): Int =
    (minOf { it.left }..maxOf { it.right }).count { x -> any { sensor -> sensor.detected(x to y) } }

fun part1(filename: String): Int = countDetected(filename, 2000000)

fun countDetected(filename: String, y: Int) = readInput(filename).map(String::toSensor).detected(y)

private fun List<Sensor>.findMissingBeacon(range: IntRange): Position? {
    for (y in range) {
        val slices =
            mapNotNull { it.slice(y) }.sortedWith(compareBy(IntRange::first, IntRange::last))

        var candidate = range.first
        for (slice in slices) {
            if (candidate in slice) {
                candidate = slice.last + 1
            }
            if (candidate > range.last) break
        }
        if (candidate in range) return candidate to y
    }
    return null
}

private fun Position.tuningFrequency(): Long = x.toLong() * 4000000L + y.toLong()

fun part2(filename: String): Long = findMissingBeaconFrequency(filename, 0..4000000)

fun findMissingBeaconFrequency(filename: String, range: IntRange) =
    readInput(filename).map(String::toSensor).findMissingBeacon(range)?.tuningFrequency()
        ?: error("No missing beacon found")

private const val filename = "Day15"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
