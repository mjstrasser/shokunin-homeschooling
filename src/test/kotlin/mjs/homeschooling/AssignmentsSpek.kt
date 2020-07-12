package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

fun tasks(vararg pts: Int) = pts.map { Task("X", it) }

internal object AssignmentsSpek : Spek({
    describe("divide tasks between three children") {

        it("fewer than three tasks cannot be assigned") {
            assertThat(assignTasks(tasks(3)).canBeAssigned).isFalse()
            assertThat(assignTasks(tasks(5, 4)).canBeAssigned).isFalse()
        }

        it("three tasks with the same points each can be assigned") {
            val points = Random.nextInt(1, 10)
            assertThat(assignTasks(tasks(points, points, points)).canBeAssigned).isTrue()
        }

        it("when largest task is more than one third they cannot be assigned") {
            assertThat(assignTasks(tasks(8, 10, 5, 2, 4)).canBeAssigned).isFalse()
            assertThat(assignTasks(tasks(5, 4, 1, 2, 7, 2, 3, 13)).canBeAssigned).isFalse()
        }

        it("works for Karen’s example") {
            assertThat(assignTasks(tasks(5, 4, 1, 2, 7, 8, 3)).canBeAssigned).isTrue()
        }

        it("doesn’t matter what order the tasks are in") {
            val values = listOf(5, 4, 1, 2, 7, 8, 3)
            repeat(10) {
                assertThat(assignTasks(tasks(*values.shuffled().toIntArray())).canBeAssigned).isTrue()
            }
        }

        it("works if tasks are split into smaller tasks") {
            assertThat(assignTasks(tasks(2, 2, 1, 2, 2, 1, 2, 3, 4, 5, 2, 1, 2, 1)).canBeAssigned).isTrue()
        }

        it("may not be able to divide a variation of Karen’s example") {
            assertThat(assignTasks(tasks(5, 4, 1, 2, 7, 9, 2)).canBeAssigned).isFalse()
        }
    }
})
