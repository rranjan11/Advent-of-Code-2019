import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 21
 * @author Rishabh Ranjan
 */
public class SpringdroidAdventure {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay21.txt"));
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
        boolean inputSent = false;
        int startIndex = 0;
        long relativeBase = 0;
        int input = 0;
        long output = 0;
        String inputString = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nWALK\n";
        List<Long> listCopy = new ArrayList<>(list);
        while (true) {
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
            if(result.halt) {
                break;
            }
            relativeBase = result.newRelativeBase;
            startIndex = result.newStartIndex;
            if (result.inputExpected) {
                input = inputString.charAt(0);
                inputString = inputString.substring(1);
                inputSent = true;
            } else {
                output = result.output;
                inputSent = false;
            }
        }
        System.out.println("Part 1 Answer: " + output);
        inputSent = false;
        startIndex = 0;
        relativeBase = 0;
        input = 0;
        output = 0;
        inputString = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nNOT E T\nNOT T T\nOR H T\nAND T J\nRUN\n";
        listCopy = new ArrayList<>(list);
        while (true) {
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
            if(result.halt) {
                break;
            }
            relativeBase = result.newRelativeBase;
            startIndex = result.newStartIndex;
            if (result.inputExpected) {
                input = inputString.charAt(0);
                inputString = inputString.substring(1);
                inputSent = true;
            } else {
                output = result.output;
                inputSent = false;
            }
        }
        System.out.println("Part 2 Answer: " + output);
    }
}