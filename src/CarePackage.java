import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Day 13
 * @author Rishabh Ranjan
 */
public class CarePackage {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay13.txt"));
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
        for (int i = 0; i < 100000000; i++) {
            list.add(0L);
        }
        boolean halt = false;
        Result result;
        int maxX = 0;
        int maxY = 0;
        ArrayList<Long> outputs = new ArrayList<>();
        int startIndex = 0;
        long relativeBase = 0;
        while(true) {
            result = IntcodeComputer.computer(list, startIndex, relativeBase, true, 0);
            halt = result.halt;
            if (halt) {
                break;
            }
            outputs.add(result.output);
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
        }
        int count = 0;
        for (int i = 2; i < outputs.size(); i += 3) {
            if (outputs.get(i) == 2) {
                count++;
            }
        }
        System.out.println("Part 1 Answer: " + count);
        for (int i = 0; i < outputs.size(); i += 3) {
            if (outputs.get(i) > maxX) {
                maxX = (int) (long) outputs.get(i);
            }
        }
        for (int i = 1; i < outputs.size(); i += 3) {
            if (outputs.get(i) > maxY) {
                maxY = (int) (long) outputs.get(i);
            }
        }
        list.set(0, 2L);
        long[][] arr = new long[maxY + 1][maxX + 1];
//        System.out.println(arr.length + " " + arr[0].length);
        count = 0;
        startIndex = 0;
        relativeBase = 0;
        halt = false;
        long score = 0;
        int input = 0;
        long x = 0;
        long y = 0;
        boolean inputSent = false;
        while(!halt) {
            result = IntcodeComputer.computer(list, startIndex, relativeBase, inputSent, input);
            if (result.inputExpected) {
//                System.out.println("Sending input");
                int paddlePos = IntcodeComputer.linearSearch(arr[arr.length - 2], 3);
                int ballPos = 0;
                for (long[] longs : arr) {
                    ballPos = IntcodeComputer.linearSearch(longs, 4);
                    if (ballPos != -1) {
                        break;
                    }
                }
                input = 0;
                if (!(ballPos == paddlePos)) {
                    input = paddlePos < ballPos ? 1 : -1;
                }
                count = 0;
                inputSent = true;
            } else {
//                System.out.println("Received output");
                while(true) {
                    inputSent = false;
                    if (result.output == -1) {
//                        System.out.println("Received -1");
                        count = -1;
                        break;
                    }
                    if (count == -1) {
//                        System.out.println("Received -2");
                        count = -2;
                        break;
                    }
                    if (count == -2) {
//                        System.out.println("Received score");
                        score = result.output;
                        count = 0;
                        break;
                    }
                    if (count%3 == 0) {
                        x = result.output;
                    } else if (count%3 == 1) {
                        y = result.output;
                    } else {
                        arr[(int) y][(int) x] = result.output;
                    }
                    count++;
                    break;
                }
            }
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            halt = result.halt;
//            for (long[] longs : arr) {
//                for (int j = 0; j < arr[0].length; j++) {
//                    System.out.print(longs[j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
        }
        System.out.println("Part 2 Answer: " + score);
    }
}
