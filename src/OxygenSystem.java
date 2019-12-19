import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

class Position {
    int x;
    int y;
    int object;
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    Position prev;
    Position(int x, int y, int object) {
        this.x = x;
        this.y = y;
        this.object = object;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position otherPosition = (Position) other;
        return x == otherPosition.x && y == otherPosition.y;
    }
    @Override
    public int hashCode() {
        return 0;
    }
}
class XYIndex {
    int x;
    int y;
    XYIndex(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof XYIndex)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        XYIndex otherIndices = (XYIndex) other;
        return x == otherIndices.x && y == otherIndices.y;
    }
    @Override
    public int hashCode() {
        return 0;
    }
}

/**
 * Day 15
 * @author Rishabh Ranjan
 */
public class OxygenSystem {
    public static void upIteration(Position currPosition, List<Position> visited,
                                   HashMap<XYIndex, Position> positionsMap, Queue<Position> queue) {
        if (currPosition.up && !visited.contains(positionsMap.get(new XYIndex(currPosition.x,
            currPosition.y - 1)))) {
            Position position = positionsMap.get(new XYIndex(currPosition.x, currPosition.y - 1));
            position.prev = currPosition;
            visited.add(position);
            queue.add(position);
        }
    }
    public static void downIteration(Position currPosition, List<Position> visited,
                                     HashMap<XYIndex, Position> positionsMap, Queue<Position> queue) {
        if (currPosition.down && !visited.contains(positionsMap.get(new XYIndex(currPosition.x,
            currPosition.y + 1)))) {
            Position position = positionsMap.get(new XYIndex(currPosition.x, currPosition.y + 1));
            position.prev = currPosition;
            visited.add(position);
            queue.add(position);
        }
    }
    public static void leftIteration(Position currPosition, List<Position> visited,
                                     HashMap<XYIndex, Position> positionsMap, Queue<Position> queue) {
        if (currPosition.left && !visited.contains(positionsMap.get(new XYIndex(currPosition.x - 1,
            currPosition.y)))) {
            Position position = positionsMap.get(new XYIndex(currPosition.x - 1, currPosition.y));
            position.prev = currPosition;
            visited.add(position);
            queue.add(position);
        }
    }
    public static void rightIteration(Position currPosition, List<Position> visited,
                                      HashMap<XYIndex, Position> positionsMap, Queue<Position> queue) {
        if (currPosition.right && !visited.contains(positionsMap.get(new XYIndex(currPosition.x + 1,
            currPosition.y)))) {
            Position position = positionsMap.get(new XYIndex(currPosition.x + 1, currPosition.y));
            position.prev = currPosition;
            visited.add(position);
            queue.add(position);
        }
    }
    public static void decideIteration(int number, Position currPosition, List<Position> visited, HashMap<XYIndex,
        Position> positionsMap, Queue<Position> queue) {
        if (number == 1) {
            upIteration(currPosition, visited, positionsMap, queue);
        } else if (number == 2) {
            downIteration(currPosition, visited, positionsMap, queue);
        } else if (number == 3) {
            leftIteration(currPosition, visited, positionsMap, queue);
        } else if (number == 4) {
            rightIteration(currPosition, visited, positionsMap, queue);
        } else {
            System.out.println("Error");
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay15.txt"));
        String listString = sc.nextLine();
        for (int i = 0; i < listString.length(); i++) {
            if (listString.charAt(i) == ',') {
                listString = listString.substring(0, i) + " " + listString.substring(i + 1);
            }
        }
        Scanner sc2 = new Scanner(listString);
        ArrayList<Long> list = new ArrayList<Long>();
        while (sc2.hasNextLong()) {
            list.add(sc2.nextLong());
        }
        int startIndex = 0;
        long relativeBase = 0;
        boolean inputSent = false;
        int input = 0;
        boolean halt = false;
        HashSet<Position> positions = new HashSet<>();
        positions.add(new Position(0, 0, 3));
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            List<Long> listCopy = new ArrayList<>(list);
            HashSet<Integer> bannedMovements = new HashSet<>();
            Position currPosition = new Position(0, 0, 3);
            positions.add(new Position(0, 0, 3));
            while (!halt) {
                Result result = IntcodeComputer.computer(listCopy, startIndex, relativeBase, inputSent, input);
                startIndex = result.newStartIndex;
                relativeBase = result.newRelativeBase;
                halt = result.halt;
                if (result.inputExpected) {
                    boolean inputFound = false;
                    while (!inputFound) {
                        input = rand.nextInt(4) + 1;
                        if (!bannedMovements.contains(input)) {
                            inputFound = true;
                        }
                    }
                    inputSent = true;
                } else if (!halt) {
                    long output = result.output;
                    if (output == 2) {
                        if (input == 1 || input == 2) {
                            currPosition.y = input == 1 ? currPosition.y + 1 : currPosition.y - 1;
                        } else if (input == 3 || input == 4) {
                            currPosition.x = input == 4 ? currPosition.x + 1 : currPosition.x - 1;
                        }
                        positions.add(new Position(currPosition.x, currPosition.y, 2));
//                        System.out.println("2 found");
                        break;
                    } else if (output == 0) {
                        if (input == 1 || input == 2) {
                            positions.add(new Position(currPosition.x, input == 1 ? currPosition.y + 1
                                : currPosition.y - 1,1));
                        } else if (input == 3 || input == 4) {
                            positions.add(new Position(input == 4 ? currPosition.x + 1 : currPosition.x - 1,
                                currPosition.y, 1));
                        }
                        bannedMovements.add(input);
                    } else if (output == 1) {
                        if (input == 1 || input == 2) {
                            currPosition.y = input == 1 ? currPosition.y + 1 : currPosition.y - 1;
                        } else if (input == 3 || input == 4) {
                            currPosition.x = input == 4 ? currPosition.x + 1 : currPosition.x - 1;
                        }
                        positions.add(new Position(currPosition.x, currPosition.y, 4));
                        bannedMovements.clear();
                    } else {
                        System.out.println("Output Error");
                        break;
                    }
                    inputSent = false;
                }
            }
        }
        positions.remove(new Position(0, 0, 4));
        positions.add(new Position(0, 0, 3));
        int maxX = -Integer.MAX_VALUE;
        int maxY = -Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for (Position position : positions) {
            if (position.x > maxX) {
                maxX = position.x;
            }
            if (position.x < minX) {
                minX = position.x;
            }
            if (position.y > maxY) {
                maxY = position.y;
            }
            if (position.y < minY){
                minY = position.y;
            }
        }
        int[][] map = new int[maxY - minY + 1][maxX - minX + 1];
        for (Position position : positions) {
            map[-position.y + maxY][position.x - minX] = position.object;
        }
        // If the map has any clusters of zeroes, the answers may be incorrect. If this is the case, rerun the program.
        System.out.println("Map:");
        for (int[] line : map) {
            for (int object : line) {
                if (object == 4) {
                    System.out.print(" ");
                } else if (object == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(object);
                }
            }
            System.out.println();
        }
        HashMap<XYIndex, Position> positionsMap = new HashMap<>();
        Position currPosition = new Position(0, 0, 0);
        int targetX = 0;
        int targetY = 0;
        Position initialPosition = new Position(0, 0, 0);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 4 || map[i][j] == 3 || map[i][j] == 2) {
                    Position position = new Position(j, i, map[i][j]);
                    position.up = map[i - 1][j] == 4 || map[i - 1][j] == 2 || map[i - 1][j] == 3;
                    position.down = map[i + 1][j] == 4 || map[i + 1][j] == 2 || map[i + 1][j] == 3;
                    position.left = map[i][j - 1] == 4 || map[i][j - 1] == 2 || map[i][j - 1] == 3;
                    position.right = map[i][j + 1] == 4 || map[i][j + 1] == 2 || map[i][j + 1] == 3;
                    if (map[i][j] == 3) {
                        initialPosition = position;
                    }
                    if (map[i][j] == 2) {
                        targetX = j;
                        targetY = i;
                    }
                    positionsMap.put(new XYIndex(j, i), position);
                }
            }
        }
//        System.out.println(targetX + " " + targetY);
        List<Position> visited = new ArrayList<>();
        visited.add(initialPosition);
        Queue<Position> queue = new ConcurrentLinkedQueue<>();
        queue.add(initialPosition);
//        System.out.println(initialPosition.x + " " + initialPosition.y);
        HashSet<Integer> usedRands = new HashSet<>();
        int random = 0;
        while (true) {
            if (queue.isEmpty()) {
                System.out.println(currPosition.x + " " + currPosition.y);
                currPosition.prev.up = false;
                currPosition.prev.down = false;
                currPosition.prev.left = false;
                currPosition.prev.right = false;
                visited.clear();
                visited.add(initialPosition);
                queue.add(initialPosition);
            }
            currPosition = queue.remove();
            if (currPosition.x == targetX && currPosition.y == targetY) {
//                System.out.println("2 found");
                break;
            }
            usedRands.clear();
            while (true) {
                random = rand.nextInt(4) + 1;
                if (!usedRands.contains(random)) {
                    usedRands.add(random);
                    break;
                }
            }
            decideIteration(random, currPosition, visited, positionsMap, queue);
            while (true) {
                random = rand.nextInt(4) + 1;
                if (!usedRands.contains(random)) {
                    usedRands.add(random);
                    break;
                }
            }
            decideIteration(random, currPosition, visited, positionsMap, queue);
            while (true) {
                random = rand.nextInt(4) + 1;
                if (!usedRands.contains(random)) {
                    usedRands.add(random);
                    break;
                }
            }
            decideIteration(random, currPosition, visited, positionsMap, queue);
            while (true) {
                random = rand.nextInt(4) + 1;
                if (!usedRands.contains(random)) {
                    usedRands.add(random);
                    break;
                }
            }
            decideIteration(random, currPosition, visited, positionsMap, queue);
        }
//        System.out.println(currPosition.object);
        int count = 0;
        for (Position ptr = currPosition; ptr != null; ptr = ptr.prev) {
            count++;
        }
//        System.out.println(currPosition.x + " " + currPosition.y);
        System.out.println("Part 1 Answer: " + (count - 1));
        positionsMap.clear();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 4 || map[i][j] == 3 || map[i][j] == 2) {
                    Position position = new Position(j, i, map[i][j]);
                    position.up = map[i - 1][j] == 4 || map[i - 1][j] == 2 || map[i - 1][j] == 3;
                    position.down = map[i + 1][j] == 4 || map[i + 1][j] == 2 || map[i + 1][j] == 3;
                    position.left = map[i][j - 1] == 4 || map[i][j - 1] == 2 || map[i][j - 1] == 3;
                    position.right = map[i][j + 1] == 4 || map[i][j + 1] == 2 || map[i][j + 1] == 3;
                    positionsMap.put(new XYIndex(j, i), position);
                }
            }
        }
        int i = 0;
        HashMap<XYIndex, Position> oxygenatedMap = new HashMap<>();
        Position source = positionsMap.get(new XYIndex(targetX, targetY));
        oxygenatedMap.put(new XYIndex(source.x, source.y), source);
        while (oxygenatedMap.size() != positionsMap.size()) {
            i++;
            HashMap<XYIndex, Position> oxygenatedMapCopy = (HashMap<XYIndex, Position>) oxygenatedMap.clone();
            for (Map.Entry<XYIndex, Position> xyIndexPositionEntry : oxygenatedMapCopy.entrySet()) {
                Position position = (Position) ((Map.Entry) xyIndexPositionEntry).getValue();
                if (position.up) {
                    oxygenatedMap.putIfAbsent(new XYIndex(position.x, position.y - 1),
                        positionsMap.get(new XYIndex(position.x, position.y - 1)));
                }
                if (position.down) {
                    oxygenatedMap.putIfAbsent(new XYIndex(position.x, position.y + 1),
                        positionsMap.get(new XYIndex(position.x, position.y + 1)));
                }
                if (position.left) {
                    oxygenatedMap.putIfAbsent(new XYIndex(position.x - 1, position.y),
                        positionsMap.get(new XYIndex(position.x - 1, position.y)));
                }
                if (position.right) {
                    oxygenatedMap.putIfAbsent(new XYIndex(position.x + 1, position.y),
                        positionsMap.get(new XYIndex(position.x + 1, position.y)));
                }
            }
        }
        System.out.println("Part 2 Answer: " + i);
    }
}