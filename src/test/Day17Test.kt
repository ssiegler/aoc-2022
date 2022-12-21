import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension
import day17.cave
import day17.jetPatterns
import geometry.Direction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

private const val filename = "Day17Example"

@ExtendWith(ApprovalsExtension::class)
class Day17Test {

    @Test
    fun `sequence starts with 3 right, 2 left, right`() {
        assertEquals(
            listOf(
                Direction.Right,
                Direction.Right,
                Direction.Right,
                Direction.Left,
                Direction.Left,
                Direction.Right,
            ),
            jetPatterns(filename).take(6).toList()
        )
    }

    @Test
    fun `empty cave`(approver: Approver) {
        val cave = cave(filename)
        assertAll(
            { approver.assertApproved(cave.visualize()) },
            { assertEquals(0, cave.height) },
        )
    }

    @Test
    fun `falling example`(approver: Approver) {
        val cave = cave(filename)
        approver.assertApproved(
            generateSequence(cave.visualize()) {
                    cave.dropNext()
                    cave.visualize()
                }
                .take(11)
                .toList()
        ) {
            it.joinToString("\n")
        }
    }

    @Test
    fun part1() {
        assertEquals(3068, day17.part1(filename))
    }

    @Test
    fun part2() {
        assertEquals(1514285714288L, day17.part2(filename))
    }
}
