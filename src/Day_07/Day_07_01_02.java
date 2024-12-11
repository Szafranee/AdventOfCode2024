package Day_07;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day_07_01_02 {

    public static void main(String[] args) {
        Path dataFilePath = Path.of("src/Day_07/data_07.txt");

        Set<Equation> equations = new HashSet<>();

        try (BufferedReader bufferedReader = Files.newBufferedReader(dataFilePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] separatedLine = line.split(" ");

                Long equationResult = Long.parseLong(removeLastChar(separatedLine[0].strip()));

                List<Integer> equationComponents = Arrays.stream(separatedLine, 1, separatedLine.length)
                        .map(Integer::parseInt)
                        .toList();

                equations.add(new Equation(equationResult, equationComponents));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Equation> correctEquations = findCorrectEquations(equations);

        long totalCalibrationResult = getTotalCalibrationResult(correctEquations);

        System.out.println("Total calibration result: " + totalCalibrationResult);
    }

    private static long getTotalCalibrationResult(Set<Equation> correctEquations) {
        long sum = 0;
        for (var equation : correctEquations) {
            sum += equation.result;
        }
        return sum;
    }

    private static Set<Equation> findCorrectEquations(Set<Equation> equations) {
        Set<Equation> correctEquations = new HashSet<>();

        for (Equation equation : equations) {
            List<List<Character>> operatorCombinations = new ArrayList<>();
            generateOperators(new ArrayList<>(), 0, equation.components.size() - 1, operatorCombinations);

            for (List<Character> operatorCombination : operatorCombinations) {
                long result = getEquationResult(equation, operatorCombination);

                if (result == equation.result) {
                    correctEquations.add(equation);
                    break;
                }
            }
        }

        return correctEquations;
    }

    private static void generateOperators(List<Character> currOperators, int operatorsCount, int operatorsNeeded, List<List<Character>> operatorCombinations) {
        if (operatorsCount == operatorsNeeded) {
            operatorCombinations.add(new ArrayList<>(currOperators));
            return;
        }

        for (char operator : List.of('+', '*', '|')) { // "|" for part 2
            currOperators.add(operator);
            generateOperators(currOperators, operatorsCount + 1, operatorsNeeded, operatorCombinations);
            currOperators.removeLast();
        }
    }

    private static long getEquationResult(Equation equation, List<Character> operatorCombination) {
        long result = equation.components.getFirst();
        for (int i = 0; i < operatorCombination.size(); i++) {
            char operator = operatorCombination.get(i);
            int component = equation.components.get(i + 1);

            if (operator == '+') {
                result += component;
            } else if (operator == '*') {
                result *= component;
            } else if (operator == '|') { // part 2 here
                result = Long.parseLong(result + Long.toString(component));
            }
        }
        return result;
    }

    public static String removeLastChar(String s) {
        return (s == null || s.isEmpty()) ? null : (s.substring(0, s.length() - 1));
    }

    record Equation(Long result, List<Integer> components) {
    }
}
