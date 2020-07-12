package mjs.homeschooling

fun assignTasks(allTasks: TaskList): Assignments {
    if (allTasks.size < 3) return nope("There must be at least 3 tasks")

    val allPoints = allTasks.points()
    if (allPoints % 3 != 0) return nope("The points are not divisible by 3")

    val pointsPerChild = allPoints / 3
    val allTasksDesc = allTasks.sortedByDescending(Task::points)
    if (allTasksDesc.first().points > pointsPerChild)
        return nope("The largest task is larger than $pointsPerChild points")

    return allocateTasks(Assignments(), allTasksDesc, pointsPerChild)
}

tailrec fun allocateTasks(assignments: Assignments, tasksDesc: TaskList, pointsPerChild: Int): Assignments {
    if (tasksDesc.isEmpty()) return assignments
    val newAssignments = assignments.assignTask(tasksDesc.first())
    if (newAssignments.childOneTasks.points() > pointsPerChild)
        return nope("Tasks cannot be divided into $pointsPerChild points each child")
    return allocateTasks(newAssignments, tasksDesc.drop(1), pointsPerChild)
}