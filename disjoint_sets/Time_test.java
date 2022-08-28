package disjoint_sets;

public class Time_test {
    private static void printTimingTable(int Nsize, long times, int opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "nanosec/op");
        System.out.printf("------------------------------------------------------------\n");
        int N = Nsize;
        double time = times;
        int opCount = opCounts;
        double timePerOp = time / opCount;
        System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
    }

    public static void main(String[] args) {
        timeConstruction();
    }

    public static void timeConstruction() {
        Naive_connected_items<Integer> t= new Naive_connected_items<>();
        int N_size = 1000;
        int op_count = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < N_size; i += 1) {
            t.add_item(i);
            op_count += 1;
            int num1 = i + 2000;
            op_count += 1;
            int num2 = i + 5000;
            op_count += 1;
            t.connect(num1, num2);
            op_count += 1;
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        printTimingTable(N_size, totalTime, op_count);

    }
}
