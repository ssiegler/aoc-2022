package day21

import utils.readInput

sealed interface Job

data class Number(val value: Long) : Job

data class Wait(val left: String, val right: String, val operation: (Long, Long) -> Long) : Job

data class Monkeys(private val jobs: Map<String, Job>) {
    fun yell(name: String): Long =
        when (val job = jobs[name]) {
            is Number -> job.value
            is Wait -> with(job) { operation(yell(left), yell(right)) }
            null -> error("No monkey named $name")
        }
}

fun readJobs(filename: String): Monkeys =
    readInput(filename)
        .associate {
            val (name, job) = it.split(": ".toRegex())
            name to job.readJob()
        }
        .let(::Monkeys)

private fun String.readJob(): Job {
    val parts = split(" ")
    return if (parts.size == 1) {
        Number(parts[0].toLong())
    } else {
        Wait(parts[0], parts[2], parts[1].toOperation())
    }
}

private fun String.toOperation(): (Long, Long) -> Long =
    when (this) {
        "+" -> Long::plus
        "-" -> Long::minus
        "*" -> Long::times
        "/" -> Long::div
        else -> error("Unknown operation $this")
    }

private const val filename = "Day21"

fun part1(filename: String) = readJobs(filename).yell("root")

fun main() {
    println(part1(filename))
}
