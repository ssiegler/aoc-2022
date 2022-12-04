package day04

import utils.readInput

fun part1(filename: String): Int =
    readInput(filename).map(String::pairOfAssignments).count { (first, second) ->
        second in first || first in second
    }

fun part2(filename: String): Int =
    readInput(filename).map(String::pairOfAssignments).count(Pair<Assignment, Assignment>::overlap)

private fun Pair<Assignment, Assignment>.overlap() =
    first.first <= second.last && second.first <= first.last

typealias Assignment = IntRange

private operator fun Assignment.contains(other: Assignment) =
    contains(other.first) && contains(other.last)

private fun String.assignment(): Assignment = split("-").map { it.toInt() }.run { first()..last() }

private fun String.pairOfAssignments(): Pair<Assignment, Assignment> =
    split(",").map { it.assignment() }.run { first() to last() }

private const val filename = "Day04"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
