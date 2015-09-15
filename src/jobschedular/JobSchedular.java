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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deepak
 */
public class JobSchedular {

    /**
     * @param args the command line arguments
     */
    static Scanner src = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Select Algorithm to be implemented:");
            System.out.println("1. First Come First Serve");
            System.out.println("2. Shortest Job First");
            System.out.println("3. Round Robin");
            System.out.println("Any other key to exit");
            try {
                choice = src.nextInt();
            } catch (Exception ex) {
                break;
            }
            switch (choice) {
                case 1:
                    fcfs();
                    break;
                case 2:
                    sjf();
                    break;
                case 3:
                    roundRobin();
                    break;

            }
        } while (choice >= 1 && choice <= 3);
    }

    private static void roundRobin() {
        int quantum;
        List<RoundRobinProcess> threadList
                = new ArrayList<>();
        System.out.println("Enter quantum time:");
        quantum = src.nextInt();
        RoundRobinProcess rrp = new RoundRobinProcess(new TaskRunner() {
            public void runTask() {
                Task t = new CpuIoBoundTask(1, 100);
                t.operation();
            }
        });
        threadList.add(rrp);

        rrp = new RoundRobinProcess(new TaskRunner() {
            public void runTask() {
                Task t = new CpuIoBoundTask(2, 200);
                t.operation();
            }
        });
        threadList.add(rrp);

        rrp = new RoundRobinProcess(new TaskRunner() {
            public void runTask() {
                Task t = new CpuIoBoundTask(3, 210);
                t.operation();
            }
        });

        threadList.add(rrp);

        //start all the threads
        threadList.stream().forEach((rrp1) -> {
            Thread t = new Thread(rrp1);
            t.start();
        });
        
        //round robin
        while (true) {
            threadList.stream().forEach((rrp1) -> {
                Thread t = new Thread(rrp1);
                t.start();
                try {
                    t.sleep(quantum);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JobSchedular.class.getName()).log(Level.SEVERE, null, ex);
                }
                rrp1.suspend();
            });
        }
    }

    private static void fcfs() {

        int index = 1;
        ArrayList<Task> tasks = new ArrayList<>(); // storage for the tasks
        Random r = new Random();
        while (index < 7) // read CPU burst times of tasks 
        {
            int burst = r.nextInt(100);
            tasks.add(new CpuBoundTask(index, burst, 0));  // add a new task to the array
            index++;
        }
        int numTasks = tasks.size();          // total # of tasks

        int waitingTime = 0;                  // waiting time in a queue
        int time = 0;                         // CPU time
        System.out.print("0");
        while (!tasks.isEmpty()) // take the tasks in the natural order
        {                                     // and throw them into the schedule

            Task t = tasks.remove(0);
            t.operation();
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
        ArrayList<Task> tasks = new ArrayList<>(); // storage for tasks
        Random r = new Random();
        while (index < 7) // read CPU burst times of tasks 
        {
            int burst = r.nextInt(100);         // create a new task
            tasks.add(new CpuBoundTask(index, burst, 0));   // and add it to an array
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
