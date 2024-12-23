package Day_11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_11_02 {
    public static void main(String[] args) {
        List<Long> stones = Day_11_01.getStones("src/Day_11/data_11.txt");
        long finalCount = countStonesAfterNIterations(stones, 75);
        System.out.println("Number of stones: " + finalCount);
    }

    static long countStonesAfterNIterations(List<Long> stones, int n) {
        Map<Long, Long> stoneMap = new HashMap<>();
        for (Long stone : stones) {
            stoneMap.merge(stone, 1L, Long::sum);
        }

        for (int i = 0; i < n; i++) {
            Map<Long, Long> nextMap = new HashMap<>();

            for (Map.Entry<Long, Long> entry : stoneMap.entrySet()) {
                long value = entry.getKey();
                long count = entry.getValue();

                if (value == 0) {
                    nextMap.merge(1L, count, Long::sum);
                } else if (value < 10) {
                    nextMap.merge(value * 2024, count, Long::sum);
                } else {
                    String strValue = String.valueOf(value);
                    if (strValue.length() % 2 == 0) {
                        int mid = strValue.length() / 2;
                        long left = Long.parseLong(strValue.substring(0, mid));
                        long right = Long.parseLong(strValue.substring(mid));
                        nextMap.merge(left, count, Long::sum);
                        nextMap.merge(right, count, Long::sum);
                    } else {
                        nextMap.merge(value * 2024, count, Long::sum);
                    }
                }
            }
            stoneMap = nextMap;
        }

        return stoneMap.values().stream().mapToLong(Long::longValue).sum();
    }
}