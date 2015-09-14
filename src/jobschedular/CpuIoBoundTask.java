package jobschedular;

public class CpuIoBoundTask extends Task {

    int n, max;

    CpuIoBoundTask(int index, int burst, int n, int max) {
        super(index, burst);
        this.n = n;
        this.max = max;
    }
/**
 * CPU bound computation in loop and printing to console.
 */
    @Override
    void operation() {
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
