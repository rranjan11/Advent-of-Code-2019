import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Day 16
 * @author Rishabh Ranjan
 */
public class FlawedFrequencyTransmission {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay16.txt"));
//        Scanner sc = new Scanner("03036732577212944063491565474664");
        String listString = sc.nextLine();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < listString.length(); i++) {
            list.add(Character.getNumericValue(listString.charAt(i)));
        }
        ArrayList<Integer> basePattern = new ArrayList<>();
        basePattern.add(0);
        basePattern.add(1);
        basePattern.add(0);
        basePattern.add(-1);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < list.size(); j++) {
                ArrayList<Integer> pattern = new ArrayList<>();
                for (int k : basePattern) {
                    for (int l = 0; l < j + 1; l++) {
                        pattern.add(k);
                    }
                }
//                System.out.println(pattern);
                int total = 0;
                for (int k = 0; k < list.size(); k++) {
                    total += list.get(k)*pattern.get((k + 1)%pattern.size());
                }
                list.set(j, Math.abs(total)%10);
            }
        }
        System.out.print("Part 1 Answer: ");
        for (int i = 0; i < 8; i++) {
            System.out.print(list.get(i));
        }
        System.out.println();
        list.clear();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < listString.length(); j++) {
                list.add(Character.getNumericValue(listString.charAt(j)));
            }
        }
        String messageOffset = "";
        for (int i = 0; i < 7; i++) {
            messageOffset += list.get(i);
        }
        int offset = Integer.parseInt(messageOffset);
//        System.out.println(offset);
//        System.out.println(list.size());
        int[] arr = list.stream().mapToInt(i->i).toArray();
        for (int i = 0; i < 100; i++) {
            for (int j = arr.length - 1; j > offset - 1; j--) {
                arr[j - 1] += arr[j];
                arr[j - 1] = arr[j - 1]%10;
            }
        }
        System.out.print("Part 2 Answer: ");
        for (int i = 0; i < 8; i++) {
            System.out.print(arr[offset + i]);
        }
        System.out.println();
    }
}