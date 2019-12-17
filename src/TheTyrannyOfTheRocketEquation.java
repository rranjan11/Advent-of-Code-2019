import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Day 1
 * @author Rishabh Ranjan
 */
public class TheTyrannyOfTheRocketEquation {
    public static void part1(Scanner sc) {
        int fuelTotal = 0;
        while(sc.hasNextInt()) {
            fuelTotal += sc.nextInt() / 3 - 2;
        }
        System.out.println("Part 1 Answer: " + fuelTotal);
    }
    public static void part2(Scanner sc) {
        int mass;
        int fuelTotal = 0;
        while (sc.hasNextInt()) {
            mass = sc.nextInt();
            while (mass /3 - 2 >= 0) {
                mass = mass / 3 - 2;
                fuelTotal += mass;
            }
        }
        System.out.println("Part 2 Answer: " + fuelTotal);
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/inputDay1.txt");
        Scanner sc = new Scanner(file);
        part1(sc);
        sc = new Scanner(file);
        part2(sc);
    }
}
