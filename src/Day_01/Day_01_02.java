package Day_01;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Day_01_02 {
    public static void main(String[] args) {
        ArrayList<Integer> locationsA = new ArrayList<>();
        HashMap<Integer, Integer> locationsB = new HashMap<>();
        int similarityScore = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader("src/Day_01/data_01.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" {3}");
                locationsA.add(Integer.parseInt(parts[0]));
                locationsB.put(Integer.parseInt(parts[1]), locationsB.getOrDefault(Integer.parseInt(parts[1]), 0) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (var loc : locationsA) {
            Integer locCount = locationsB.get(loc);
            if (locCount != null) {
                similarityScore += locCount * loc;
            }
        }

        System.out.println("Similarity Score: " + similarityScore);
    }
}