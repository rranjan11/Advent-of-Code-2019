import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Day 8
 * @author Rishabh Ranjan
 */
public class SpaceImageFormat {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay8.txt"));
        String input = sc.next();
        int[] arr = new int[input.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(input.substring(i, i + 1));
        }
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < arr.length; i += 150) {
            int count = 0;
            for (int j = i; j < i + 150; j++) {
                if (arr[j] == 0) {
                    count++;
                }
            }
            if (count < min) {
                minIndex = i;
                min = count;
            }
        }
        int countOnes = 0;
        int countTwos = 0;
        for (int i = minIndex; i < minIndex + 150; i++) {
            if (arr[i] == 1) {
                countOnes++;
            }
            if (arr[i] == 2) {
                countTwos++;
            }
        }
        System.out.println("Part 1 Answer: " + countOnes*countTwos);
        int[] image = new int[150];
        for (int i = 0; i < 150; i++) {
            for (int j = i; j < arr.length; j += 150) {
                if (arr[j] != 2) {
                    image[i] = arr[j];
                    break;
                }
            }
        }
        System.out.println("Part 2 Answer:");
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 25; j++) {
                System.out.print(image[count] == 1 ? '#' : " ");
                count++;
            }
            System.out.println();
        }
    }
}