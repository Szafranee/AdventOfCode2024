package Day_02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.util.Arrays;

public class Day_02_01 {
    public static void main(String[] args) {
        int safeReportCounter = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/Day_02/data_02.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int[] levels = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();

                if (Math.abs(levels[0] - levels[1]) <= 3) {
                    if (levels[0] < levels[1] && isIncreasing(levels)) {
                        safeReportCounter++;
                    } else if (levels[0] > levels[1] && isDecreasing(levels)) {
                        safeReportCounter++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Number of safe reports: " + safeReportCounter);
    }

    private static boolean isIncreasing(int[] levels) {
        for (int i = 1; i < levels.length - 1; i++) {
            if (levels[i] >= levels[i + 1] || Math.abs(levels[i] - levels[i + 1]) > 3) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDecreasing(int[] levels) {
        for (int i = 1; i < levels.length - 1; i++) {
            if (levels[i] <= levels[i + 1] || Math.abs(levels[i] - levels[i + 1]) > 3) {
                return false;
            }
        }
        return true;
    }
}
