import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

class Coordinates {
    int x;
    int y;
    int layer;
    Coordinates prev;
    Coordinates(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Coordinates)) {
            return false;
        }
        Coordinates otherCoordinates = (Coordinates) other;
        return x == otherCoordinates.x && y == otherCoordinates.y && layer == otherCoordinates.layer;
    }
    @Override
    public int hashCode() {
        return 0;
    }
}

/**
 * Day 20
 * @author Rishabh Ranjan
 */
public class DonutMaze {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay20.txt"));
//        Scanner sc = new Scanner(new File("test.txt"));
        ArrayList<String> map = new ArrayList<>();
        while (sc.hasNextLine()) {
            map.add(sc.nextLine());
        }
        String spaces = "";
        for (int i = 0; i < map.get(0).length() - map.get(map.size() - 1).length(); i++) {
            spaces += " ";
        }
        map.set(map.size() - 1, map.get(map.size() - 1) + spaces);
//        for (String str : map) {
//            System.out.println(str);
//        }
        HashMap<String, HashSet<Coordinates>> portals = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length(); j++) {
                if (map.get(i).charAt(j) == '.') {
                    if (Character.isAlphabetic(map.get(i - 1).charAt(j))) {
                        if (portals.containsKey("" + map.get(i - 1).charAt(j) + map.get(i - 2).charAt(j))) {
                            portals.get("" + map.get(i - 1).charAt(j) + map.get(i - 2).charAt(j)).add(
                                new Coordinates(j, i, 0));
                        } else if (portals.containsKey("" + map.get(i - 2).charAt(j) + map.get(i - 1).charAt(j))) {
                            portals.get("" + map.get(i - 2).charAt(j) + map.get(i - 1).charAt(j)).add(
                                new Coordinates(j, i, 0));
                        } else {
                            HashSet<Coordinates> coordinatesSet = new HashSet<>();
                            coordinatesSet.add(new Coordinates(j, i, 0));
                            portals.put("" + map.get(i - 1).charAt(j) + map.get(i - 2).charAt(j), coordinatesSet);
                        }
                    } else if (Character.isAlphabetic(map.get(i + 1).charAt(j))) {
                        if (portals.containsKey("" + map.get(i + 1).charAt(j) + map.get(i + 2).charAt(j))) {
                            portals.get("" + map.get(i + 1).charAt(j) + map.get(i + 2).charAt(j)).add(
                                new Coordinates(j, i, 0));
                        } else if (portals.containsKey("" + map.get(i + 2).charAt(j) + map.get(i + 1).charAt(j))) {
                            portals.get("" + map.get(i + 2).charAt(j) + map.get(i + 1).charAt(j)).add(
                                new Coordinates(j, i, 0));
                        } else {
                            HashSet<Coordinates> coordinatesSet = new HashSet<>();
                            coordinatesSet.add(new Coordinates(j, i, 0));
                            portals.put("" + map.get(i + 1).charAt(j) + map.get(i + 2).charAt(j), coordinatesSet);
                        }
                    } else if (Character.isAlphabetic(map.get(i).charAt(j - 1))) {
                        if (portals.containsKey("" + map.get(i).charAt(j - 1) + map.get(i).charAt(j - 2))) {
                            portals.get("" + map.get(i).charAt(j - 1) + map.get(i).charAt(j - 2)).add(
                                new Coordinates(j, i, 0));
                        } else if (portals.containsKey("" + map.get(i).charAt(j - 2) + map.get(i).charAt(j - 1))) {
                            portals.get("" + map.get(i).charAt(j - 2) + map.get(i).charAt(j - 1)).add(
                                new Coordinates(j, i, 0));
                        } else {
                            HashSet<Coordinates> coordinatesSet = new HashSet<>();
                            coordinatesSet.add(new Coordinates(j, i, 0));
                            portals.put("" + map.get(i).charAt(j - 1) + map.get(i).charAt(j - 2), coordinatesSet);
                        }
                    } else if (Character.isAlphabetic(map.get(i).charAt(j + 1))) {
                        if (portals.containsKey("" + map.get(i).charAt(j + 1) + map.get(i).charAt(j + 2))) {
                            portals.get("" + map.get(i).charAt(j + 1) + map.get(i).charAt(j + 2)).add(
                                new Coordinates(j, i, 0));
                        } else if (portals.containsKey("" + map.get(i).charAt(j + 2) + map.get(i).charAt(j + 1))) {
                            portals.get("" + map.get(i).charAt(j + 2) + map.get(i).charAt(j + 1)).add(
                                new Coordinates(j, i, 0));
                        } else {
                            HashSet<Coordinates> coordinatesSet = new HashSet<>();
                            coordinatesSet.add(new Coordinates(j, i, 0));
                            portals.put("" + map.get(i).charAt(j + 1) + map.get(i).charAt(j + 2), coordinatesSet);
                        }
                    }
                }
            }
        }
//        System.out.println(portals);
        Coordinates currPosition = new Coordinates(0, 0, 0);
        for (Coordinates coordinates : portals.get("AA")) {
            currPosition = coordinates;
        }
        Queue<Coordinates> queue = new ConcurrentLinkedQueue<>();
        HashSet<Coordinates> visited = new HashSet<>();
        queue.add(currPosition);
        visited.add(currPosition);
        boolean found = false;
        while (!queue.isEmpty()) {
            currPosition = queue.remove();
            for (Coordinates coordinates : portals.get("ZZ")) {
                if (currPosition.equals(coordinates)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y - 1, 0))
                && map.get(currPosition.y - 1).charAt(currPosition.x) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x, currPosition.y - 1, 0);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y - 1, 0))
                && Character.isAlphabetic(map.get(currPosition.y - 1).charAt(currPosition.x))) {
                visited.add(new Coordinates(currPosition.x, currPosition.y - 1, 0));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y - 1).charAt(
                    currPosition.x) + map.get(currPosition.y - 2).charAt(currPosition.x)) ? portals.get("" + map.get(
                        currPosition.y - 1).charAt(currPosition.x) + map.get(currPosition.y - 2).charAt(currPosition.x))
                    : portals.get("" + map.get(currPosition.y - 2).charAt(currPosition.x) + map.get(
                        currPosition.y - 1).charAt(currPosition.x));
                for (Coordinates coordinates : positions) {
                    if (!currPosition.equals(coordinates)) {
                        coordinates.prev = currPosition;
                        visited.add(coordinates);
                        queue.add(coordinates);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y + 1, 0))
                && map.get(currPosition.y + 1).charAt(currPosition.x) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x, currPosition.y + 1, 0);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y + 1, 0))
                && Character.isAlphabetic(map.get(currPosition.y + 1).charAt(currPosition.x))) {
                visited.add(new Coordinates(currPosition.x, currPosition.y + 1, 0));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y + 1).charAt(
                    currPosition.x) + map.get(currPosition.y + 2).charAt(currPosition.x)) ? portals.get("" + map.get(
                    currPosition.y + 1).charAt(currPosition.x) + map.get(currPosition.y + 2).charAt(currPosition.x))
                    : portals.get("" + map.get(currPosition.y + 2).charAt(currPosition.x) + map.get(
                    currPosition.y + 1).charAt(currPosition.x));
                for (Coordinates coordinates : positions) {
                    if (!currPosition.equals(coordinates)) {
                        coordinates.prev = currPosition;
                        visited.add(coordinates);
                        queue.add(coordinates);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x - 1, currPosition.y, 0))
                && map.get(currPosition.y).charAt(currPosition.x - 1) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x - 1, currPosition.y, 0);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x - 1, currPosition.y, 0))
                && Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x - 1))) {
                visited.add(new Coordinates(currPosition.x - 1, currPosition.y, 0));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y).charAt(
                    currPosition.x - 1) + map.get(currPosition.y).charAt(currPosition.x - 2)) ? portals.get("" + map.get(
                    currPosition.y).charAt(currPosition.x - 1) + map.get(currPosition.y).charAt(currPosition.x - 2))
                    : portals.get("" + map.get(currPosition.y).charAt(currPosition.x - 2) + map.get(
                    currPosition.y).charAt(currPosition.x - 1));
                for (Coordinates coordinates : positions) {
                    if (!currPosition.equals(coordinates)) {
                        coordinates.prev = currPosition;
                        visited.add(coordinates);
                        queue.add(coordinates);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x + 1, currPosition.y, 0))
                && map.get(currPosition.y).charAt(currPosition.x + 1) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x + 1, currPosition.y, 0);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x + 1, currPosition.y, 0))
                && Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x + 1))) {
                visited.add(new Coordinates(currPosition.x + 1, currPosition.y, 0));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y).charAt(
                    currPosition.x + 1) + map.get(currPosition.y).charAt(currPosition.x + 2)) ? portals.get("" + map.get(
                    currPosition.y).charAt(currPosition.x + 1) + map.get(currPosition.y).charAt(currPosition.x + 2))
                    : portals.get("" + map.get(currPosition.y).charAt(currPosition.x + 2) + map.get(
                    currPosition.y).charAt(currPosition.x + 1));
                for (Coordinates coordinates : positions) {
                    if (!currPosition.equals(coordinates)) {
                        coordinates.prev = currPosition;
                        visited.add(coordinates);
                        queue.add(coordinates);
                    }
                }
            }
        }
        int count = 0;
        for (Coordinates ptr = currPosition; ptr.prev != null; ptr = ptr.prev) {
            count++;
        }
        System.out.println("Part 1 Answer: " + count);
        for (Coordinates coordinates : portals.get("AA")) {
            currPosition = coordinates;
        }
        currPosition.prev = null;
        queue.clear();
        visited.clear();
        queue.add(currPosition);
        visited.add(currPosition);
        found = false;
        int i = 0;
        while (!queue.isEmpty()) { // runs for approximately 48 hours
            if (i%10000 == 0) {
                System.out.println(i + " iterations completed");
            }
            currPosition = queue.remove();
//            System.out.println(currPosition.layer);
            for (Coordinates coordinates : portals.get("ZZ")) {
                if (currPosition.equals(coordinates)) {
                    found = true;
                    System.out.println("found");
                    break;
                }
            }
            if (found) {
                break;
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y - 1, currPosition.layer))
                && map.get(currPosition.y - 1).charAt(currPosition.x) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x, currPosition.y - 1, currPosition.layer);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y - 1, currPosition.layer))
                && Character.isAlphabetic(map.get(currPosition.y - 1).charAt(currPosition.x)) && !(currPosition.y == 2
                && currPosition.layer == 0)) {
                visited.add(new Coordinates(currPosition.x, currPosition.y - 1, currPosition.layer));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y - 1).charAt(
                    currPosition.x) + map.get(currPosition.y - 2).charAt(currPosition.x)) ? portals.get("" + map.get(
                    currPosition.y - 1).charAt(currPosition.x) + map.get(currPosition.y - 2).charAt(currPosition.x))
                    : portals.get("" + map.get(currPosition.y - 2).charAt(currPosition.x) + map.get(
                    currPosition.y - 1).charAt(currPosition.x));
                for (Coordinates coordinates : positions) {
                    if (!(currPosition.x == coordinates.x && currPosition.y == coordinates.y)) {
                        Coordinates coordinatesCopy = new Coordinates(coordinates.x, coordinates.y, coordinates.layer);
                        coordinatesCopy.layer = currPosition.y == 2 ? currPosition.layer - 1 : currPosition.layer + 1;
                        coordinatesCopy.prev = currPosition;
                        visited.add(coordinatesCopy);
                        queue.add(coordinatesCopy);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y + 1, currPosition.layer))
                && map.get(currPosition.y + 1).charAt(currPosition.x) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x, currPosition.y + 1, currPosition.layer);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x, currPosition.y + 1, currPosition.layer))
                && Character.isAlphabetic(map.get(currPosition.y + 1).charAt(currPosition.x))
                && !(currPosition.y == map.size() - 3 && currPosition.layer == 0)) {
                visited.add(new Coordinates(currPosition.x, currPosition.y + 1, currPosition.layer));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y + 1).charAt(
                    currPosition.x) + map.get(currPosition.y + 2).charAt(currPosition.x)) ? portals.get("" + map.get(
                    currPosition.y + 1).charAt(currPosition.x) + map.get(currPosition.y + 2).charAt(currPosition.x))
                    : portals.get("" + map.get(currPosition.y + 2).charAt(currPosition.x) + map.get(
                    currPosition.y + 1).charAt(currPosition.x));
                for (Coordinates coordinates : positions) {
                    if (!(currPosition.x == coordinates.x && currPosition.y == coordinates.y)) {
                        Coordinates coordinatesCopy = new Coordinates(coordinates.x, coordinates.y, coordinates.layer);
                        coordinatesCopy.layer = currPosition.y == map.size() - 3 ? currPosition.layer - 1
                            : currPosition.layer + 1;
                        coordinatesCopy.prev = currPosition;
                        visited.add(coordinatesCopy);
                        queue.add(coordinatesCopy);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x - 1, currPosition.y, currPosition.layer))
                && map.get(currPosition.y).charAt(currPosition.x - 1) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x - 1, currPosition.y, currPosition.layer);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x - 1, currPosition.y, currPosition.layer))
                && Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x - 1)) && !(currPosition.x == 2
                && currPosition.layer == 0)) {
                visited.add(new Coordinates(currPosition.x - 1, currPosition.y, currPosition.layer));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y).charAt(
                    currPosition.x - 1) + map.get(currPosition.y).charAt(currPosition.x - 2)) ? portals.get("" + map.get(
                    currPosition.y).charAt(currPosition.x - 1) + map.get(currPosition.y).charAt(currPosition.x - 2))
                    : portals.get("" + map.get(currPosition.y).charAt(currPosition.x - 2) + map.get(
                    currPosition.y).charAt(currPosition.x - 1));
                for (Coordinates coordinates : positions) {
                    if (!(currPosition.x == coordinates.x && currPosition.y == coordinates.y)) {
                        Coordinates coordinatesCopy = new Coordinates(coordinates.x, coordinates.y, coordinates.layer);
                        coordinatesCopy.layer = currPosition.x == 2 ? currPosition.layer - 1 : currPosition.layer + 1;
                        coordinatesCopy.prev = currPosition;
                        visited.add(coordinatesCopy);
                        queue.add(coordinatesCopy);
                    }
                }
            }
            if (!visited.contains(new Coordinates(currPosition.x + 1, currPosition.y, currPosition.layer))
                && map.get(currPosition.y).charAt(currPosition.x + 1) == '.') {
                Coordinates coordinates = new Coordinates(currPosition.x + 1, currPosition.y, currPosition.layer);
                coordinates.prev = currPosition;
                visited.add(coordinates);
                queue.add(coordinates);
            }
            if (!visited.contains(new Coordinates(currPosition.x + 1, currPosition.y, currPosition.layer))
                && Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x + 1))
                && !(currPosition.x == map.get(0).length() - 3 && currPosition.layer == 0)) {
                visited.add(new Coordinates(currPosition.x + 1, currPosition.y, currPosition.layer));
                HashSet<Coordinates> positions = portals.containsKey("" + map.get(currPosition.y).charAt(
                    currPosition.x + 1) + map.get(currPosition.y).charAt(currPosition.x + 2)) ? portals.get("" + map.get(
                    currPosition.y).charAt(currPosition.x + 1) + map.get(currPosition.y).charAt(currPosition.x + 2))
                    : portals.get("" + map.get(currPosition.y).charAt(currPosition.x + 2) + map.get(
                    currPosition.y).charAt(currPosition.x + 1));
                for (Coordinates coordinates : positions) {
                    if (!(currPosition.x == coordinates.x && currPosition.y == coordinates.y)) {
                        Coordinates coordinatesCopy = new Coordinates(coordinates.x, coordinates.y, coordinates.layer);
                        coordinatesCopy.layer = currPosition.x == map.get(0).length() - 3 ? currPosition.layer - 1
                            : currPosition.layer + 1;
                        coordinatesCopy.prev = currPosition;
                        visited.add(coordinatesCopy);
                        queue.add(coordinatesCopy);
                    }
                }
            }
            i++;
        }
        count = 0;
        for (Coordinates ptr = currPosition; ptr.prev != null; ptr = ptr.prev) {
//            System.out.println(ptr.x + " " + ptr.y + " " + ptr.layer);
            count++;
        }
        System.out.println("Part 2 Answer: " + count);
    }
}