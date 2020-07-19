package mjs.homeschooling

/**
 * Assign a [TaskList] to three children with equal points, if possible.
 */
fun assignTasks(allTasks: TaskList): Assignments {
    if (allTasks.size < 3) return nope("there must be at least 3 tasks")

    val allPoints = allTasks.points()
    if (allPoints % 3 != 0) return nope("the total points ($allPoints) are not divisible by 3")

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedDesc()
    if (allTasksDesc.first().points > pointsPerChild)
        return nope("the largest task is larger than $pointsPerChild points")

    val kidOneTasks = extractPoints(emptyList(), allTasksDesc, pointsPerChild)

    /** Kotlin [Iterable.minus] preserves order in the source list */
    val otherKidsTasks = allTasksDesc - kidOneTasks

    val kidTwoTasks = extractPoints(emptyList(), otherKidsTasks, pointsPerChild)
    val remainingTasks = otherKidsTasks - kidTwoTasks

    return if (remainingTasks.points() == pointsPerChild)
        Assignments(true, ChildTasks("Ash", kidOneTasks), ChildTasks("Kim", kidTwoTasks), ChildTasks("Lou", remainingTasks))
    else
        nope("the tasks (${3 * pointsPerChild} points) cannot be allocated into $pointsPerChild points per child")
}

/**
 * Extract a list of tasks that add to the given points, if possible, from an
 * input list in descending order of size.
 */
tailrec fun extractPoints(dest: TaskList, sourceDesc: TaskList, points: Int): TaskList {
    if (dest.points() == points) return dest

    // No more points left to add: return nothing
    if (sourceDesc.isEmpty()) return emptyList()

    return if (sourceDesc.first().points + dest.points() <= points)
    // Head of list (most points) will fit
        extractPoints(dest + sourceDesc.take(1), sourceDesc.drop(1), points)
    else
    // Try with the tail
        extractPoints(dest, sourceDesc.drop(1), points)
}

fun nope(whyNot: String) = Assignments(canBeAssigned = false, whyNot = whyNot)
