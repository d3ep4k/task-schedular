/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobschedular;

import java.util.Scanner;

/**
 *
 * @author deepak
 */
public class Task {

    public int index;                   // task index (ID)
    public int burst;                   // task burst time

    public Task(int index, int burst) {
        this.index = index;
        this.burst = burst;
    }

    /**
     * CPU bound operation. Calculate the value of PI
     * @param sum
     */
    public void cpuBound(float sum) {
        for (double i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                sum += -1 / (2 * i - 1);
            } else {
                sum += 1 / (2 * i - 1);
            }
        }
    }

    /**
     * IO bound operations. Reading file and printing to console.
     *
     * @param filename
     */
    public void ioBound(String filename) {

        Scanner sc = new Scanner(Task.class.getResourceAsStream(filename));

        while (sc.hasNextLine()) {
            int i = sc.nextInt();
            System.out.println(i);
        }
        sc.close();

    }

    /**
     * CPU bound computation in loop and printing to console.
     *
     * @param n
     * @param max
     */
    public void cpuAndIoBound(int n, int max) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < max - i - 1; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i * 2 + 1; j++) {
                System.out.print("X");
            }
            System.out.println("");
        }
    }
}
