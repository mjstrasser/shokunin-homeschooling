package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

internal object TasksSpek : Spek({
    describe("parseTask") {
        it("parses simple tokens") {
            assertThat(parseTask("A1")).isEqualTo(Task("A", 1))
            assertThat(parseTask("QY47")).isEqualTo(Task("QY", 47))
        }
        it("parses complex tokens that match the pattern") {
            assertThat(parseTask("Walter-143")).isEqualTo(Task("Walter", 143, "-"))
            assertThat(parseTask("Maths:15")).isEqualTo(Task("Maths", 15, ":"))
        }
        it("throws an exception for a token that cannot be parsed") {
            assertThat {
                parseTask("A")
            }.isFailure().hasMessage(""""A" does not specify a task""")
        }
    }

    describe("nameFor") {
        it("returns tasks names with single letters up to 26") {
            assertThat(nameFor(1)).isEqualTo("A")
            assertThat(nameFor(17)).isEqualTo("Q")
            assertThat(nameFor(26)).isEqualTo("Z")
        }
        it("returns task names with multiple letters for more than 26") {
            assertThat(nameFor(27)).isEqualTo("AA")
            assertThat(nameFor(28)).isEqualTo("AB")
            assertThat(nameFor(26 + 26)).isEqualTo("AZ")
            assertThat(nameFor(26 + 26 * 26)).isEqualTo("ZZ")
        }
    }

    describe("generate random tasks") {
        it("returns a list of tasks") {
            val nTasks = Random.nextInt(1000)
            assertThat(generateTasks(nTasks, Random.nextInt(20)).size).isEqualTo(nTasks)
        }
        it("returns task names from A to the nth letter up to Z") {
            generateTasks(3, 20).apply {
                assertThat(first().name).isEqualTo("A")
                assertThat(last().name).isEqualTo("C")
            }
            generateTasks(11, 15).apply {
                assertThat(first().name).isEqualTo("A")
                assertThat(last().name).isEqualTo("K")
            }
            generateTasks(26, 10).apply {
                assertThat(first().name).isEqualTo("A")
                assertThat(last().name).isEqualTo("Z")
            }
        }
    }
})