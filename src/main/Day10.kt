package day10

import utils.readInput

private const val filename = "Day10"

fun part1(filename: String): Int =
    readInput(filename)
        .readProgram()
        .signalStrengths()
        .filterIndexed { index, _ -> index in 20..220 step 40 }
        .sum()

fun part2(filename: String): String = readInput(filename).readProgram().render()

fun main() {
    println(part1(filename))
    println(part2(filename))
}

class Cpu {
    private var x: Int = 1
    private fun execute(instruction: Instruction): Sequence<Cpu.() -> Unit> =
        when (instruction) {
            is Instruction.Addx -> sequenceOf({}, { x += instruction.value })
            is Instruction.Noop -> sequenceOf({})
        }

    fun execute(program: Program): List<Int> =
        listOf(1, 1) +
            program.flatMap(::execute).map { change ->
                change()
                x
            }
}

sealed interface Instruction {
    data class Addx(val value: Int) : Instruction
    object Noop : Instruction
}

typealias Program = List<Instruction>

fun Program.signalStrengths(): Sequence<Int> =
    Cpu().execute(this).asSequence().mapIndexed { index, value -> index * value }

fun List<String>.readProgram(): Program = map { line ->
    when {
        line == "noop" -> Instruction.Noop
        line.startsWith("addx ") -> Instruction.Addx(line.removePrefix("addx ").toInt())
        else -> error("Unknown instruction: $line")
    }
}

fun Program.render(): String {
    val output = (1..6).map { CharArray(40) }
    Cpu().execute(this).asSequence().drop(1).take(240).withIndex().chunked(40).chunked(6).forEach {
        it.withIndex().forEach { (row, values) ->
            values.withIndex().forEach { (position, value) ->
                output[row][position] = pixel(position, value.value)
            }
        }
    }
    return output.joinToString("\n") { it.concatToString() }
}

fun pixel(position: Int, value: Int): Char = if (position in value - 1..value + 1) '#' else '.'
