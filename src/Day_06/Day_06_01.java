package Day_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day_06_01 {
    public static void main(String[] args) {
        Path dataFilePath = Path.of("src/Day_06/data_06.txt");
        List<String> linesInFile;

        try {
            linesInFile = Files.readAllLines(dataFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int numRows = linesInFile.size();
        int numCols = linesInFile.getFirst().length();
        char[][] labMap = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            labMap[i] = linesInFile.get(i).toCharArray();
        }

        int guardPositions = countGuardPositions(labMap);

        System.out.println("Number of guard positions: " + guardPositions);
    }

    public static int countGuardPositions(char[][] labMap) {
        int guardStartPosY;
        int guardStartPosX;

        int[] guardStartPos = findGuardStartPosition(labMap);
        guardStartPosY = guardStartPos[0];
        guardStartPosX = guardStartPos[1];

        if (guardStartPosY == -1 || guardStartPosX == -1) {
            System.out.println("No guard position found in the map");
            return 0;
        }

        int guardPosY = guardStartPosY;
        int guardPosX = guardStartPosX;
        String direction = "up";

        // Mark the guard's starting position
        labMap[guardPosX][guardPosY] = 'X';

        while (guardPosY < labMap[0].length || guardPosX < labMap.length) {
            try {
                switch (direction) {
                    case "up" -> {
                        if (labMap[guardPosY - 1][guardPosX] == '#') {
                            direction = "right";
                        } else {
                            guardPosY--;
                            labMap[guardPosY][guardPosX] = 'X';}
                    }
                    case "right" -> {
                        if (labMap[guardPosY][guardPosX + 1] == '#') {
                            direction = "down";
                        } else {
                            guardPosX++;
                            labMap[guardPosY][guardPosX] = 'X';
                        }
                    }
                    case "down" -> {
                        if (labMap[guardPosY + 1][guardPosX] == '#') {
                            direction = "left";
                        } else {
                            guardPosY++;
                            labMap[guardPosY][guardPosX] = 'X';
                        }
                    }
                    case "left" -> {
                        if (labMap[guardPosY][guardPosX - 1] == '#') {
                            direction = "up";
                        } else {
                            guardPosX--;
                            labMap[guardPosY][guardPosX] = 'X';
                        }
                    }
                }
            } catch (IndexOutOfBoundsException _) {
//                System.out.println("Guard has reached the end of the map");
                break;
            }
        }

        int xCount = 0;

        for (char[] chars : labMap) {
            for (char aChar : chars) {
                if (aChar == 'X') {
                    xCount++;
                }
            }
        }

        return xCount;
    }

    private static int[] findGuardStartPosition(char[][] labMap) {
        for (int i = 0; i < labMap.length; i++) {
            for (int j = 0; j < labMap[i].length; j++) {
                if (labMap[i][j] == '^') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Default return if no position is found
    }
}