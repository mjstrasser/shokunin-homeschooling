package mjs.homeschooling

/**
 * Data structure with the outcome of homeschooling task assignments.
 */
data class Assignments(
        val canBeAssigned: Boolean = true,
        val childOneTasks: ChildTasks = ChildTasks("Kim"),
        val childTwoTasks: ChildTasks = ChildTasks("Ash"),
        val childThreeTasks: ChildTasks = ChildTasks("Lou"),
        val whyNot: String = ""
)

/**
 * Assign a [Task] to the child with the fewest points.
 */
fun Assignments.assignTask(task: Task): Assignments {
    val sortedChildren = listOf(childOneTasks, childTwoTasks, childThreeTasks)
            .sortedBy(ChildTasks::points)
    return Assignments(
            childOneTasks = sortedChildren[0].add(task),
            childTwoTasks = sortedChildren[1],
            childThreeTasks = sortedChildren[2]
    )
}

fun nope(whyNot: String) = Assignments(canBeAssigned = false, whyNot = whyNot)
