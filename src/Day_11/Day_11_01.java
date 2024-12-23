package Day_11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day_11_01 {
    public static void main(String[] args) {
        List<Long> stones = getStones("src/Day_11/data_11.txt");

        List<Long> finalStones = applyRulesNTimes(stones, 25);

        int stonesCount = finalStones.size();

        System.out.println("Number of stones: " + stonesCount);
    }

    static List<Long> getStones(String filePath) {
        List<Long> stones = new ArrayList<>();
        String line = "";
        try (var bufferedReader = Files.newBufferedReader(Path.of(filePath))) {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String[] stoneStrings = line.split(" ");

        for (String stoneString : stoneStrings) {
            stones.add(Long.parseLong(stoneString));
        }


        return stones;
    }

    static List<Long> applyRules(List<Long> stones) {
        List<Long> newStones = new ArrayList<>();
        for (long stone : stones) {
            if (stone == 0) {
                newStones.add(1L);
            } else if (String.valueOf(stone).length() % 2 == 0) {
                String stoneString = Long.toString(stone);
                int halfLength = stoneString.length() / 2;
                long leftStone = Long.parseLong(stoneString.substring(0, halfLength));
                long rightStone = Long.parseLong(stoneString.substring(halfLength));
                newStones.add(leftStone);
                newStones.add(rightStone);
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }

    static List<Long> applyRulesNTimes(List<Long> stones, int n) {
        List<Long> newStones = stones;
        for (int i = 0; i < n; i++) {
            System.out.println("Applying rules for the " + i + "th time");
            newStones = applyRules(newStones);
        }
        return newStones;
    }
}
