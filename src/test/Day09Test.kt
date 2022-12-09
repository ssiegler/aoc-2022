package day09

import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

private fun Rope.visualize() = buildString {
    for (y in 5 downTo 0) {
        for (x in 0..5) {
            val index = knots.indexOf(x to y)
            append(
                when (index) {
                    -1 -> "."
                    0 -> "H"
                    else -> "$index"
                }
            )
        }
        append("\n")
    }
}

private const val filename = "Day09Example"

@ExtendWith(ApprovalsExtension::class)
class Day09Test {
    @Test
    fun part1() {
        assertEquals(13, part1(filename))
    }

    @Test
    fun `verify part1 visually`(approver: Approver) {
        approver.assertApproved(
            readMoves(filename).simulate().joinToString("\n") { it.visualize() }
        )
    }
}
