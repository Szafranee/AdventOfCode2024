package Day_01;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;

public class taks_01_01 {
    public static void main(String[] args) {
        ArrayList<Integer> locationsA = new ArrayList<>();
        ArrayList<Integer> locationsB = new ArrayList<>();
        int totalDistance = 0;

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

        Collections.sort(locationsA);
        Collections.sort(locationsB);

        for (int i = 0; i < locationsA.size(); i++) {
            totalDistance += Math.abs(locationsA.get(i) - locationsB.get(i));
        }

        System.out.println(totalDistance);

    }
}
