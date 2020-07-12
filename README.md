# Homeschooling

Solution to the ThoughtWorks Australia Shokunin challenge for July 2020.

## The challenge

In one busy Australian household both parents have been working from home during lockdown
while also home schooling their three children (triplets).
Each day the children's teacher assigns a list of tasks for them to complete between them.
Each task is assigned some points based on difficulty.
The parents are keen that each day each child completes the same number of points so as not
to fall behind. And also to stop the constant bickering about who does more/less work :)

For example on Monday the teacher assigned:

 Task A: 5 points
 Task B: 4 points
 Task C: 1 point
 Task D: 2 points
 Task E: 7 points
 Task F: 8 points
 Task G: 3 points

 These tasks can be split amongst the triplets like so:

 Child 1: Task D (2 points) + Task F (8 points) = 10 points
 Child 2: Task A (5 points) + Task B (4 points) + Task C (1 point) = 10 points
 Child 3: Task E (7 points) + Task G (3 points) = 10 points

The children cannot work on the same task. That would require the negotiation skills of
 a UN diplomat.

The family needs a way to quickly assess if the tasks can be divided amongst all the children
so they each get the same number of points.
If the tasks fail to be split between the three children they will send them back to the 
teacher and ask for a new set of tasks.

## How to run the solution


Run using the script `go.sh`, for example:

```bash
# Randomly generate 10 tasks
./go.sh --random-tasks 10
# Run the example shown above
./go.sh A5,B4,C1,D2,E7,F8,G3
# Read tasks from a file
./go.sh --file example.txt
```

The script will build the application first if necessary.
