package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

internal object AssignmentsSpek : Spek({

    fun tasks(vararg pts: Int): TaskList = pts.map { Task(nameFor(it), it) }

    fun shouldAssign(tasks: TaskList, pointsPerChild: Int) = assignTasks(tasks).apply {
        assertThat(canBeAssigned).isTrue()
        assertThat(childOneTasks.points()).isEqualTo(pointsPerChild)
        assertThat(childTwoTasks.points()).isEqualTo(pointsPerChild)
        assertThat(childThreeTasks.points()).isEqualTo(pointsPerChild)
    }

    fun shouldNotAssign(tasks: TaskList, whyNot: String) = assignTasks(tasks).apply {
        assertThat(canBeAssigned).isFalse()
        assertThat(whyNot).isEqualTo(whyNot)
    }

    describe("divide tasks between three children") {

        it("fewer than three tasks cannot be assigned") {
            shouldNotAssign(tasks(6), whyNot = "There must be at least 3 tasks")
            shouldNotAssign(tasks(5, 4), whyNot = "There must be at least 3 tasks")
        }

        it("three tasks with the same points each can be assigned") {
            repeat (10) {
                val points = Random.nextInt(1, 10)
                shouldAssign(tasks(points, points, points), pointsPerChild = points)
            }
        }

        it("total points must be divisible by 3") {
            shouldNotAssign(tasks(1, 2, 3, 4, 5, 6, 7), whyNot = "The points are not divisible by 3")
        }

        it("when largest task is more than one third they cannot be assigned") {
            shouldNotAssign(tasks(8, 10, 4, 2, 3), whyNot = "The largest task is larger than 9 points")
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
            shouldNotAssign(tasks(5, 4, 1, 2, 7, 9, 2), whyNot = "Tasks cannot be divided into 10 points each child")
        }
    }
})
