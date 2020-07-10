package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.random.Random

internal object TaskSpek : Spek({
    describe("randomTasks") {
        it("returns a list of tasks") {
            val nTasks = Random.nextInt(26)
            assertThat(randomTasks(nTasks).size).isEqualTo(nTasks)
        }
        it("returns tasks from A to the nth letter") {
            assertThat(randomTasks(3).last().name).isEqualTo("C")
            assertThat(randomTasks(11).last().name).isEqualTo("K")
            assertThat(randomTasks(23).last().name).isEqualTo("W")
        }
    }
})