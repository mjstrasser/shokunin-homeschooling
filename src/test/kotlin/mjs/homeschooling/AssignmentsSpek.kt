package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

internal object AssignmentsSpek : Spek({

    fun tasks(vararg pts: Int) = pts.map { Task("X", it) }

    describe("divide tasks between three children") {

        it("fewer than three tasks cannot be assigned") {
            assignTasks(tasks(5, 4)).apply {
                assertThat(canBeAssigned).isFalse()
                assertThat(whyNot).isEqualTo("There must be at least 3 tasks")
            }
        }

        it("three tasks with the same points each can be assigned") {
            val points = Random.nextInt(1, 10)
            assignTasks(tasks(points, points, points)).apply {
                assertThat(canBeAssigned).isTrue()
                assertThat(childOneTasks.points()).isEqualTo(points)
                assertThat(childTwoTasks.points()).isEqualTo(points)
                assertThat(childThreeTasks.points()).isEqualTo(points)
            }
        }

        it("total points must be divisible by 3") {
            assignTasks(tasks(1, 2, 3, 4, 5, 6, 7)).apply {
                assertThat(canBeAssigned).isFalse()
                assertThat(whyNot).isEqualTo("The points are not divisible by 3")
            }
        }

        it("when largest task is more than one third they cannot be assigned") {
            assignTasks(tasks(8, 10, 4, 2, 3)).apply {
                assertThat(canBeAssigned).isFalse()
                assertThat(whyNot).isEqualTo("The largest task is larger than 9 points")
            }
        }

        it("works for Karen’s example") {
            assignTasks(tasks(5, 4, 1, 2, 7, 8, 3)).apply {
                assertThat(canBeAssigned).isTrue()
                assertThat(childOneTasks.points()).isEqualTo(10)
                assertThat(childTwoTasks.points()).isEqualTo(10)
                assertThat(childThreeTasks.points()).isEqualTo(10)
            }
        }

        it("doesn’t matter what order the tasks are in") {
            val values = listOf(5, 4, 1, 2, 7, 8, 3)
            repeat(10) {
                assignTasks(tasks(*values.shuffled().toIntArray())).apply {
                    assertThat(canBeAssigned).isTrue()
                    assertThat(childOneTasks.points()).isEqualTo(10)
                    assertThat(childTwoTasks.points()).isEqualTo(10)
                    assertThat(childThreeTasks.points()).isEqualTo(10)
                }
            }
        }

        it("works if tasks are split into smaller tasks") {
            assignTasks(tasks(2, 2, 1, 2, 2, 1, 2, 3, 4, 5, 2, 1, 2, 1)).apply {
                assertThat(canBeAssigned).isTrue()
                assertThat(childOneTasks.points()).isEqualTo(10)
                assertThat(childTwoTasks.points()).isEqualTo(10)
                assertThat(childThreeTasks.points()).isEqualTo(10)
            }
        }

        it("may not be able to divide a variation of Karen’s example") {
            assignTasks(tasks(5, 4, 1, 2, 7, 9, 2)).apply {
                assertThat(canBeAssigned).isFalse()
                assertThat(whyNot).isEqualTo("Tasks cannot be divided into 10 points each child")
            }
        }
    }
})
