import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

class SpaceObject {
    SpaceObject orbiting;
    ArrayList<SpaceObject> orbiters = new ArrayList<>();
    String name;
    SpaceObject prev;
    SpaceObject(SpaceObject orbiting, String name) {
        this.orbiting = orbiting;
        this.name = name;
    }
}

/**
 * Day 6
 * @author Rishabh Ranjan
 */
public class UniversalOrbitMap {
    public static int countOrbits(SpaceObject object) {
        int count = 0;
        for (SpaceObject ptr = object.orbiting; ptr != null; ptr = ptr.orbiting) {
            count++;
        }
        return count;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay6.txt"));
        HashMap<String, SpaceObject> objects = new HashMap<>();
        while (sc.hasNextLine()) {
            String relation = sc.nextLine();
            SpaceObject orbited = objects.containsKey(relation.substring(0, 3)) ? objects.get(relation.substring(0, 3))
                : new SpaceObject(null, relation.substring(0, 3));
            SpaceObject orbiter = objects.containsKey(relation.substring(4)) ? objects.get(relation.substring(4))
                : new SpaceObject(orbited, relation.substring(4));
            orbiter.orbiting = orbited;
            orbited.orbiters.add(orbiter);
            objects.putIfAbsent(orbited.name, orbited);
            objects.putIfAbsent(orbiter.name, orbiter);
        }
        Iterator iterator = objects.entrySet().iterator();
        int count = 0;
        while(iterator.hasNext()) {
            SpaceObject object = (SpaceObject) ((Map.Entry) iterator.next()).getValue();
//            System.out.println(object.name);
            count += countOrbits(object);
        }
        System.out.println("Part 1 Answer: " + count);
        SpaceObject you = objects.get("YOU");
        SpaceObject santa = objects.get("SAN");
        List<SpaceObject> visited = new ArrayList<>();
        visited.add(you);
        Queue<SpaceObject> queue = new ConcurrentLinkedQueue<>();
        queue.add(you);
        while (!queue.isEmpty()) {
            SpaceObject currObject = queue.remove();
            if (currObject.name.equals(santa.name)) {
                break;
            }
            if (currObject.orbiting != null && !visited.contains(currObject.orbiting)) {
                visited.add(currObject.orbiting);
                currObject.orbiting.prev = currObject;
                queue.add(currObject.orbiting);
            }
            for (SpaceObject object : currObject.orbiters) {
                if (!visited.contains(object)) {
                    visited.add(object);
                    object.prev = currObject;
                    queue.add(object);
                }
            }
        }
        count = 0;
        for (SpaceObject ptr = santa.prev; ptr != you; ptr = ptr.prev) {
            count++;
        }
        System.out.println("Part 2 Answer: " + (count - 1));
    }
}