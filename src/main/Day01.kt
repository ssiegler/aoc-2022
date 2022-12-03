package day01

import utils.readInput

data class Reader(val elves: List<List<Int>> = listOf(), val elf: List<Int> = listOf()) {
    fun read(line: String): Reader =
        when {
            line.isBlank() -> Reader(complete())
            else -> Reader(elves, elf + line.toInt())
        }

    fun complete(): List<List<Int>> =
        when {
            elf.isEmpty() -> elves
            else -> elves + listOf(elf)
        }
}

fun part1(input: List<String>): Int = readCalories(input).maxOf { it.sum() }

fun part2(input: List<String>): Int =
    readCalories(input).map { it.sum() }.sortedDescending().take(3).sum()

private fun readCalories(input: List<String>) = input.fold(Reader(), Reader::read).complete()

fun main() {

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
