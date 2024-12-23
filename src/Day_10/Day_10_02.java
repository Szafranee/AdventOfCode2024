package Day_10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_10_02 {
    public static void main(String[] args) {
        int[][] topographicMap = Day_10_01.getTopographicMap("src/Day_10/data_10.txt");
        int totalRating = calculateTotalTrailheadRating(topographicMap);
        System.out.println("Total Rating: " + totalRating);
    }

    static Set<String> findDistinctTrails(int[][] topographicMap, int startX, int startY) {
        Set<String> distinctTrails = new HashSet<>();
        boolean[][] visited = new boolean[topographicMap.length][topographicMap[0].length];
        dfs(topographicMap, startX, startY, 0, "", distinctTrails, visited);
        return distinctTrails;
    }

    static void dfs(int[][] map, int x, int y, int currentHeight, String path, Set<String> distinctTrails, boolean[][] visited) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || visited[x][y] || map[x][y] != currentHeight) {
            return;
        }
        visited[x][y] = true;
        path += x + "," + y + ";";
        if (map[x][y] == 9) {
            distinctTrails.add(path);
        }
        dfs(map, x + 1, y, currentHeight + 1, path, distinctTrails, visited);
        dfs(map, x - 1, y, currentHeight + 1, path, distinctTrails, visited);
        dfs(map, x, y + 1, currentHeight + 1, path, distinctTrails, visited);
        dfs(map, x, y - 1, currentHeight + 1, path, distinctTrails, visited);
        visited[x][y] = false;
    }

    static int calculateTrailheadRating(int[][] topographicMap, int startX, int startY) {
        Set<String> distinctTrails = findDistinctTrails(topographicMap, startX, startY);
        return distinctTrails.size();
    }

    static int calculateTotalTrailheadRating(int[][] topographicMap) {
        List<int[]> trailheads = Day_10_01.findTrailheads(topographicMap);
        int totalRating = 0;
        for (int[] trailhead : trailheads) {
            totalRating += calculateTrailheadRating(topographicMap, trailhead[0], trailhead[1]);
        }
        return totalRating;
    }
}