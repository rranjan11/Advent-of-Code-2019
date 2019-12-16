import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 2
 * @author Rishabh Ranjan
 */
public class ProgramAlarm {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay2.txt"));
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
        listCopy.set(1, 12L);
        listCopy.set(2, 2L);
        IntcodeComputer.computer(listCopy, 0, 0, false, 0);
        System.out.println("Part 1 Answer: " + listCopy.get(0));
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                listCopy = new ArrayList<>(list);
                listCopy.set(1, (long) i);
                listCopy.set(2, (long) j);
                IntcodeComputer.computer(listCopy, 0, 0, false, 0);
                if (listCopy.get(0) == 19690720) {
                    System.out.println("Part 2 Answer: " + (i*100 + j));
                    break;
                }
            }
        }
    }
}
