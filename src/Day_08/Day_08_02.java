package Day_08;

import java.util.*;

public class Day_08_02 {
    public static void main(String[] args) {
        char[][] antennasMap = Day_08_01.getAntennasMap("src/Day_08/data_08.txt");
        if (antennasMap == null) {
            return;
        }

        int[][] antinodesMap = new int[antennasMap.length][antennasMap[0].length];

        int antinodesCount = getAntinodesCount(antennasMap, antinodesMap);

        System.out.println("Number of antinodes: " + antinodesCount);
    }

    static int getAntinodesCount(char[][] antennasMap, int[][] antinodesMap) {

        Map<Character, List<int[]>> antennasPositions = new HashMap<>();

        for (int i = 0; i < antennasMap.length; i++) {
            for (int j = 0; j < antennasMap[0].length; j++) {
                char antenna = antennasMap[i][j];
                if (antenna != '.') {
                    antennasPositions.putIfAbsent(antenna, new ArrayList<>());
                    antennasPositions.get(antenna).add(new int[]{i, j});
                }
            }
        }

        for (var antenna : antennasPositions.entrySet()) {
            for (int i = 0; i < antenna.getValue().size(); i++) {
                for (int j = i + 1; j < antenna.getValue().size(); j++) {
                    int[] firstAntennaPosition = antenna.getValue().get(i);
                    int[] secondAntennaPosition = antenna.getValue().get(j);

                    int x1 = firstAntennaPosition[0];
                    int y1 = firstAntennaPosition[1];

                    int x2 = secondAntennaPosition[0];
                    int y2 = secondAntennaPosition[1];

                    int dx = x2 - x1;
                    int dy = y2 - y1;

                    int newX1 = x1 - dx;
                    int newY1 = y1 - dy;
                    int newX2 = x2 + dx;
                    int newY2 = y2 + dy;

                    antinodesMap[x1][y1] = 1;
                    antinodesMap[x2][y2] = 1;

                    while (isValidPosition(newX1, newY1, antinodesMap)) {
                        antinodesMap[newX1][newY1] = 1;
                        newX1 -= dx;
                        newY1 -= dy;
                    }

                    while (isValidPosition(newX2, newY2, antinodesMap)) {
                        antinodesMap[newX2][newY2] = 1;
                        newX2 += dx;
                        newY2 += dy;
                    }
                }
            }
        }

        return Arrays.stream(antinodesMap).flatMapToInt(Arrays::stream).sum();
    }

    private static boolean isValidPosition(int x, int y, int[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }
}
