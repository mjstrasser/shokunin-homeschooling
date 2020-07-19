package mjs.homeschooling

/**
 * Data structure with the outcome of homeschooling task assignments.
 */
data class Assignments(
        val childOneTasks: ChildTasks = ChildTasks("Kim"),
        val childTwoTasks: ChildTasks = ChildTasks("Ash"),
        val childThreeTasks: ChildTasks = ChildTasks("Lou"),
        val canBeAssigned: Boolean = true,
        val whyNot: String = ""
)
