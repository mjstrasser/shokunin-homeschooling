package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

object CliSpek : Spek({

    describe("Homeschooling CLI") {
        it("accepts manually-specified tasks") {
            HomeSchooling().apply {
                parse(listOf("A1", "B2", "C3"))
                assertThat(selectTasks()).isEqualTo(listOf(Task("A", 1), Task("B", 2), Task("C", 3)))
            }
        }
        it("fails on the first manual task that cannot be parsed") {
            assertThat {
                HomeSchooling().parse(listOf("A1", "B", "C3", "D"))
            }.isFailure().hasMessage("'B' does not specify a task")
        }
        it("generates random tasks") {
            val numTasks = Random.nextInt(15)
            HomeSchooling().apply {
                parse(listOf("--random-tasks", numTasks.toString()))
                assertThat(selectTasks()).hasSize(numTasks)
            }
        }
        it("recognises -r for generating random tasks") {
            val numTasks = Random.nextInt(15)
            HomeSchooling().apply {
                parse(listOf("-r", numTasks.toString()))
                assertThat(selectTasks()).hasSize(numTasks)
            }
        }
        it("fails if a number is not specified for random tasks") {
            assertThat {
                HomeSchooling().parse(listOf("-r", "X"))
            }.isFailure().hasMessage("""Invalid value for "-r": X is not a valid integer""")
        }
        it("prefers randomly-generated tasks to individually specified ones") {
            HomeSchooling().apply {
                parse(listOf("A1", "B2", "-r", "15"))
                assertThat(selectTasks()).hasSize(15)
            }
        }
        it("fails if an unrecognised option is provided") {
            assertThat {
                HomeSchooling().parse(listOf("-t", "15"))
            }.isFailure().hasMessage("""no such option: "-t".""")
        }
    }

})