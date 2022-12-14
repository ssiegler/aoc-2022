package day21

import day21.Operation.*
import utils.readInput
import java.util.*

sealed interface Job

data class Number(val value: Long) : Job

data class Wait(val left: String, val right: String, val operation: Operation) : Job

enum class Operation(val operation: (Long, Long) -> Long) {
    ADD(Long::plus),
    SUB(Long::minus),
    MUL(Long::times),
    DIV(Long::div);

    fun invert() =
        when (this) {
            ADD -> SUB
            SUB -> ADD
            MUL -> DIV
            DIV -> MUL
        }

    operator fun invoke(left: Long, right: Long) = operation(left, right)
}

data class Monkeys(private val jobs: Map<String, Job>) {
    fun yell(name: String): Long =
        when (val job = jobs[name]) {
            is Number -> job.value
            is Wait -> with(job) { operation(yell(left), yell(right)) }
            null -> error("No monkey named $name")
        }

    fun match(): Long {
        val jobs = jobs + ("root" to (jobs["root"] as Wait).copy(operation = SUB))
        val path = findPath("root", "humn") ?: error("No path from 'root' to 'humn'")
        var balance = 0L
        for ((parent, current) in path.windowed(2)) {
            val (left, right, op) = jobs[parent] as Wait
            val other = yell(if (current == left) right else left)
            balance =
                if (current == right && (op == DIV || op == SUB)) {
                    op(other, balance)
                } else {
                    op.invert().invoke(balance, other)
                }
        }
        return balance
    }

    private fun findPath(start: String, end: String): List<String>? {
        val queue = ArrayDeque<List<String>>()
        queue.add(listOf(start))
        while (queue.isNotEmpty()) {
            val path = queue.remove()
            val current = path.last()
            if (current == end) return path
            val job = jobs[current]
            if (job is Wait) {
                queue.add(path + job.left)
                queue.add(path + job.right)
            }
        }
        return null
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

private fun String.toOperation(): Operation =
    when (this) {
        "+" -> ADD
        "-" -> SUB
        "*" -> MUL
        "/" -> DIV
        else -> error("Unknown operation $this")
    }

private const val filename = "Day21"

fun part1(filename: String) = readJobs(filename).yell("root")

fun part2(filename: String) = readJobs(filename).match()

fun main() {
    println(part1(filename))
    println(part2(filename))
}
