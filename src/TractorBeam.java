import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 19
 * @author Rishabh Ranjan
 */
public class TractorBeam {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay19.txt"));
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
        long output = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                int startIndex = 0;
                long relativeBase = 0;
                List<Long> listCopy = new ArrayList<>(list);
                Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, j);
                startIndex = result.newStartIndex;
                relativeBase = result.newRelativeBase;
                result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, i);
                output += result.output;
//                System.out.print(result.output == 1 ? '#' : '.');
            }
//            System.out.println("Iteration " + (i + 1) + ": " + output);
//            System.out.println();
        }
        System.out.println("Part 1 Answer: " + output);
        int rightmostBeam = 0;
        boolean started = false;
        for (int i = 50; i < 10000; i++) {
//            System.out.println("Iteration " + (i + 1));
            for (int j = rightmostBeam; j < 10000; j++) {
                int startIndex = 0;
                long relativeBase = 0;
                List<Long> listCopy = new ArrayList<>(list);
                Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, j);
                startIndex = result.newStartIndex;
                relativeBase = result.newRelativeBase;
                result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, i);
                if (result.output == 1) {
                    rightmostBeam = j;
                    started = true;
//                    System.out.println("In tractor beam");
                } else if (started) {
                    break;
                }
            }
            if (rightmostBeam - 99 >= 0) {
                int startIndex = 0;
                long relativeBase = 0;
                List<Long> listCopy = new ArrayList<>(list);
                Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true,
                    rightmostBeam - 99);
                startIndex = result.newStartIndex;
                relativeBase = result.newRelativeBase;
                result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, i);
                if (result.output == 1) {
                    startIndex = 0;
                    relativeBase = 0;
                    listCopy = new ArrayList<>(list);
                    result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true,
                        rightmostBeam - 99);
                    startIndex = result.newStartIndex;
                    relativeBase = result.newRelativeBase;
                    result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true,
                        i + 99);
                    if (result.output == 1) {
                        System.out.println("Part 2 Answer: " + ((rightmostBeam - 99)*10000 + i));
                        break;
                    }
                }
            }
        }
    }
}