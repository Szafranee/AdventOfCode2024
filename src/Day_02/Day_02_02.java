package Day_02;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day_02_02 {
    public static void main(String[] args) {
        int safeReportCounter = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/Day_02/data_02.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<Integer> levels = Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new));

                if (isSafe(levels)) {
                    safeReportCounter++;
                    continue;
                }

                if (canBeMadeSafe(levels)) {
                    safeReportCounter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Number of safe reports: " + safeReportCounter);
    }

    private static boolean isSafe(ArrayList<Integer> levels) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 0; i < levels.size() - 1; i++) {
            int diff = levels.get(i) - levels.get(i + 1);

            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            if (diff < 0) {
                isDecreasing = false;
            } else if (diff > 0) {
                isIncreasing = false;
            } else {
                return false;
            }
        }

        return isIncreasing || isDecreasing;
    }

    // we check every possibility
    private static boolean canBeMadeSafe(ArrayList<Integer> levels) {
        for (int i = 0; i < levels.size(); i++) {
            ArrayList<Integer> newLevels = new ArrayList<>(levels);
            newLevels.remove(i);
            if (isSafe(newLevels)) {
                return true;
            }
        }
        return false;
    }
}