package Day_04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day_04_01 {
    public static void main(String[] args) {
        Path dataFilePath = Path.of("src/Day_04/data_04.txt");
        List<String> linesInFile;

        try {
            linesInFile = Files.readAllLines(dataFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int numRows = linesInFile.size();
        int numCols = linesInFile.getFirst().length();
        char[][] charsInFile = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            charsInFile[i] = linesInFile.get(i).toCharArray();
        }

        List<String> directions = List.of("right", "left", "up", "down", "up-right", "down-right", "up-left", "down-left");

        int xmasCount = countXmas(charsInFile, directions);

        System.out.println("Number of \"XMAS\" strings found: " + xmasCount);
    }

    public static int countXmas(char[][] chars, List<String> directions) {
        int xmasCount = 0;

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (chars[i][j] == 'X') {
                    for (String direction : directions) {
                        if (checkForXmas(chars, i, j, direction)) {
                            xmasCount++;
                        }
                    }
                }
            }
        }
        return xmasCount;
    }

    private static boolean checkForXmas(char[][] chars, int currI, int currJ, String direction) {
        try {
            return switch (direction) {
                case "right" -> chars[currI][currJ + 1] == 'M' && chars[currI][currJ + 2] == 'A' && chars[currI][currJ + 3] == 'S';
                case "left" -> chars[currI][currJ - 1] == 'M' && chars[currI][currJ - 2] == 'A' && chars[currI][currJ - 3] == 'S';
                case "up" -> chars[currI - 1][currJ] == 'M' && chars[currI - 2][currJ] == 'A' && chars[currI - 3][currJ] == 'S';
                case "down" -> chars[currI + 1][currJ] == 'M' && chars[currI + 2][currJ] == 'A' && chars[currI + 3][currJ] == 'S';
                case "up-right" -> chars[currI - 1][currJ + 1] == 'M' && chars[currI - 2][currJ + 2] == 'A' && chars[currI - 3][currJ + 3] == 'S';
                case "down-right" -> chars[currI + 1][currJ + 1] == 'M' && chars[currI + 2][currJ + 2] == 'A' && chars[currI + 3][currJ + 3] == 'S';
                case "up-left" -> chars[currI - 1][currJ - 1] == 'M' && chars[currI - 2][currJ - 2] == 'A' && chars[currI - 3][currJ - 3] == 'S';
                case "down-left" -> chars[currI + 1][currJ - 1] == 'M' && chars[currI + 2][currJ - 2] == 'A' && chars[currI + 3][currJ - 3] == 'S';
                default -> false;
            };
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}