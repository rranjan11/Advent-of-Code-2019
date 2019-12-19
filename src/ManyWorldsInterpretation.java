// This program is unfinished

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

class XYPosition {
    int x;
    int y;
    XYPosition prev;
    XYPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof XYPosition)) {
            return false;
        }
        XYPosition otherXYPosition = (XYPosition) other;
        return x == otherXYPosition.x && y == otherXYPosition.y;
    }
    @Override
    public int hashCode() {
        return 0;
    }
}

/**
 * Day 18
 * @author Rishabh Ranjan
 */
public class ManyWorldsInterpretation {
    public static int getShortestPath(ArrayList<String> map, HashMap<Character, XYPosition> keys,
                                       HashMap<Character, XYPosition> doors, int x, int y) {
        HashMap<Character, Integer> reachableKeys = getReachableKeys(map, x, y, keys, doors);
        System.out.println(reachableKeys);
        int min = Integer.MAX_VALUE;
        char bestKey = ' ';
        if (reachableKeys.isEmpty()) {
            return 0;
        }
        for (Map.Entry<Character, Integer> reachableKey : reachableKeys.entrySet()) {
            x = keys.get(reachableKey.getKey()).x;
            y = keys.get(reachableKey.getKey()).y;
            ArrayList<String> mapCopy = (ArrayList<String>) map.clone();
            HashMap<Character, XYPosition> keysCopy = (HashMap<Character, XYPosition>) keys.clone();
            keysCopy.remove(reachableKey.getKey());
            HashMap<Character, XYPosition> doorsCopy = (HashMap<Character, XYPosition>) doors.clone();
            doorsCopy.remove(Character.toUpperCase(reachableKey.getKey()));
            int pathLength = getShortestPath(mapCopy, keysCopy, doorsCopy, x, y);
            if (pathLength < min) {
                min = pathLength;
                bestKey = reachableKey.getKey();
            }
        }
        return min + reachableKeys.get(bestKey);
    }
    public static HashMap<Character, Integer> getReachableKeys(ArrayList<String> map, int x, int y,
                                                      HashMap<Character, XYPosition> keys,
                                                      HashMap<Character, XYPosition> doors) {
        HashMap<Character, Integer> reachableKeys = new HashMap<>();
//        System.out.println(x + " " +  y);
        XYPosition currPosition = new XYPosition(x, y);
        Queue<XYPosition> queue = new ConcurrentLinkedQueue<>();
        HashSet<XYPosition> visited = new HashSet<>();
        queue.add(currPosition);
        visited.add(currPosition);
        while (!queue.isEmpty()) {
//            System.out.println(queue.size());
            currPosition = queue.remove();
//            System.out.println(map.get(currPosition.y).charAt(currPosition.x));
            if (Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x))
                && Character.isLowerCase(map.get(currPosition.y).charAt(currPosition.x))
                && keys.containsKey(map.get(currPosition.y).charAt(currPosition.x))) {
                int count = 0;
                for (XYPosition ptr = currPosition; ptr.prev != null; ptr = ptr.prev) {
//                    System.out.println(ptr.x + " " + ptr.y + ", " + ptr.prev.x + " " + ptr.prev.y);
                    count++;
                }
//                System.out.println("key added");
                reachableKeys.put(map.get(currPosition.y).charAt(currPosition.x), count);
            }
            if ((currPosition.y != 0 && (map.get(currPosition.y - 1).charAt(currPosition.x) == '.'
                || (Character.isAlphabetic(map.get(currPosition.y - 1).charAt(currPosition.x))
                && !doors.containsKey(map.get(currPosition.y - 1).charAt(currPosition.x)))))
                && !visited.contains(new XYPosition(currPosition.x, currPosition.y - 1))) {
//                if (map.get(currPosition.y - 1).charAt(currPosition.x) == '#') {
//                    System.out.println("up");
//                }
                XYPosition position = new XYPosition(currPosition.x, currPosition.y - 1);
                position.prev = currPosition;
                visited.add(position);
                queue.add(position);
            }
            if ((currPosition.y != map.size() - 1 && (map.get(currPosition.y + 1).charAt(currPosition.x) == '.'
                || (Character.isAlphabetic(map.get(currPosition.y + 1).charAt(currPosition.x))
                && !doors.containsKey(map.get(currPosition.y + 1).charAt(currPosition.x)))))
                && !visited.contains(new XYPosition(currPosition.x, currPosition.y + 1))) {
//                if (map.get(currPosition.y + 1).charAt(currPosition.x) == '#') {
//                    System.out.println("down");
//                }
                XYPosition position = new XYPosition(currPosition.x, currPosition.y + 1);
                position.prev = currPosition;
                visited.add(position);
                queue.add(position);
            }
            if ((currPosition.x != 0 && (map.get(currPosition.y).charAt(currPosition.x - 1) == '.'
                || (Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x - 1))
                && !doors.containsKey(map.get(currPosition.y).charAt(currPosition.x - 1)))))
                && !visited.contains(new XYPosition(currPosition.x - 1, currPosition.y))) {
//                if (map.get(currPosition.y).charAt(currPosition.x - 1) == '#') {
//                    System.out.println("left");
//                }
                XYPosition position = new XYPosition(currPosition.x - 1, currPosition.y);
                position.prev = currPosition;
                visited.add(position);
                queue.add(position);
            }
            if ((currPosition.x != map.get(0).length() - 1 && (map.get(currPosition.y).charAt(currPosition.x + 1) == '.'
                || (Character.isAlphabetic(map.get(currPosition.y).charAt(currPosition.x + 1))
                && !doors.containsKey(map.get(currPosition.y).charAt(currPosition.x + 1)))))
                && !visited.contains(new XYPosition(currPosition.x + 1, currPosition.y))) {
//                if (map.get(currPosition.y).charAt(currPosition.x + 1) == '#') {
//                    System.out.println("right");
//                }
                XYPosition position = new XYPosition(currPosition.x + 1, currPosition.y);
                position.prev = currPosition;
                visited.add(position);
                queue.add(position);
            }
        }
//        System.out.println("Done");
        return reachableKeys;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay18.txt"));
//        Scanner sc = new Scanner(new File("src/test.txt"));
        ArrayList<String> map = new ArrayList<>();
        int x = 0;
        int y = 0;
        HashMap<Character, XYPosition> keys = new HashMap<>();
        HashMap<Character, XYPosition> doors = new HashMap<>();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '@') {
                    x = i;
                    y = map.size();
                    str = str.substring(0, i) + '.' + str.substring(i + 1);
//                    System.out.println(str);
                } else if (Character.isAlphabetic(str.charAt(i))) {
                    if (Character.isUpperCase(str.charAt(i))) {
                        doors.put(str.charAt(i), new XYPosition(i, map.size()));
                    } else if (Character.isLowerCase(str.charAt(i))) {
                        keys.put(str.charAt(i), new XYPosition(i, map.size()));
                    }
                }
            }
            map.add(str);
        }
        for (String str : map) {
            System.out.println(str);
        }
        System.out.println(x + " " + y);
        System.out.println(keys + " " + doors);
        for (Map.Entry<Character, XYPosition> key : keys.entrySet()) {
            System.out.println(key.getKey() + " " + key.getValue().x + " " + key.getValue().y);
        }
        System.out.println(getShortestPath(map, keys, doors, x, y));
//        System.out.println(Character.isAlphabetic('A'));
    }
}
