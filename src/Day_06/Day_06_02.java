package Day_06;

import java.util.HashSet;
import java.util.Map;

public class Day_06_02 {
    public static void main(String[] args) {
        char[][] labMap = Day_06_01.getLabMap("src/Day_06/data_06.txt");
        if (labMap == null) {
            return;
        }

        int[] guardStartPos = Day_06_01.findGuardStartPosition(labMap);
        int guardStartPosY = guardStartPos[0];
        int guardStartPosX = guardStartPos[1];

        int possibleObstructionsCount = countPossibleObstructions(labMap, guardStartPosY, guardStartPosX);

        System.out.println("Number of possible obstructions: " + possibleObstructionsCount);
    }

    private static int countPossibleObstructions(char[][] labMap, int guardStartPosY, int guardStartPosX) {
        int possibleObstructionsCount = 0;

        HashSet<Map.Entry<Integer, Integer>> guardPath = findGuardPath(labMap, guardStartPosY, guardStartPosX);

        for (var guardPos : guardPath) {
            int guardPosY = guardPos.getKey();
            int guardPosX = guardPos.getValue();

            labMap[guardPosY][guardPosX] = '#';

            if (checkIfGuardInLoop(labMap, guardStartPosY, guardStartPosX)) {
                possibleObstructionsCount++;
            }

            // Restore the original state (we don't want to copy the array every time)
            labMap[guardPosY][guardPosX] = '.';
        }
        return possibleObstructionsCount;
    }

    private static boolean checkIfGuardInLoop(char[][] labMap, int guardStartPosY, int guardStartPosX) {
        if (guardStartPosY == -1 || guardStartPosX == -1) {
            System.out.println("No guard position found in the map");
            return false;
        }

        int guardPosY = guardStartPosY;
        int guardPosX = guardStartPosX;
        Direction direction = Direction.UP;

        //                              position        direction
        HashSet<Map.Entry<Map.Entry<Integer, Integer>, Direction>> visited = new HashSet<>();
        visited.add(Map.entry(Map.entry(guardPosY, guardPosX), direction));

        while (true) {
            try {
                switch (direction) {
                    case UP -> {
                        if (labMap[guardPosY - 1][guardPosX] == '#') {
                            direction = Direction.RIGHT;
                        } else {
                            guardPosY--;
                            if (visited.contains(Map.entry(Map.entry(guardPosY, guardPosX), direction))) {
                                return true;
                            }
                            visited.add(Map.entry(Map.entry(guardPosY, guardPosX), direction));
                        }
                    }
                    case RIGHT -> {
                        if (labMap[guardPosY][guardPosX + 1] == '#') {
                            direction = Direction.DOWN;
                        } else {
                            guardPosX++;
                            if (visited.contains(Map.entry(Map.entry(guardPosY, guardPosX), direction))) {
                                return true;
                            }
                            visited.add(Map.entry(Map.entry(guardPosY, guardPosX), direction));
                        }
                    }
                    case DOWN -> {
                        if (labMap[guardPosY + 1][guardPosX] == '#') {
                            direction = Direction.LEFT;
                        } else {
                            guardPosY++;
                            if (visited.contains(Map.entry(Map.entry(guardPosY, guardPosX), direction))) {
                                return true;
                            }
                            visited.add(Map.entry(Map.entry(guardPosY, guardPosX), direction));
                        }
                    }
                    case LEFT -> {
                        if (labMap[guardPosY][guardPosX - 1] == '#') {
                            direction = Direction.UP;
                        } else {
                            guardPosX--;
                            if (visited.contains(Map.entry(Map.entry(guardPosY, guardPosX), direction))) {
                                return true;
                            }
                            visited.add(Map.entry(Map.entry(guardPosY, guardPosX), direction));
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        return false;
    }

    private static HashSet<Map.Entry<Integer, Integer>> findGuardPath(char[][] labMap, int guardStartPosY, int guardStartPosX) {
        if (guardStartPosY == -1 || guardStartPosX == -1) {
            System.out.println("No guard position found in the map");
            return new HashSet<>();
        }

        int guardPosY = guardStartPosY;
        int guardPosX = guardStartPosX;
        Direction direction = Direction.UP;

        HashSet<Map.Entry<Integer, Integer>> guardPath = new HashSet<>();

        while (true) {
            try {
                switch (direction) {
                    case UP -> {
                        if (labMap[guardPosY - 1][guardPosX] == '#') {
                            direction = Direction.RIGHT;
                        } else {
                            guardPosY--;
                            labMap[guardPosY][guardPosX] = 'X';
                            guardPath.add(Map.entry(guardPosY, guardPosX));
                        }
                    }
                    case RIGHT -> {
                        if (labMap[guardPosY][guardPosX + 1] == '#') {
                            direction = Direction.DOWN;
                        } else {
                            guardPosX++;
                            labMap[guardPosY][guardPosX] = 'X';
                            guardPath.add(Map.entry(guardPosY, guardPosX));
                        }
                    }
                    case DOWN -> {
                        if (labMap[guardPosY + 1][guardPosX] == '#') {
                            direction = Direction.LEFT;
                        } else {
                            guardPosY++;
                            labMap[guardPosY][guardPosX] = 'X';
                            guardPath.add(Map.entry(guardPosY, guardPosX));
                        }
                    }
                    case LEFT -> {
                        if (labMap[guardPosY][guardPosX - 1] == '#') {
                            direction = Direction.UP;
                        } else {
                            guardPosX--;
                            labMap[guardPosY][guardPosX] = 'X';
                            guardPath.add(Map.entry(guardPosY, guardPosX));
                        }
                    }
                }
            } catch (IndexOutOfBoundsException _) {
                break;
            }
        }

        return guardPath;
    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
}