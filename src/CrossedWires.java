import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Indices {
    int i;
    int j;
    Indices(int i, int j) {
        this.i = i;
        this.j = j;
    }
    public boolean equals(Object other) {
        if (!(other instanceof Indices)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Indices otherIndices = (Indices) other;
        return i == otherIndices.i && j == otherIndices.j;
    }
}

/**
 * Day 3
 * @author Rishabh Ranjan
 */
public class CrossedWires {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay3.txt"));
        String wire1 = sc.nextLine();
        String wire2 = sc.nextLine();
        for (int i = 0; i < wire1.length(); i++) {
            if (wire1.charAt(i) == ',') {
                wire1 = wire1.substring(0, i) + " " + wire1.substring(i + 1);
            }
        }
        for (int i = 0; i < wire2.length(); i++) {
            if (wire2.charAt(i) == ',') {
                wire2 = wire2.substring(0, i) + " " + wire2.substring(i + 1);
            }
        }
        int maxLeft = 0;
        int maxRight = 0;
        int maxUp = 0;
        int maxDown = 0;
        int xPos = 0;
        int yPos = 0;
        String currStr;
        Scanner wire1sc = new Scanner(wire1);
        Scanner wire2sc = new Scanner(wire2);
        while(wire1sc.hasNext()) {
            currStr = wire1sc.next();
            if (currStr.charAt(0) == 'L') {
                xPos -= Integer.parseInt(currStr.substring(1));
                if (xPos < 0 && Math.abs(xPos) > maxLeft) {
                    maxLeft = Math.abs(xPos);
                }
            } else if (currStr.charAt(0) == 'R') {
                xPos += Integer.parseInt(currStr.substring(1));
                if (xPos > 0 && xPos > maxRight) {
                    maxRight = xPos;
                }
            } else if (currStr.charAt(0) == 'U') {
                yPos += Integer.parseInt(currStr.substring(1));
                if (yPos > 0 && yPos > maxUp) {
                    maxUp = yPos;
                }
            } else if (currStr.charAt(0) == 'D') {
                yPos -= Integer.parseInt(currStr.substring(1));
                if (yPos < 0 && Math.abs(yPos) > maxDown) {
                    maxDown = Math.abs(yPos);
                }
            } else {
                System.out.println("Error");
                return;
            }
        }
        xPos = 0;
        yPos = 0;
        while(wire2sc.hasNext()) {
            currStr = wire2sc.next();
            if (currStr.charAt(0) == 'L') {
                xPos -= Integer.parseInt(currStr.substring(1));
                if (xPos < 0 && Math.abs(xPos) > maxLeft) {
                    maxLeft = Math.abs(xPos);
                }
            } else if (currStr.charAt(0) == 'R') {
                xPos += Integer.parseInt(currStr.substring(1));
                if (xPos > 0 && xPos > maxRight) {
                    maxRight = xPos;
                }
            } else if (currStr.charAt(0) == 'U') {
                yPos += Integer.parseInt(currStr.substring(1));
                if (yPos > 0 && yPos > maxUp) {
                    maxUp = yPos;
                }
            } else if (currStr.charAt(0) == 'D') {
                yPos -= Integer.parseInt(currStr.substring(1));
                if (yPos < 0 && Math.abs(yPos) > maxDown) {
                    maxDown = Math.abs(yPos);
                }
            } else {
                System.out.println("Error");
                return;
            }
        }
//        System.out.println(maxUp);
//        System.out.println(maxDown);
//        System.out.println(maxLeft);
//        System.out.println(maxRight);
//        System.out.println(maxUp + maxDown + 1);
//        System.out.println(maxLeft + maxRight + 1);
        int[][] arr = new int[maxUp + maxDown + 1][maxLeft + maxRight + 1];
        int[][] arrDist = new int[maxUp + maxDown + 1][maxLeft + maxRight + 1];
        wire1sc = new Scanner(wire1);
        wire2sc = new Scanner(wire2);
        int i = maxUp;
        int j = maxLeft;
        int count = 0;
        while (wire1sc.hasNext()) {
            currStr = wire1sc.next();
//            System.out.println(currStr);
            if (currStr.charAt(0) == 'L') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    j--;
                    arr[i][j] = 1;
                    count++;
                    if (arrDist[i][j] == 0) {
                        arrDist[i][j] = count;
                    }
                }
            } else if (currStr.charAt(0) == 'R') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    j++;
                    arr[i][j] = 1;
                    count++;
                    if (arrDist[i][j] == 0) {
                        arrDist[i][j] = count;
                    }
                }
            } else if (currStr.charAt(0) == 'U') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    i--;
                    arr[i][j] = 1;
                    count++;
                    if (arrDist[i][j] == 0) {
                        arrDist[i][j] = count;
                    }
                }
            } else if (currStr.charAt(0) == 'D') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    i++;
                    arr[i][j] = 1;
                    count++;
                    if (arrDist[i][j] == 0) {
                        arrDist[i][j] = count;
                    }
                }
            } else {
                System.out.println("Error");
                return;
            }
        }
        i = maxUp;
        j = maxLeft;
        count = 0;
        ArrayList<Indices> visited = new ArrayList<Indices>();
        while (wire2sc.hasNext()) {
            currStr = wire2sc.next();
//            System.out.println(currStr);
            if (currStr.charAt(0) == 'L') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    j--;
                    count++;
                    if (!visited.contains(new Indices(i, j))) {
                        arr[i][j] += 1;
                        arrDist[i][j] += count;
                        visited.add(new Indices(i, j));
                    }
                }
            } else if (currStr.charAt(0) == 'R') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    j++;
                    count++;
                    if (!visited.contains(new Indices(i, j))) {
                        arr[i][j] += 1;
                        arrDist[i][j] += count;
                        visited.add(new Indices(i, j));
                    }
                }
            } else if (currStr.charAt(0) == 'U') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    i--;
                    count++;
                    if (!visited.contains(new Indices(i, j))) {
                        arr[i][j] += 1;
                        arrDist[i][j] += count;
                        visited.add(new Indices(i, j));
                    }
                }
            } else if (currStr.charAt(0) == 'D') {
                for (int k = 0; k < Integer.parseInt(currStr.substring(1)); k++) {
                    i++;
                    count++;
                    if (!visited.contains(new Indices(i, j))) {
                        arr[i][j] += 1;
                        arrDist[i][j] += count;
                        visited.add(new Indices(i, j));
                    }
                }
            } else {
                System.out.println("Error");
                return;
            }
        }
        int minDistance = Integer.MAX_VALUE;
        int minManhattanDist = Integer.MAX_VALUE;
        for (int k = 0; k < arr.length; k++) {
            for (int l = 0; l < arr[0].length; l++) {
                if (arr[k][l] == 2) {
                    int manhattanDist = Math.abs(k - maxUp) + Math.abs(l - maxLeft);
                    if(manhattanDist < minManhattanDist) {
                        minManhattanDist = manhattanDist;
                    }
                    if (arrDist[k][l] < minDistance) {
                        minDistance = arrDist[k][l];
                    }
                }
            }
        }
        System.out.println("Part 1 Answer: " + minManhattanDist);
        System.out.println("Part 2 Answer: " + minDistance);
    }
}