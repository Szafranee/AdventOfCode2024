package Day_01;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day_01_02 {
    public static void main(String[] args) {
        ArrayList<Integer> locationsA = new ArrayList<>();
        ArrayList<Integer> locationsB = new ArrayList<>();
        int similarityScore = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader("src/Day_01/data_01.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" {3}");
                locationsA.add(Integer.parseInt(parts[0]));
                locationsB.add(Integer.parseInt(parts[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<Integer, Integer> locationsCount = new HashMap<>();

        Arrays.stream(locationsB.toArray()).collect(Collectors.groupingBy(l -> l)).forEach((k, v) -> locationsCount.put((Integer) k, v.size()));

        for (var loc : locationsA) {
            Integer locCount = locationsCount.get(loc);
            if (locCount != null) {
                similarityScore += locCount * loc;
            }
        }

        System.out.println(similarityScore);
    }
}
