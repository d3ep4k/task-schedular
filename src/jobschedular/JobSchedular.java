/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobschedular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author deepak
 */
public class JobSchedular {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        fcfs();
    }

    private static void roundRobin() {
        List<RoundRobinProcess> threadList
                = new ArrayList<RoundRobinProcess>();

        RoundRobinProcess rrp = new RoundRobinProcess(new TaskRunner() {
            @Override
            public void runTask() {
                Task t = new Task(1, 10);
                t.cpuAndIoBound(10, 20);
            }
        });
        threadList.add(rrp);

        rrp = new RoundRobinProcess(new TaskRunner() {
            @Override
            public void runTask() {
                Task t = new Task(2, 10);
                t.ioBound("/resources/data.txt");
            }
        });
        threadList.add(rrp);

        rrp = new RoundRobinProcess(new TaskRunner() {
            @Override
            public void runTask() {
                Task t = new Task(3, 20);
                t.cpuBound(100);
            }
        });
        threadList.add(rrp);

        for (RoundRobinProcess rrp1 : threadList) {
            new Thread(rrp1).start();
        }

    }

    private static void fcfs() {

        int index = 1;
        ArrayList<Task> tasks = new ArrayList<Task>(); // storage for the tasks
        Random r = new Random();
        while (index < 7) // read CPU burst times of tasks 
        {
            int burst = r.nextInt(100);
            tasks.add(new Task(index, burst));  // add a new task to the array
            index++;
        }
        int numTasks = tasks.size();          // total # of tasks

        int waitingTime = 0;                  // waiting time in a queue
        int time = 0;                         // CPU time
        System.out.print("0");
        while (!tasks.isEmpty()) // take the tasks in the natural order
        {                                     // and throw them into the schedule
            
            Task t = tasks.remove(0);
            t.cpuAndIoBound(time, time);
            if (!tasks.isEmpty()) {
                waitingTime += waitingTime + t.burst;
            }
            time += t.burst;
            System.out.print(" - P" + t.index + " - " + time);
        }
        System.out.println("\nAverage waiting time: " + waitingTime + "/"
                + numTasks + " = " + ((1.0 * waitingTime) / numTasks));

    }

    private static void sjf() {
        int index = 1;
        ArrayList<Task> tasks = new ArrayList<Task>(); // storage for tasks
        Random r = new Random();
        while (index < 7) // read CPU burst times of tasks 
        {
            int burst = r.nextInt(100);         // create a new task
            tasks.add(new Task(index, burst));   // and add it to an array
            index++;
        }
        int numTasks = tasks.size();           // total number of tasks

        Task[] tasksA = tasks.toArray(new Task[tasks.size()]); // convert ArrayList
        Arrays.sort(tasksA, new Comparator<Task>() {
            public int compare(Task t1, Task t2) // comparator for sorting
            {
                return (t1.burst - t2.burst);
            }
        });                // sort tasks by burst times

        System.out.println(numTasks + " created"); // print the schedule
        int waitingTime = 0;
        int time = 0;
        System.out.print("0");
        for (int i = 0; i < tasksA.length; i++) {
            Task t = tasksA[i];
            time += t.burst;
            System.out.print(" - P" + t.index + " - " + time);
            if (i < tasksA.length - 1) {
                waitingTime += time;
            }
        }
        System.out.println("\nAverage waiting time: " + waitingTime + "/"
                + numTasks + " = " + ((1.0 * waitingTime) / numTasks));
    }
}
