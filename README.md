# Homeschooling triplets

Allocating homeschooling tasks to triplets Kim, Ash and Lou.

Solution to the ThoughtWorks Australia Shokunin challenge for July 2020.

## The challenge

In one busy Australian household both parents have been working from home during lockdown
while also homeschooling their three children (triplets).

Each day the children’s teacher assigns a list of tasks for them to complete between them.
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

## Prerequisite

Java 8 or later. Without installing Java you can run it in Docker from this directory with:

    docker run -i -t -v `pwd`:`pwd` -w `pwd` adoptopenjdk:8-jdk-hotspot /bin/bash

## Script

Run using the script `go.sh`, which will build the application first if necessary.

Running `./go.sh --help` (or with no arguments) shows how to run the app.

## Specify tasks

Specify tasks using space-separated tokens with name and points together. A simple
example is (Karen’s example tasks):

    ./go.sh A5 B4 C1 D2 E7 F8 G3

You can get fancy with:

    ./go.sh Maths:6 Geography:4 Biology:5 Art:5 Music:7 Physics:3

## Generate random tasks

To generate random tasks, specify the number of tasks and, optionally, the maximum
number of points for each (default 10):

    ./go.sh --random-tasks 10 --max-points 20
    ./go.sh -r 8 -m 12

If you specify both explicit tasks and --random-tasks, the latter takes precedence.  

## Read arguments from file

You can read arguments from a file:

    ./go.sh @example.txt
