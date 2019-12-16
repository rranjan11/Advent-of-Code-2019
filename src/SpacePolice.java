import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Robot {
    int currX;
    int currY;
    int angle;
    Robot(int currX, int currY, int angle) {
        this.currX = currX;
        this.currY = currY;
        this.angle = angle;
    }
    void rotateAndMove(long direction) {
        angle += direction == 0 ? 90 : -90;
        angle = angle%360;
        while (angle < 0) {
            angle += 360;
            angle = angle%360;
        }
        if (angle == 0) {
            currX += 1;
        } else if (angle == 90) {
            currY += 1;
        } else if (angle == 180) {
            currX -= 1;
        } else if (angle == 270) {
            currY -= 1;
        } else {
            System.out.println("Angle Error");
        }
    }
}
class Panel {
    int x;
    int y;
    long color;
    Panel(int x, int y, long color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Panel)) {
            return false;
        }
        Panel otherPanel = (Panel) other;
        return x == otherPanel.x && y == otherPanel.y;
    }
}

/**
 * Day 11
 * @author Rishabh Ranjan
 */
public class SpacePolice {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay11.txt"));
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
        ArrayList<Panel> painted = new ArrayList<>();
        Robot robot = new Robot(0, 0, 90);
        int startIndex = 0;
        long relativeBase = 0;
        List<Long> listCopy = new ArrayList<>(list);
        while (true) {
            int color = 0;
            Panel currPanel = painted.contains(new Panel(robot.currX, robot.currY, color)) ?
                painted.get(painted.indexOf(new Panel(robot.currX, robot.currY, color)))
                : new Panel(robot.currX, robot.currY, color);
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, (int) currPanel.color);
            boolean halt = result.halt;
            if (halt) {
                break;
            }
            startIndex = result.newStartIndex;
            currPanel.color = result.output;
            if (!painted.contains(currPanel)) {
                painted.add(currPanel);
            }
            relativeBase = result.newRelativeBase;
            result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, false, (int) currPanel.color);
            halt = result.halt;
            if (halt) {
                break;
            }
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            robot.rotateAndMove(result.output);
        }
        System.out.println("Part 1 Answer: " + painted.size());
        painted = new ArrayList<>();
        robot = new Robot(0, 0, 90);
        startIndex = 0;
        relativeBase = 0;
        listCopy = new ArrayList<>(list);
        while (true) {
            int color = painted.isEmpty() ? 1 : 0;
            Panel currPanel = painted.contains(new Panel(robot.currX, robot.currY, color)) ?
                painted.get(painted.indexOf(new Panel(robot.currX, robot.currY, color)))
                : new Panel(robot.currX, robot.currY, color);
            Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, true, (int) currPanel.color);
            boolean halt = result.halt;
            if (halt) {
                break;
            }
            startIndex = result.newStartIndex;
            currPanel.color = result.output;
            if (!painted.contains(currPanel)) {
                painted.add(currPanel);
            }
            relativeBase = result.newRelativeBase;
            result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, false, (int) currPanel.color);
            halt = result.halt;
            if (halt) {
                break;
            }
            startIndex = result.newStartIndex;
            relativeBase = result.newRelativeBase;
            robot.rotateAndMove(result.output);
        }
        int maxX = -Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = -Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for (Panel panel : painted) {
            if (panel.x > maxX) {
                maxX = panel.x;
            }
            if (panel.x < minX) {
                minX = panel.x;
            }
            if (panel.y > maxY) {
                maxY = panel.y;
            }
            if (panel.y < minY) {
                minY = panel.y;
            }
        }
//        System.out.println(maxX + " " + minX + " " + maxY + " " + minY);
        int[][] arr = new int[maxY - minY + 1][maxX - minX + 1];
        for (Panel panel : painted) {
            if (panel.color == 1) {
                arr[-panel.y + maxY][panel.x - minX] = 1;
            }
        }
        System.out.println("Part 2 Answer:");
        for (int[] line : arr) {
            for (int panel : line) {
                System.out.print(panel == 1 ? "#" : " ");
            }
            System.out.println();
        }
    }
}
