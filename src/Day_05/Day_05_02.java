package Day_05;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day_05_02 {
    public static void main(String[] args) {
        Path dataFilePath = Path.of("src/Day_05/data_05.txt");
        ArrayList<String> printRules = new ArrayList<>();
        ArrayList<String> printUpdates = new ArrayList<>();
        boolean addRules = true;

        try (BufferedReader bufferedReader = Files.newBufferedReader(dataFilePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    addRules = false;
                    continue;
                }

                if (addRules) {
                    printRules.add(line);
                } else {
                    printUpdates.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map.Entry<Integer, Integer>> rules = Day_05_01.mapRules(printRules);
        List<List<Integer>> updatesList = Day_05_01.toIntLists(printUpdates);

        List<List<Integer>> correctUpdates = Day_05_01.findCorrectUpdates(rules, updatesList);

        // leaves only incorrect reports
        updatesList.removeAll(correctUpdates);

        List<List<Integer>> fixedUpdates = fixIncorrectUpdates(updatesList, rules);

        int sumOfMiddlePages = Day_05_01.sumMiddlePages(fixedUpdates);
        System.out.println("Sum of middle pages: " + sumOfMiddlePages);
    }

    public static List<List<Integer>> fixIncorrectUpdates(List<List<Integer>> updatesToFix, List<Map.Entry<Integer, Integer>> rules) {
        List<List<Integer>> fixedUpdates = new ArrayList<>();

        for (var update : updatesToFix) {
            List<Integer> sortedUpdate = new ArrayList<>(update);
            sortedUpdate.sort((page1, page2) -> {
                if (page1.equals(page2)) return 0;

                for (var rule : rules) {
                    if (rule.getKey().equals(page1) && rule.getValue().equals(page2)) {
                        return -1;
                    } else if (rule.getKey().equals(page2) && rule.getValue().equals(page1)) {
                        return 1;
                    }
                }
                return 0;
            });
            fixedUpdates.add(sortedUpdate);
        }

        return fixedUpdates;
    }
}