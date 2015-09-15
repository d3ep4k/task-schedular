package jobschedular;

public class CpuIoBoundTask extends Task {

    int n, max;

    CpuIoBoundTask(int index, int burst) {
        super(index, burst);
    }
/**
 * CPU bound computation in loop and printing to console.
 */
    @Override
    void operation() {
        burst--;
        System.out.println("CPU/IO Bound");
    }

}
