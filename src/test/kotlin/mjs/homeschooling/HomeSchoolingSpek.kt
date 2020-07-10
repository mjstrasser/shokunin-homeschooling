package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random


internal object HomeSchoolingSpek : Spek({
    describe("divide tasks between three children") {
        it("fewer than three tasks cannot be assigned") {
            (1..2).forEach { numTasks ->
                divideBetweenThreeChildren(randomTasks(numTasks)).apply {
                    assertThat(canBeSplit).isFalse()
                }
            }
        }
        describe("three tasks") {
            it("with the same points each can be assigned") {
                val pointsPerTask = Random.nextInt(1, 10)
                divideBetweenThreeChildren(listOf(
                        Task("A", pointsPerTask),
                        Task("B", pointsPerTask),
                        Task("C", pointsPerTask)
                )).apply {
                    assertThat(canBeSplit).isTrue()
                    listOf(childOneTasks, childTwoTasks, childThreeTasks).forEach { tasks ->
                        assertThat(tasks).hasSize(1)
                        assertThat(tasks.first().points).isEqualTo(pointsPerTask)
                    }
                }
            }
        }
    }
})