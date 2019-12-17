import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 17
 * @author Rishabh Ranjan
 */
public class SetAndForget {
    public static boolean pathForward(ArrayList<String> view, int x, int y, char direction) {
        if (direction == '^' && y != 0 && view.get(y - 1).charAt(x) == '#') {
            return true;
        }
        if (direction == '<' && x != 0 && view.get(y).charAt(x - 1) == '#') {
            return true;
        }
        if (direction == 'v' && y != view.size() - 1 && view.get(y + 1).charAt(x) == '#') {
            return true;
        }
        return direction == '>' && x != view.get(0).length() - 1 && view.get(y).charAt(x + 1) == '#';
    }
    public static boolean pathToRight(ArrayList<String> view, int x, int y, char direction) {
        char newDirection;
        if (direction == '^') {
            newDirection = '>';
        } else if (direction == '>') {
            newDirection = 'v';
        } else if (direction == 'v') {
            newDirection = '<';
        } else {
            newDirection = '^';
        }
        return pathForward(view, x, y, newDirection);
    }
    public static boolean pathToLeft(ArrayList<String> view, int x, int y, char direction) {
        char newDirection;
        if (direction == '^') {
            newDirection = '<';
        } else if (direction == '>') {
            newDirection = '^';
        } else if (direction == 'v') {
            newDirection = '>';
        } else {
            newDirection = 'v';
        }
        return pathForward(view, x, y, newDirection);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay17.txt"));
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
        List<Long> listCopy = new ArrayList<>(list);
        boolean halt = false;
        int startIndex = 0;
        long relativeBase = 0;
        String output = "";
        while(true) {
            Result result = IntcodeComputer.computer(listCopy,startIndex, relativeBase, false, 0);
            halt = result.halt;
            if (halt) {
                break;
            }
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            output += (char) result.output;
        }
//        System.out.println(output);
        ArrayList<String> view = new ArrayList<>();
        Scanner sc3 = new Scanner(output);
        while (sc3.hasNextLine()) {
            view.add(sc3.nextLine());
        }
        view.remove(view.size() - 1);
        int total = 0;
        for (int i = 1; i < view.size() - 1; i++) {
            for (int j = 1; j < view.get(0).length() - 1; j++) {
                if (view.get(i).charAt(j) == '#' && view.get(i - 1).charAt(j) == '#' && view.get(i + 1).charAt(j) == '#'
                && view.get(i).charAt(j - 1) == '#' && view.get(i).charAt(j + 1) == '#') {
                    total += i*j;
                }
            }
        }
        int x = 0;
        int y = 0;
        char direction = '^';
        for (int i = 0; i < view.size(); i++) {
            for (int j = 0; j < view.get(0).length(); j++) {
                if (view.get(i).charAt(j) == '^' || view.get(i).charAt(j) == '<' || view.get(i).charAt(j) == '>'
                    || view.get(i).charAt(j) == 'v') {
                    x = j;
                    y = i;
                    direction = view.get(i).charAt(j);
                }
            }
        }
        System.out.println("Part 1 Answer: " + total);
        String path = "";
        int distance = 0;
        while (pathForward(view, x, y, direction) || pathToRight(view, x, y, direction)
            || pathToLeft(view, x, y, direction)) {
            if (pathForward(view, x, y, direction)) {
                distance += 1;
                if (direction == '^') {
                    y -= 1;
                } else if (direction == '>') {
                    x += 1;
                } else if (direction == 'v') {
                    y += 1;
                } else if (direction == '<') {
                    x -= 1;
                }
            } else if (pathToRight(view, x, y, direction)) {
                if (direction == '^') {
                    direction = '>';
                } else if (direction == '>') {
                    direction = 'v';
                } else if (direction == 'v') {
                    direction = '<';
                } else if (direction == '<') {
                    direction = '^';
                }
                if (distance != 0) {
                    if (!path.equals("")) {
                        path += ',';
                    }
                    path += distance;
                    distance = 0;
                }
                if (!path.equals("")) {
                    path += ',';
                }
                path += 'R';
            } else if (pathToLeft(view, x, y, direction)) {
                if (direction == '^') {
                    direction = '<';
                } else if (direction == '>') {
                    direction = '^';
                } else if (direction == 'v') {
                    direction = '>';
                } else if (direction == '<') {
                    direction = 'v';
                }
                if (distance != 0) {
                    if (!path.equals("")) {
                        path += ',';
                    }
                    path += distance;
                    distance = 0;
                }
                if (!path.equals("")) {
                    path += ',';
                }
                path += 'L';
//                System.out.println(direction);
            }
//            System.out.println(x + " " + y + " " + direction);
//            System.out.println(pathForward(view, x, y, direction) + " " + pathToRight(view, x, y, direction) + " "
//                + pathToLeft(view, x, y, direction));
        }
        if (distance != 0) {
            if (!path.equals("")) {
                path += ',';
            }
            path += distance;
        }
//        System.out.println(view.get(0).length());
//        System.out.println(x + " " + y + " " + direction);
        System.out.println("Path: " + path);
        String A = "R,6,L,8,R,8";
        String B = "R,4,R,6,R,6,R,4,R,4";
        String C = "L,8,R,6,L,10,L,10";
        String functionPath = "";
        while (!path.equals("")) {
            if (path.length() >= A.length() && path.substring(0, A.length()).equals(A)) {
                functionPath += "A,";
                path = path.substring(A.length());
            } else if (path.length() >= B.length() && path.substring(0, B.length()).equals(B)) {
                functionPath += "B,";
                path = path.substring(B.length());
            } else if (path.length() >= C.length() && path.substring(0, C.length()).equals(C)) {
                functionPath += "C,";
                path = path.substring(C.length());
            } else {
                System.out.println("Error");
                break;
            }
            if (!path.equals("") && path.charAt(0) == ',') {
                path = path.substring(1);
            }
        }
        functionPath = functionPath.substring(0, functionPath.length() - 1) + '\n';
        A += '\n';
        B += '\n';
        C = "L,8,R,6,L,5,5,L,5,5\n";
//        System.out.println(functionPath);
        halt = false;
        startIndex = 0;
        relativeBase = 0;
        listCopy = new ArrayList<>(list);
        listCopy.set(0, 2L);
        int input = 0;
        long dust = 0;
        boolean inputSent = false;
        boolean videoFeedAnswerSent = false;
        while (true) {
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
            halt = result.halt;
            if (halt) {
                break;
            }
            relativeBase = result.newRelativeBase;
            startIndex = result.newStartIndex;
            if (result.inputExpected) {
//                System.out.println(A);
                if (!functionPath.equals("")) {
                    input = functionPath.charAt(0);
                    functionPath = functionPath.substring(1);
//                    System.out.println("Sending " + input + " from path");
                } else if (!A.equals("")) {
//                    System.out.println(A);
                    input = A.charAt(0);
                    A = A.substring(1);
//                    System.out.println("Sending " + input + " from A");
                } else if (!B.equals("")) {
                    input = B.charAt(0);
                    B = B.substring(1);
//                    System.out.println("Sending " + input + " from B");
                } else if (!C.equals("")) {
//                    System.out.println(C);
                    input = C.charAt(0);
                    C = C.substring(1);
//                    System.out.println("Sending " + input + " from C");
                } else {
                    input = videoFeedAnswerSent ? '\n' : 'n';
                    videoFeedAnswerSent = true;
                }
                inputSent = true;
            } else {
                dust = result.output;
                inputSent = false;
//                System.out.println("No input sent");
//                System.out.println(dust);
            }
        }
        System.out.println("Part 2 Answer: " + dust);
    }
}