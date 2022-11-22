import java.util.*;

public class Main {
    private static class Inf {
        public int[] s;
        public List<String> r;

        public Inf() {
            r = new ArrayList<>();
        }
    }
    private static Map<String, int[]> M = new HashMap<String, int[]>() {{
        put("01", new int[]{1, 0, 3, -1});
        put("02", new int[]{2, 0, 0, -2});
        put("03", new int[]{3, 0, 3, 3});
        put("04", new int[]{-2, 0, 0, 2});
        put("05", new int[]{-1, 0, 3, 1});
        put("11", new int[]{0, 3, 3, 3});
        put("21", new int[]{-2, 2, 3, 0});
        put("22", new int[]{2, -2, 0, 0});
        put("23", new int[]{0, 0, 3, 0});
        put("24", new int[]{-2, 2, 0, 0});
        put("25", new int[]{2, -2, 3, 0});
        put("31", new int[]{1, -2, 0, 1});
        put("32", new int[]{2, 2, 0, 2});
        put("33", new int[]{3, 0, 0, 3});
        put("34", new int[]{-2, -2, 0, -2});
        put("35", new int[]{-1, 2, 0, -1});
    }};

    public static void main(String[] args) {
        String ss = "5212";
        int[] input = new int[4];
        for (int i = 0; i < 4; i++) {
            input[i] = ss.charAt(i) - '0';
        }

        Deque<Inf> q = new ArrayDeque<>();
        Set<Integer> his = new HashSet<>();
        his.add(sig(input));
        Inf inf = new Inf();
        inf.s = input;
        q.offer(inf);
        while (!q.isEmpty()) {
            Inf p = q.poll();
            for (String s : M.keySet()) {
                int[] m = M.get(s);
                int[] ns = new int[4];
                for (int i = 0; i < 4; i++) {
                    ns[i] = (p.s[i] + m[i]) % 6;
                }
                int si = sig(ns);
                inf = new Inf();
                inf.s = ns;
                inf.r.addAll(p.r);
                inf.r.add(s);
                if (valid(ns)) {
                    System.out.println(inf.r);
                    throw new RuntimeException("E");
                }
                if (!his.contains(si)) {
                    his.add(si);
                    q.offer(inf);
                }
            }
        }
        System.out.println("NO RESULT!");
    }

    private static boolean valid(int[] a) {
        for (int b : a) {
            if (b != 1) {
                return false;
            }
        }
        return true;
    }

    private static int sig(int[] a) {
        int mul = 6;
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res += a[i] * mul;
            mul *= 6;
        }
        return res;
    }
}