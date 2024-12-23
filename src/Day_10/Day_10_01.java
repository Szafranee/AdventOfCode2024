package Day_10;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_10_01 {
    public static void main(String[] args) {
        int[][] topographicMap = getTopographicMap("src/Day_10/data_10.txt");

        int totalTrailheadScore = calculateTotalTrailheadScore(topographicMap);

        System.out.println("Total trailhead score: " + totalTrailheadScore);
    }

    static int[][] getTopographicMap(String filePath) {
        List<String> linesInFile;
        Path dataFilePath = Path.of(filePath);

        try (BufferedReader bufferedReader = Files.newBufferedReader(dataFilePath)) {
            linesInFile = bufferedReader.lines().toList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        int numRows = linesInFile.size();
        int numCols = linesInFile.getFirst().length();
        int[][] topographicMap = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String line = linesInFile.get(i);
            for (int j = 0; j < numCols; j++) {
                topographicMap[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        return topographicMap;
    }

    static List<int[]> findTrailheads(int[][] topographicMap) {
        List<int[]> trailheads = new ArrayList<>();
        for (int i = 0; i < topographicMap.length; i++) {
            for (int j = 0; j < topographicMap[0].length; j++) {
                if (topographicMap[i][j] == 0) {
                    trailheads.add(new int[]{i, j});
                }
            }
        }
        return trailheads;
    }

    static Set<int[]> findReachableNines(int[][] topographicMap, int startX, int startY) {
        Set<int[]> reachableNines = new HashSet<>();
        boolean[][] visited = new boolean[topographicMap.length][topographicMap[0].length];
        dfs(topographicMap, startX, startY, 0, reachableNines, visited);
        return reachableNines;
    }

    static void dfs(int[][] map, int x, int y, int currentHeight, Set<int[]> reachableNines, boolean[][] visited) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || visited[x][y] || map[x][y] != currentHeight) {
            return;
        }
        visited[x][y] = true;
        if (map[x][y] == 9) {
            reachableNines.add(new int[]{x, y});
        }
        dfs(map, x + 1, y, currentHeight + 1, reachableNines, visited);
        dfs(map, x - 1, y, currentHeight + 1, reachableNines, visited);
        dfs(map, x, y + 1, currentHeight + 1, reachableNines, visited);
        dfs(map, x, y - 1, currentHeight + 1, reachableNines, visited);
    }

    static int calculateTrailheadScore(int[][] topographicMap, int startX, int startY) {
        Set<int[]> reachableNines = findReachableNines(topographicMap, startX, startY);
        return reachableNines.size();
    }

    static int calculateTotalTrailheadScore(int[][] topographicMap) {
        List<int[]> trailheads = findTrailheads(topographicMap);
        int totalScore = 0;
        for (int[] trailhead : trailheads) {
            totalScore += calculateTrailheadScore(topographicMap, trailhead[0], trailhead[1]);
        }
        return totalScore;
    }
}