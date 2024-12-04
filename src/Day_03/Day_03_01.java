package Day_03;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_03_01 {
    public static void main(String[] args) {
        ArrayList<String> correctMuls = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/Day_03/data_03.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher mulMatcher = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)").matcher(line);

               while (mulMatcher.find()) {
                   correctMuls.add(mulMatcher.group());
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int result = doAndSumMuls(correctMuls);
        System.out.println("Result: " + result);
    }

    public static int doAndSumMuls(ArrayList<String> mulsToSum) {
        int result = 0;

        for (var mul : mulsToSum) {
            Matcher numMatcher = Pattern.compile("\\d{1,3}").matcher(mul);
            int[] numsToMul = new int[2];
            int i = 0;

            while (numMatcher.find()) {
                numsToMul[i++] = Integer.parseInt(numMatcher.group());
            }

            result += (numsToMul[0] * numsToMul[1]);
        }
        return result;
    }
}
