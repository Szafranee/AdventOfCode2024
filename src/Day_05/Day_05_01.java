package Day_05;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day_05_01 {
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

        List<Map.Entry<Integer, Integer>> rules = mapRules(printRules);
        List<List<Integer>> updatesList = toIntLists(printUpdates);

        List<List<Integer>> correctUpdates = findCorrectUpdates(rules, updatesList);

        int sumOfMiddlePages = sumMiddlePages(correctUpdates);
        System.out.println("Sum of middle pages: " + sumOfMiddlePages);
    }

    public static List<Map.Entry<Integer, Integer>> mapRules(List<String> rulesToMap) {
        return rulesToMap.stream()
                .map(s -> s.split("\\|"))
                .map(arr -> Map.entry(Integer.parseInt(arr[0].trim()), Integer.parseInt(arr[1].trim())))
                .collect(Collectors.toList());
    }

    public static List<List<Integer>> toIntLists(List<String> stringList) {
        List<List<Integer>> intList = new ArrayList<>();
        for (String s : stringList) {
            intList.add(Stream.of(s.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
        }
        return intList;
    }

    public static List<List<Integer>> findCorrectUpdates(List<Map.Entry<Integer, Integer>> rules, List<List<Integer>> updatesList) {
        List<List<Integer>> correctUpdates = new ArrayList<>();

        for (List<Integer> update : updatesList) {
            Set<Integer> printedPages = new HashSet<>();
            boolean isValidUpdate = true;

            for (int page : update) {
                List<Map.Entry<Integer, Integer>> printAfterRules = rules.stream()
                        .filter((entry) -> entry.getKey() == page)
                        .toList();

                List<Map.Entry<Integer, Integer>> printBeforeRules = rules.stream()
                        .filter((entry) -> entry.getValue() == page)
                        .toList();

               for (var rule : printAfterRules) {
                   if (!printedPages.contains(rule.getValue())) {
                       if (rule == printAfterRules.getLast()) {
                           printedPages.add(page);
                       }
                   } else  {
                       isValidUpdate = false;
                       break;
                   }
               }

                for (var rule : printBeforeRules) {
                    if (!printedPages.contains(rule.getKey()) && update.contains(rule.getKey())) {
                        isValidUpdate = false;
                        break;
                    } else {
                        if (rule == printBeforeRules.getLast()) {
                            printedPages.add(page);
                        }
                    }
                }

                if (!isValidUpdate) {
                    break;
                }

            }

            if (isValidUpdate) {
                correctUpdates.add(update);
            }
        }

        return correctUpdates;
    }

    public static int sumMiddlePages(List<List<Integer>> updatesToSum) {
        return updatesToSum.stream()
                .mapToInt(list -> list.get(list.size() / 2))
                .sum();
    }
}
