package Day_04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day_04_02 {

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

        int xmasCount = countXmas(charsInFile);

        System.out.println("Number of \"XMAS\" strings found: " + xmasCount);
    }

    public static int countXmas(char[][] chars) {
        int xmasCount = 0;

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (chars[i][j] == 'A') {
                    if (checkForXmas(chars, i, j)) {
                        xmasCount++;
                    }
                }
            }
        }
        return xmasCount;
    }

    private static boolean checkForXmas(char[][] chars, int currI, int currJ) {
    /*  "-1, -1"  "-1, +1"
             "0, 0"
        "+1, -1"  "+1, +1"  */

        try {
            // MAS MAS
            if (chars[currI - 1][currJ - 1] == 'M'
                    && chars[currI - 1][currJ + 1] == 'M'
                    && chars[currI + 1][currJ - 1] == 'S'
                    && chars[currI + 1][currJ + 1] == 'S'
            ) {
                return true;
            }

            // MAS SAM
            if (chars[currI - 1][currJ - 1] == 'M'
                    && chars[currI - 1][currJ + 1] == 'S'
                    && chars[currI + 1][currJ - 1] == 'M'
                    && chars[currI + 1][currJ + 1] == 'S'
            ) {
                return true;
            }

            // SAM SAM
            if (chars[currI - 1][currJ - 1] == 'S'
                    && chars[currI - 1][currJ + 1] == 'S'
                    && chars[currI + 1][currJ - 1] == 'M'
                    && chars[currI + 1][currJ + 1] == 'M'
            ) {
                return true;
            }

            // SAM MAS
            if (chars[currI - 1][currJ - 1] == 'S'
                    && chars[currI - 1][currJ + 1] == 'M'
                    && chars[currI + 1][currJ - 1] == 'S'
                    && chars[currI + 1][currJ + 1] == 'M'
            ) {
                return true;
            }

        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}