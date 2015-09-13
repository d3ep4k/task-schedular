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

class RoundRobinProcess implements Runnable{

    private final Object lock = new Object();
    private volatile boolean suspend = false, stopped = false;
    TaskRunner runner;

    public RoundRobinProcess(TaskRunner runner) {
        this.runner = runner;
    }
    
    
    @Override
    public void run() {
        while (!stopped) {
            while (!suspend) {
                runner.runTask();
            }
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
    
    /**
     * Suspend the process
     */
    public void suspend() {
        suspend = true;
    }

    public void stop() {
        suspend = true;
        stopped = true;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * Resume the process 
     */
    public void resume() {
        suspend = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }


}
