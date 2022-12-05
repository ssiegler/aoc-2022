package day05

import utils.readInput
import java.util.*

typealias Stacks = List<Deque<Char>>

fun List<String>.stacks(): Stacks {
    val lines = takeWhile { it.isNotBlank() }
    val numberOfStacks = lines.last().splitToSequence(" +".toRegex()).last().toInt()
    val stacks = (1..numberOfStacks).map { ArrayDeque<Char>() }
    lines.reversed().drop(1).forEach {
        it.chunked(4).withIndex().forEach { indexedValue ->
            val crate = indexedValue.value[1]
            if (crate != ' ') {
                stacks[indexedValue.index].push(crate)
            }
        }
    }
    return stacks
}

fun List<String>.instructions(): List<String> = subList(indexOfFirst { it.isBlank() } + 1, size)

fun List<String>.rearrange(stacks: Stacks, step: (Int, Deque<Char>, Deque<Char>) -> Unit) =
    forEach {
        val (count, source, destination) =
            """move (\d+) from (\d+) to (\d+)""".toRegex().matchEntire(it)?.destructured
                ?: error("Invalid instruction: $it")
        step(count.toInt(), stacks[source.toInt() - 1], stacks[destination.toInt() - 1])
    }

fun part1(filename: String) =
    filename.process { count, source, destination ->
        repeat(count) { _ -> destination.push(source.pop()) }
    }

private fun String.process(step: (Int, Deque<Char>, Deque<Char>) -> Unit): String {
    val input = readInput(this)
    val stacks = input.stacks()
    input.instructions().rearrange(stacks, step)
    return stacks.tops()
}

fun part2(filename: String) =
    filename.process { count, source, destination ->
        val buffer = ArrayDeque<Char>()
        repeat(count) { _ -> buffer.push(source.pop()) }
        repeat(count) { _ -> destination.push(buffer.pop()) }
    }

private fun Stacks.tops(): String = joinToString("") { "${it.peek()}" }

private const val filename = "Day05"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
