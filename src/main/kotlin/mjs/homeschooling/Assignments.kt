package mjs.homeschooling

data class Assignments(
        val canBeAssigned: Boolean = true,
        val childOneTasks: ChildTasks = ChildTasks("Kim"),
        val childTwoTasks: ChildTasks = ChildTasks("Shane"),
        val childThreeTasks: ChildTasks = ChildTasks("Drew"),
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
