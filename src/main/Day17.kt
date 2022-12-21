package day17

import geometry.Direction
import geometry.Direction.Left
import geometry.Direction.Right
import utils.inputFile
import java.nio.file.Files
import kotlin.experimental.and
import kotlin.experimental.or

fun jetPatterns(filename: String) =
    Files.readString(inputFile(filename)).map {
        when (it) {
            '<' -> Left
            '>' -> Right
            else -> error("Unknown jet pattern $it")
        }
    }

typealias Pile = ArrayDeque<Byte>

typealias Rock = ByteArray

private infix fun Byte.shl(amount: Int) = toInt().shl(amount).toByte()

private infix fun Byte.shr(amount: Int) = toInt().shr(amount).toByte()

private val shapes =
    listOf(
        {
            byteArrayOf(
                0b0011110,
            )
        },
        {
            byteArrayOf(
                0b0001000,
                0b0011100,
                0b0001000,
            )
        },
        {
            byteArrayOf(
                0b0000100,
                0b0000100,
                0b0011100,
            )
        },
        {
            byteArrayOf(
                0b0010000,
                0b0010000,
                0b0010000,
                0b0010000,
            )
        },
        {
            byteArrayOf(
                0b0011000,
                0b0011000,
            )
        }
    )

class Cave(jetPatterns: Collection<Direction>) {
    private val jetCount = jetPatterns.size
    private val shapeCount = shapes.size
    private val movements = generateSequence { jetPatterns }.flatten().iterator()
    private val rocks = generateSequence { shapes }.flatten().map { it() }.iterator()

    private val pile: Pile = Pile()

    val height
        get() = pile.size

    val profile
        get() = Triple(depths, rockIndex, jetIndex)

    private val depths: List<Int>
        get() {
            val depths: Array<Int?> = arrayOfNulls(7)
            for ((depth, row) in pile.withIndex()) {
                val unsetIndices = depths.withIndex().filter { it.value == null }.map { it.index }
                if (unsetIndices.isEmpty()) break
                for (column in unsetIndices) {
                    if (row.toInt() and (0b1000000 shr column) == 0) {
                        depths[column] = depth
                    }
                }
            }
            return depths.map { it ?: pile.size }
        }
    private var rockIndex = 0
    private var jetIndex = 0

    fun dropNext() {
        val rock = rocks.next()
        rockIndex = (rockIndex + 1) % shapeCount
        repeat(rock.size + 3) { pile.addFirst(0) }
        for (level in pile.indices) {
            val direction = movements.next()
            jetIndex = (jetIndex + 1) % jetCount
            rock.push(direction, level)
            if (rock.stopsFalling(level)) {
                rock.addToPile(level)
                break
            }
        }
    }

    private fun Rock.push(direction: Direction, level: Int) {
        when (direction) {
            Left -> push(level, 0b1000000) { it shl 1 }
            Right -> push(level, 0b0000001) { it shr 1 }
            else -> error("Cannot push to $direction")
        }
    }

    private fun Rock.push(level: Int, wallMask: Byte, shift: (Byte) -> Byte) {
        val shifted = map(shift)
        if (
            all { row -> row and wallMask == 0.toByte() } &&
                shifted.zip(pile.drop(level)).all { (rockRow, pileRow) ->
                    rockRow and pileRow == 0.toByte()
                }
        ) {
            indices.forEach { row -> set(row, shifted[row]) }
        }
    }

    private fun Rock.stopsFalling(level: Int): Boolean =
        level >= pile.size - size ||
            asSequence().zip(pile.asSequence().drop(level + 1)).any { (rockRow, pileRow) ->
                rockRow and pileRow != 0.toByte()
            }

    private fun Rock.addToPile(level: Int) {
        withIndex().forEach { (i, r) -> pile[level + i] = pile[level + i] or r }
        while (pile[0] == 0.toByte()) {
            pile.removeFirst()
        }
    }

    fun visualize(): String = buildString {
        for (row in pile) {
            append('|')
            for (column in 6 downTo 0) {
                append(
                    if (row.toInt() shr column and 1 == 1) {
                        '#'
                    } else {
                        '.'
                    }
                )
            }
            append("|\n")
        }
        append("+-------+\n")
    }
}

fun cave(filename: String) = Cave(jetPatterns(filename))

fun part1(filename: String) =
    with(cave(filename)) {
        repeat(2022) { dropNext() }
        height
    }

fun part2(filename: String): Long {
    var tortoise = cave(filename).apply { dropNext() }
    val hare =
        cave(filename).apply {
            dropNext()
            dropNext()
        }
    while (tortoise.profile != hare.profile) {
        tortoise.dropNext()
        hare.dropNext()
        hare.dropNext()
    }

    tortoise = cave(filename)
    var mu = 0
    while (tortoise.profile != hare.profile) {
        tortoise.dropNext()
        hare.dropNext()
        mu += 1
    }
    val startHeight = tortoise.height

    var lam = 1
    val fixed = tortoise.profile
    tortoise.dropNext()
    while (fixed != tortoise.profile) {
        tortoise.dropNext()
        lam += 1
    }
    val cycleHeight = tortoise.height - startHeight

    val inCycles = 1000000000000 - mu
    val cycles = inCycles / lam
    val remaining = inCycles % lam

    repeat(remaining.toInt()) { tortoise.dropNext() }
    return tortoise.height + (cycles - 1) * cycleHeight
}

private const val filename = "Day17"

fun main() {
    println(part1(filename))
    println(part2(filename))
}
