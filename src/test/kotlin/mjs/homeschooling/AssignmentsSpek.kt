package mjs.homeschooling

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

internal object AssignmentsSpek : Spek({

    fun tasks(vararg pts: Int): TaskList = pts.mapIndexed { idx, i -> Task(nameFor(idx + 1), i) }

    fun shouldAssign(tasks: TaskList, pointsPerChild: Int) = assignTasks(tasks).also {
        assertThat(it.canBeAssigned).isTrue()
        assertThat(it.childOneTasks.points()).isEqualTo(pointsPerChild)
        assertThat(it.childTwoTasks.points()).isEqualTo(pointsPerChild)
        assertThat(it.childThreeTasks.points()).isEqualTo(pointsPerChild)
    }

    fun shouldNotAssign(tasks: TaskList, whyNot: String) = assignTasks(tasks).also {
        assertThat(it.canBeAssigned).isFalse()
        assertThat(it.whyNot).isEqualTo(whyNot)
    }

    fun Assert<TaskList>.sameAs(expected: TaskList) = given { actual ->
        if (actual.sorted() == expected.sorted()) return
        expected("Task list ${show(expected.sorted())} but was ${show(actual.sorted())}")
    }

    describe("divide tasks between three children") {

        it("fewer than three tasks cannot be assigned") {
            shouldNotAssign(tasks(6), whyNot = "there must be at least 3 tasks")
            shouldNotAssign(tasks(5, 4), whyNot = "there must be at least 3 tasks")
        }
        it("three tasks with the same points each can be assigned") {
            repeat(10) {
                val points = Random.nextInt(1, 10)
                shouldAssign(tasks(points, points, points), pointsPerChild = points)
            }
        }
        it("total points must be divisible by 3") {
            shouldNotAssign(tasks(1, 2, 3, 4, 5, 6, 7), whyNot = "the total points (28) are not divisible by 3")
        }
        it("when largest task is more than one third they cannot be assigned") {
            shouldNotAssign(tasks(8, 10, 4, 2, 3), whyNot = "the largest task is larger than 9 points")
        }
        it("works for Karen’s example") {
            shouldAssign(tasks(5, 4, 1, 2, 7, 8, 3), pointsPerChild = 10)
        }
        it("doesn’t matter what order the tasks are in") {
            val values = listOf(5, 4, 1, 2, 7, 8, 3)
            repeat(10) {
                shouldAssign(tasks(*values.shuffled().toIntArray()), pointsPerChild = 10)
            }
        }
        it("works if tasks are split into smaller tasks") {
            shouldAssign(tasks(2, 2, 1, 2, 2, 1, 2, 3, 4, 5, 2, 1, 2, 1), pointsPerChild = 10)
        }
        it("may not be able to divide a variation of Karen’s example") {
            shouldNotAssign(tasks(5, 4, 1, 2, 7, 9, 2),
                    whyNot = "the tasks (30 points) cannot be allocated into 10 points per child")
        }
        it("should be able to allocate: A5 B5 C4 D3 E3 F4 G2 H2 I8") {
            shouldAssign(tasks(5, 5, 4, 3, 3, 4, 2, 2, 8), pointsPerChild = 12)
        }
    }

    describe("extract tasks with a specified number of points") {

        fun shouldExtract(source: String, points: Int, oneThird: String) =
                assertThat(extractPoints(emptyList(), parseTasks(source), points))
                        .sameAs(parseTasks(oneThird))

        it("finds the first task if has the points") {
            shouldExtract("A5 B4 C3 D2 E1", 5, "A5")
        }
        it("finds the first two tasks if they add to the points") {
            shouldExtract("A3 B2 C4 D1 E2 F2 G1", 5, "A3 B2")
        }
        it("finds the first and third tasks when they add to the points") {
            shouldExtract("A4 B3 C1 D2 E2 F2 G1", 5, "A4 C1")
        }
        it("finds the first second and third tasks when they add to the points") {
            shouldExtract("A2 B2 C1 D5 E5", 5, "A2 B2 C1")
        }
    }
})
