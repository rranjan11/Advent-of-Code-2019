import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Day 5
 * @author Rishabh Ranjan
 */
public class SunnyWithAChanceOfAsteroids {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay5.txt"));
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
        List<Long> listCopy = new ArrayList<>(list);
        boolean halt = false;
        int startIndex = 0;
        long relativeBase = 0;
        boolean inputSent = false;
        int input = 0;
        while(!halt) {
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            halt = result.halt;
            if (result.inputExpected) {
                input = 1;
                inputSent = true;
            } else if (!halt) {
                System.out.println("Part 1 Output: " + result.output);
                inputSent = false;
            }
        }
        listCopy = new ArrayList<>(list);
        halt = false;
        startIndex = 0;
        relativeBase = 0;
        inputSent = false;
        input = 0;
        while(!halt) {
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            halt = result.halt;
            if (result.inputExpected) {
                input = 5;
                inputSent = true;
            } else if (!halt) {
                System.out.println("Part 2 Output: " + result.output);
                inputSent = false;
            }
        }
    }
}