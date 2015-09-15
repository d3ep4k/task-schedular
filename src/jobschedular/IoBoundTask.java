package jobschedular;

import java.util.Scanner;

public class IoBoundTask extends Task {

    String filePath;

    public IoBoundTask(int index, int burst, String fileName) {
        super(index, burst);
        this.filePath = fileName;
    }

    /**
     * IO bound operations. Reading file and printing to console.
     */
    @Override
    void operation() {
        try (Scanner sc = new Scanner(Task.class.getResourceAsStream(filePath))) {
            while (sc.hasNextLine()) {
                String i = sc.nextLine();
                System.out.println(i);
            }
        }
    }

}
