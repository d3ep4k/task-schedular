/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobschedular;

/**
 *
 * @author deepak
 */
public abstract class Task {
  public int index;                   // task index (ID)
    public int burst;                   // task burst time

    public Task(int index, int burst) {
        this.index = index;
        this.burst = burst;
    }

    abstract void operation();
}
