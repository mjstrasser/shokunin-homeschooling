package mjs.homeschooling

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object IndexerSpek : Spek({
    describe("Oscillating indexer for a list of 3") {
        it("provides 0, 1, 2 for 3 cases") {
            assertThat(oscillatingIndexer(3)).isEqualTo(listOf(0, 1, 2))
        }
        it("provides 0, 1, 2, 2 for 4 cases") {
            assertThat(oscillatingIndexer(4)).isEqualTo(listOf(0, 1, 2, 2))
        }
        it("provides 0, 1, 2, 2, 1 for 5 cases") {
            assertThat(oscillatingIndexer(5)).isEqualTo(listOf(0, 1, 2, 2, 1))
        }
        it("provides 0, 1, 2, 2, 1, 0 for 6 cases") {
            assertThat(oscillatingIndexer(6)).isEqualTo(listOf(0, 1, 2, 2, 1, 0))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0 for 7 cases") {
            assertThat(oscillatingIndexer(7)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0, 1 for 8 cases") {
            assertThat(oscillatingIndexer(8)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0, 1))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0, 1, 2 for 9 cases") {
            assertThat(oscillatingIndexer(9)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0, 1, 2))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0, 1, 2, 2 for 10 cases") {
            assertThat(oscillatingIndexer(10)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0, 1, 2, 2))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1 for 11 cases") {
            assertThat(oscillatingIndexer(11)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1))
        }
        it("provides 0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1, 0 for 12 cases") {
            assertThat(oscillatingIndexer(12)).isEqualTo(listOf(0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1, 0))
        }
    }
})
