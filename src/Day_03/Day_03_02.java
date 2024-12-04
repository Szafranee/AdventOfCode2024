package Day_03;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_03_02 {
    public static void main(String[] args) {
        ArrayList<String> instructions = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of("src/Day_03/data_03.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher instructionsMatcher = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\))").matcher(line);

                while (instructionsMatcher.find()) {
                    instructions.add(instructionsMatcher.group());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(instructions);
        int result = processInstructions(instructions);
        System.out.println("Result: " + result);
    }

    public static int processInstructions(ArrayList<String> instructions) {
        int result = 0;
        boolean doInstructions = true;

        for (var instruction : instructions) {
            if (instruction.startsWith("do()")) {
                doInstructions = true;
            } else if (instruction.startsWith("don't()")) {
                doInstructions = false;
            }

            if (instruction.startsWith("mul") && doInstructions) {
                result = doMul(result, instruction);
            }
        }
        return result;
    }

    private static int doMul(int result, String mulToDo) {
        Matcher numMatcher = Pattern.compile("\\d{1,3}").matcher(mulToDo);
        int[] numsToMul = new int[2];
        int i = 0;

        while (numMatcher.find()) {
            numsToMul[i++] = Integer.parseInt(numMatcher.group());
        }

        result += (numsToMul[0] * numsToMul[1]);
        return result;
    }
}
