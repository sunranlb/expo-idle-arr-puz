import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.*;

public class Main {
    /**
     *                   0
     *              1         2
     *          3        4         5
     *     6        7         8         9
     *         10       11        12
     *    13       14        15        16
     *         17       18        19
     *    20       21        22        23
     *         24       25        26
     *    27       28        29        30
     *         31       32        33
     *             34        35
     *                  36
     */

    private static final int MAX = 2;
    private static long initChest;
    private static int[][] aff = new int[][]{
            {0, 1, 2, 4},
            {1, 0, 3, 4, 7},
            {2, 0, 4, 5, 8},
            {3, 1, 6, 7, 10},
            {4, 0, 1, 2, 7, 8, 11},
            {5, 2, 8, 9, 12},
            {6, 3, 10, 13},
            {7, 1, 3, 4, 10, 11, 14},
            {8, 2, 4, 5, 11, 12, 15},
            {9, 5, 12, 16},
            {10, 3, 6, 7, 13, 14, 17},
            {11, 4, 7, 8, 14, 15, 18},
            {12, 5, 8, 9, 15, 16, 19},
            {13, 6, 10, 17, 20},
            {14, 7, 10, 11, 17, 18, 21},
            {15, 8, 11, 12, 18, 19, 22},
            {16, 9, 12, 19, 23},
            {17, 10, 13, 14, 20, 21, 24},
            {18, 11, 14, 15, 21, 22, 25},
            {19, 12, 15, 16, 22, 23, 26},
            {20, 13, 17, 24, 27},
            {21, 14, 17, 18, 24, 25, 28},
            {22, 15, 18, 19, 25, 26, 29},
            {23, 16, 19, 26, 30},
            {24, 17, 20, 21, 27, 28, 31},
            {25, 18, 21, 22, 28, 29, 32},
            {26, 19, 22, 23, 29, 30, 33},
            {27, 20, 24, 31},
            {28, 21, 24, 25, 31, 32, 34},
            {29, 22, 25, 26, 32, 33, 35},
            {30, 23, 26, 33},
            {31, 24, 27, 28, 34},
            {32, 25, 28, 29, 34, 35, 36},
            {33, 26, 29, 30, 35},
            {34, 28, 31, 32, 36},
            {35, 29, 32, 33, 36},
            {36, 32, 34, 35}
    };

    private static int size = 0;
    private static long startTime;
    private static OperatingSystemMXBean mem;
    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        init();
//        print(initChest);

        Set<Long> his = new HashSet<>();
        Deque<Inf> q = new ArrayDeque<>();
        Inf inf = new Inf();
        inf.c = initChest;
        q.offer(inf);
        while (!q.isEmpty()) {
            size = q.size();
//            System.out.println(his.size());
            Inf p = q.poll();
            for (int i = 0; i < 37; i++) {
                long nxt = act(p.c, i);
//                print(nxt);
                if (!his.contains(nxt)) {
                    his.add(nxt);
                    Inf newInf = new Inf();
                    newInf.c = nxt;
                    newInf.r.addAll(p.r);
                    newInf.r.add(i);
                    if (valid(nxt)) {
                        System.out.println(newInf.r);
                        throw new RuntimeException("EXIT");
                    }
                    q.add(newInf);
                }
            }
        }
    }

    private static class Inf {
        public long c;
        public List<Integer> r;

        public Inf() {
            c = 0;
            r = new ArrayList<>();
        }
    }
    private static void init() {
        initChest = (1L << 37) - 1;
        initChest ^= (1L << 4);
        initChest ^= (1L << 11);
        monitor();

    }

    private static void monitorAve() {
        Thread t = new Thread(() -> {
            int pre = 0;
            int cnt = 1;
            int sum = 0;
            while (true) {
                sum += size - pre;
                System.out.println(sum / (double) cnt);
                cnt++;
                pre = size;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        t.start();
    }

    private static void monitor() {
        Thread t = new Thread(() -> {
            while (true) {
                getMemInfo();
                System.out.println((System.currentTimeMillis() - startTime) + ":" + size);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        t.start();
    }

    public static void getMemInfo() {
        System.out.println((mem.getFreePhysicalMemorySize() / 1024 / 1024) + "/" + (mem.getTotalPhysicalMemorySize() / 1024 / 1024));
//        System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
//        System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
    }

    private static long act(long chest, int i) {
        for (int ii : aff[i]) {
            chest ^= (1L << ii);
        }
        return chest;
    }

    private static boolean valid(long c) {
        return c == (1L << 37) - 1;
    }

    private static void print(long c) {
        System.out.println("                    " + ((c >> 0) & 1));
        System.out.println("               " + ((c >> 1) & 1) + "         " + ((c >> 2) & 1));
        System.out.println("          " + ((c >> 3) & 1) + "         " + ((c >> 4) & 1) + "         " + ((c >> 5) & 1));
        System.out.println("     " + ((c >> 6) & 1) + "         " + ((c >> 7) & 1) + "         " + ((c >> 8) & 1) + "         " + ((c >> 9) & 1));
        System.out.println("          " + ((c >> 10) & 1) + "         " + ((c >> 11) & 1) + "         " + ((c >> 12) & 1));
        System.out.println("     " + ((c >> 13) & 1) + "         " + ((c >> 14) & 1) + "         " + ((c >> 15) & 1) + "         " + ((c >> 16) & 1));
        System.out.println("          " + ((c >> 17) & 1) + "         " + ((c >> 18) & 1) + "         " + ((c >> 19) & 1));
        System.out.println("     " + ((c >> 20) & 1) + "         " + ((c >> 21) & 1) + "         " + ((c >> 22) & 1) + "         " + ((c >> 23) & 1));
        System.out.println("          " + ((c >> 24) & 1) + "         " + ((c >> 25) & 1) + "         " + ((c >> 26) & 1));
        System.out.println("     " + ((c >> 27) & 1) + "         " + ((c >> 28) & 1) + "         " + ((c >> 29) & 1) + "         " + ((c >> 30) & 1));
        System.out.println("          " + ((c >> 31) & 1) + "         " + ((c >> 32) & 1) + "         " + ((c >> 33) & 1));
        System.out.println("               " + ((c >> 34) & 1) + "         " + ((c >> 35) & 1));
        System.out.println("                    " + ((c >> 36) & 1));
    }
}
