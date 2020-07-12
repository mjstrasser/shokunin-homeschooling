package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ParsingSpek : Spek({

    describe("parsing task specifications from the command line") {
        it("parses comma-separated values") {
            assertThat(parseTasks("A1,B2,C3")).isEqualTo(listOf(Task("A", 1), Task("B", 2), Task("C", 3)))
        }
    }

})