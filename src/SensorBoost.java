import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 9
 * @author Rishabh Ranjan
 */
public class SensorBoost {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay9.txt"));
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
        System.out.println("Part 1 Answer: " + IntcodeComputer.computer(new ArrayList<>(list), 0, 0,
            true, 1).output);
        System.out.println("Part 2 Answer: " + IntcodeComputer.computer(new ArrayList<>(list), 0, 0,
            true, 2).output);
    }
}
