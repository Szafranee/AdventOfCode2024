package Day_09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Day_09_02 {
    public static void main(String[] args) {
        String line = "";
        try (var bufferedReader = Files.newBufferedReader(Path.of("src/Day_09/data_09.txt"))) {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        char[] lineCharArray = line.toCharArray();

        ArrayList<Integer> diskMap = Day_09_01.mapToDiskMap(lineCharArray);

        compressDisk(diskMap);

        long checksum = calculateChecksum(diskMap);

        System.out.println("Checksum: " + checksum);
    }

    private static long calculateChecksum(ArrayList<Integer> diskMap) {
        long checkSum = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            if (diskMap.get(i) != -1) {
                checkSum += (long) i * diskMap.get(i);
            }
        }
        return checkSum;
    }

    private static void compressDisk(ArrayList<Integer> diskMap) {
        int maxFileId = Collections.max(diskMap);
        for (int fileId = maxFileId; fileId >= 0; fileId--) {
            int fileLength = 0;
            int fileStart = -1;
            for (int i = 0; i < diskMap.size(); i++) {
                if (diskMap.get(i) == fileId) {
                    if (fileStart == -1) {
                        fileStart = i;
                    }
                    fileLength++;
                } else if (fileStart != -1) {
                    break;
                }
            }
            if (fileStart != -1) {
                int freeSpaceStart = findFreeSpace(diskMap, fileLength);
                if (freeSpaceStart != -1 && freeSpaceStart < fileStart) {
                    for (int i = 0; i < fileLength; i++) {
                        diskMap.set(freeSpaceStart + i, fileId);
                        diskMap.set(fileStart + i, -1);
                    }
                }
            }
        }
    }

    private static int findFreeSpace(ArrayList<Integer> diskMap, int length) {
        int freeSpaceLength = 0;
        for (int i = 0; i < diskMap.size(); i++) {
            if (diskMap.get(i) == -1) {
                freeSpaceLength++;
                if (freeSpaceLength == length) {
                    return i - length + 1;
                }
            } else {
                freeSpaceLength = 0;
            }
        }
        return -1;
    }
}