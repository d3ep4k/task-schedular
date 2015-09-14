package jobschedular;

public class CpuBoundTask extends Task {

    float sum;

    public CpuBoundTask(int index, int burst, float sum) {
        super(index, burst);
        this.sum = sum;
    }

    /**
     * CPU bound operation. Calculate the value of PI
     */
    @Override
    void operation() {
        for (double i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                sum += -1 / (2 * i - 1);
            } else {
                sum += 1 / (2 * i - 1);
            }
        }
    }

}
