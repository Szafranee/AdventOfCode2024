package Day_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day_09_01 {
    public static void main(String[] args) {
        String line = "";
        try (var bufferedReader = Files.newBufferedReader(Path.of("src/Day_09/data_09.txt"))) {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        char[] lineCharArray = line.toCharArray();

        ArrayList<Integer> diskMap = mapToDiskMap(lineCharArray);

        compressDisk(diskMap);

        long checksum = calculateChecksum(diskMap);

        System.out.println("Checksum: " + checksum);

    }

    private static long calculateChecksum(ArrayList<Integer> diskMap) {
        long checkSum = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            checkSum += (long) i * diskMap.get(i);
        }
        return checkSum;
    }

    private static void compressDisk(ArrayList<Integer> diskMap) {
        int lastFreeSpace = 0;
        for (int i = diskMap.size() - 1; i >= 0; i--) {
            if (diskMap.get(i) != -1) {
                for (int j = lastFreeSpace; j < diskMap.size(); j++) {
                    if (j >= i) {
                        diskMap.removeIf(integer -> integer == -1);
                        return;
                    }
                    if (diskMap.get(j) == -1) {
                        diskMap.set(j, diskMap.get(i));
                        diskMap.set(i, -1);
                        lastFreeSpace = j;
                        break;
                    }
                }
            }
        }
    }

    static ArrayList<Integer> mapToDiskMap(char[] lineCharArray) {
        ArrayList<Integer> diskMap = new ArrayList<>();
        int fileId = 0;
        for (int i = 0; i < lineCharArray.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < Character.getNumericValue(lineCharArray[i]); j++) {
                    diskMap.add(fileId);
                }
                fileId++;
            } else {
                for (int j = 0; j < Character.getNumericValue(lineCharArray[i]); j++) {
                    diskMap.add(-1);
                }
            }
        }
        return diskMap;
    }
}
