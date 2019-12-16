import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 7
 * @author Rishabh Ranjan
 */
public class AmplificationCircuit {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay7.txt"));
        String listString = sc.nextLine();
        for (int i = 0; i < listString.length(); i++) {
            if (listString.charAt(i) == ',') {
                listString = listString.substring(0, i) + " " + listString.substring(i + 1);
            }
        }
        Scanner sc2 = new Scanner(listString);
        List<Long> list = new ArrayList<Long>();
        while (sc2.hasNextLong()) {
            list.add(sc2.nextLong());
        }
        int max = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        for (int m = 0; m < 5; m++) {
                            if (j != i && k != i && k != j && l != i && l != j && l != k && m != i && m != j && m != k
                                && m != l) {
                                int output = 0;
                                int[] startIndex = new int[5];
                                long[] relativeBase = new long[5];
                                List<Long>[] listCopy = new ArrayList[5];
                                for (int n = 0; n < 5; n++) {
                                    listCopy[n] = new ArrayList<>(list);
                                }
                                Result result = IntcodeComputer.computer(listCopy[0], startIndex[0], relativeBase[0],
                                    true, i);
                                startIndex[0] = result.newStartIndex;
                                relativeBase[0] = result.newRelativeBase;
                                result = IntcodeComputer.computer(listCopy[0], startIndex[0], relativeBase[0],
                                    true, output);
                                startIndex[0] = result.newStartIndex;
                                relativeBase[0] = result.newRelativeBase;
                                output = (int) result.output;
                                result = IntcodeComputer.computer(listCopy[1], startIndex[1], relativeBase[1],
                                    true, j);
                                startIndex[1] = result.newStartIndex;
                                relativeBase[1] = result.newRelativeBase;
                                result = IntcodeComputer.computer(listCopy[1], startIndex[1], relativeBase[1],
                                    true, output);
                                startIndex[1] = result.newStartIndex;
                                relativeBase[1] = result.newRelativeBase;
                                output = (int) result.output;
                                result = IntcodeComputer.computer(listCopy[2], startIndex[2], relativeBase[2],
                                    true, k);
                                startIndex[2] = result.newStartIndex;
                                relativeBase[2] = result.newRelativeBase;
                                result = IntcodeComputer.computer(listCopy[2], startIndex[2], relativeBase[2],
                                    true, output);
                                startIndex[2] = result.newStartIndex;
                                relativeBase[2] = result.newRelativeBase;
                                output = (int) result.output;
                                result = IntcodeComputer.computer(listCopy[3], startIndex[3], relativeBase[3],
                                    true, l);
                                startIndex[3] = result.newStartIndex;
                                relativeBase[3] = result.newRelativeBase;
                                result = IntcodeComputer.computer(listCopy[3], startIndex[3], relativeBase[3],
                                    true, output);
                                startIndex[3] = result.newStartIndex;
                                relativeBase[3] = result.newRelativeBase;
                                output = (int) result.output;
                                result = IntcodeComputer.computer(listCopy[4], startIndex[4], relativeBase[4],
                                    true, m);
                                startIndex[4] = result.newStartIndex;
                                relativeBase[4] = result.newRelativeBase;
                                result = IntcodeComputer.computer(listCopy[4], startIndex[4], relativeBase[4],
                                    true, output);
                                startIndex[4] = result.newStartIndex;
                                relativeBase[4] = result.newRelativeBase;
                                output = (int) result.output;
                                if (output > max) {
                                    max = output;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Part 1 Answer: " + max);
        max = 0;
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                for (int k = 5; k < 10; k++) {
                    for (int l = 5; l < 10; l++) {
                        for (int m = 5; m < 10; m++) {
                            if (j != i && k != i && k != j && l != i && l != j && l != k && m != i && m != j && m != k
                                && m != l) {
                                boolean halt = false;
                                int output = 0;
                                int[] startIndex = new int[5];
                                long[] relativeBase = new long[5];
                                boolean[] phaseSettingNotNeeded = new boolean[5];
                                List<Long>[] listCopy = new ArrayList[5];
                                for (int n = 0; n < 5; n++) {
                                    listCopy[n] = new ArrayList<>(list);
                                }
                                while(!halt) {
                                    Result result = IntcodeComputer.computer(listCopy[0], startIndex[0], relativeBase[0],
                                        true, phaseSettingNotNeeded[0] ? output : i);
                                    halt = result.halt;
                                    startIndex[0] = result.newStartIndex;
                                    relativeBase[0] = result.newRelativeBase;
                                    if (!halt) {
                                        output = (int) result.output;
                                    }
                                    phaseSettingNotNeeded[0] = true;
                                    if (halt) {
                                        break;
                                    }
                                    result = IntcodeComputer.computer(listCopy[1], startIndex[1], relativeBase[1],
                                        true, phaseSettingNotNeeded[1] ? output : j);
                                    halt = result.halt;
                                    startIndex[1] = result.newStartIndex;
                                    relativeBase[1] = result.newRelativeBase;
                                    if (!halt) {
                                        output = (int) result.output;
                                    }
                                    phaseSettingNotNeeded[1] = true;
                                    if (halt) {
                                        break;
                                    }
                                    result = IntcodeComputer.computer(listCopy[2], startIndex[2], relativeBase[2],
                                        true, phaseSettingNotNeeded[2] ? output : k);
                                    halt = result.halt;
                                    startIndex[2] = result.newStartIndex;
                                    relativeBase[2] = result.newRelativeBase;
                                    if (!halt) {
                                        output = (int) result.output;
                                    }
                                    phaseSettingNotNeeded[2] = true;
                                    if (halt) {
                                        break;
                                    }
                                    result = IntcodeComputer.computer(listCopy[3], startIndex[3], relativeBase[3],
                                        true, phaseSettingNotNeeded[3] ? output : l);
                                    halt = result.halt;
                                    startIndex[3] = result.newStartIndex;
                                    relativeBase[3] = result.newRelativeBase;
                                    if (!halt) {
                                        output = (int) result.output;
                                    }
                                    phaseSettingNotNeeded[3] = true;
                                    if (halt) {
                                        break;
                                    }
                                    result = IntcodeComputer.computer(listCopy[4], startIndex[4], relativeBase[4],
                                        true, phaseSettingNotNeeded[4] ? output : m);
                                    halt = result.halt;
                                    startIndex[4] = result.newStartIndex;
                                    relativeBase[4] = result.newRelativeBase;
                                    if (!halt) {
                                        output = (int) result.output;
                                    }
                                    phaseSettingNotNeeded[4] = true;
                                }
                                if (output > max) {
                                    max = output;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Part 2 Answer: " + max);
    }
}
