public class Main {
    private static final long START_TIME_NANOS = System.nanoTime();

    public static boolean isReductible(int num, int k) {
        if (k > 9 || k < 1) {
            return false;
        }
        while (num > 9) {
            int aux = 0;
            while (num != 0) {
                aux += (num % 10) * (num % 10);
                num /= 10;
            }
            num = aux;
        }
        return num == k;
    }

    public static void main(String[] args) {
        // Compulsory
        System.out.println("Hello world!");

        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        int result = (n * 3 + Integer.parseInt("10101", 2) + Integer.parseInt("FF", 16)) * 6;
        int sum_of_digits = 0;

        while (true) {
            sum_of_digits += result % 10;
            result /= 10;

            if (result == 0) {
                if (sum_of_digits < 10) {
                    break;
                } else {
                    result = sum_of_digits;
                    sum_of_digits = 0;
                }
            }
        }
        System.out.println(sum_of_digits);

        System.out.println("Willy-nilly, this semester I will learn " + languages[sum_of_digits]);

        // Homework
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        String resStr = "";
        while (a <= b) {
            if (isReductible(a, k)) {
                resStr += a + ", ";
            }
            a++;
        }
        System.out.println(resStr);

        long elapsedTimeInNanos = System.nanoTime() - START_TIME_NANOS;
        System.out.println("Elapsed time in nanoseconds: " + elapsedTimeInNanos);

        // Bonus
        int[][] adiancyMatrix = new int[64][64];
        // nodul 0 are 1
        int w_value = Integer.parseInt(args[3]);
        for (int i = 1; i <= w_value; i++) {
            adiancyMatrix[i][0] = adiancyMatrix[0][i] = 1;
            adiancyMatrix[i][i + 1] = adiancyMatrix[i + 1][i] = adiancyMatrix[i - 1][i] = adiancyMatrix[i][i - 1] = 1;
        }
        adiancyMatrix[w_value - 1][1] = adiancyMatrix[1][w_value - 1] = 1;

        System.out.printf("/ ");
        for (int i = 0; i < w_value; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        for (int i = 0; i < w_value; i++) {
            System.out.printf("%d ", i);
            for (int j = 0; j < w_value; j++) {
                System.out.printf("%d ", adiancyMatrix[i][j]);
            }
            System.out.println();
        }

        int total_cycles = 0;
        int length = 3;
        while (length <= w_value) {
            for (int i = 1; i <= w_value - 1; i++) {
                System.out.print("0 -> ");
                for (int j = i - 1; j < length - 2 + i; j++) {
                    System.out.print(j % (w_value - 1) + 1 + " -> ");
                }
                System.out.println("0");
                total_cycles++;
            }
            length++;
            System.out.println();
        }
        for (int i = 1; i <= w_value; i++) {
            System.out.print(i);
            if (i != w_value) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        total_cycles++;

        int uresult = w_value * w_value - 3 * w_value + 3;
        System.out.printf("Found cycles: %d ... Expected cycles: %d%n", total_cycles, uresult);
    }
}