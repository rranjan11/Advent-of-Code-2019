import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Asteroid implements Comparable<Asteroid> {
    int x;
    int y;
    double arcTan;
    Asteroid(int x, int y, double arcTan) {
        this.x = x;
        this.y = y;
        this.arcTan = arcTan;
    }
    @Override
    public int compareTo(Asteroid other) {
        return -Double.compare(arcTan, other.arcTan);
    }
    @Override
    public String toString() {
        return x + " " + y + " " + arcTan;
    }
}

/**
 * Day 10
 * @author Rishabh Ranjan
 */
public class MonitoringStation {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay10.txt"));
        ArrayList<String> asteroidBelt = new ArrayList<>();
        while (sc.hasNextLine()) {
            asteroidBelt.add(sc.nextLine());
        }
        int max = 0;
        int maxI = 0;
        int maxJ = 0;
        for (int i = 0; i < asteroidBelt.size(); i++) {
            for (int j = 0; j < asteroidBelt.get(0).length(); j++) {
//                System.out.println(i + " " + j);
                if (asteroidBelt.get(i).charAt(j) == '#') {
                    int detected = 0;
                    for (int k = 0; k < asteroidBelt.size(); k++) {
                        for (int l = 0; l < asteroidBelt.get(0).length(); l++) {
                            boolean detectable = false;
                            if (asteroidBelt.get(k).charAt(l) == '#' && (k != i || l != j)) {
                                detectable = true;
                                for (int m = 0; m < asteroidBelt.size(); m++) {
                                    for (int n = 0; n < asteroidBelt.get(0).length(); n++) {
                                        if (asteroidBelt.get(m).charAt(n) == '#' && (m != k || n != l) && (m != i
                                            || n != j) && ((m >= i && m <= k) || (m <= i && m >= k))
                                            && ((n >= j && n <= l) || (n <= j && n >= l)) && (((m == k && m == i)
                                            || (n == l && n == j)) || ((((double) (m - i))/(k - i))
                                            == (((double)(n - j))/(l - j))))) {
                                            detectable = false;
                                            break;
                                        }
                                    }
                                    if (!detectable) {
                                        break;
                                    }
                                }
                            }
                            if (detectable) {
                                detected++;
                            }
                        }
                    }
                    if (detected > max) {
                        max = detected;
                        maxI = i;
                        maxJ = j;
                    }
                }
            }
        }
        System.out.println("Part 1 Answer " + max);
        ArrayList<Asteroid> quadrant1 = new ArrayList<>();
        ArrayList<Asteroid> quadrant2 = new ArrayList<>();
        ArrayList<Asteroid> quadrant3 = new ArrayList<>();
        ArrayList<Asteroid> quadrant4 = new ArrayList<>();
        Asteroid checkpoint1 = new Asteroid(0, 0, 0);
        Asteroid checkpoint2 = new Asteroid(0, 0, 0);
        Asteroid checkpoint3 = new Asteroid(0, 0, 0);
        Asteroid checkpoint4 = new Asteroid(0, 0, 0);
        int i = maxI;
        int j = maxJ;
        for (int k = 0; k < asteroidBelt.size(); k++) {
            for (int l = 0; l < asteroidBelt.get(0).length(); l++) {
                boolean detectable = false;
                if (asteroidBelt.get(k).charAt(l) == '#' && (k != i || l != j)) {
                    detectable = true;
                    for (int m = 0; m < asteroidBelt.size(); m++) {
                        for (int n = 0; n < asteroidBelt.get(0).length(); n++) {
                            if (asteroidBelt.get(m).charAt(n) == '#' && (m != k || n != l) && (m != i
                                || n != j) && ((m >= i && m <= k) || (m <= i && m >= k))
                                && ((n >= j && n <= l) || (n <= j && n >= l)) && (((m == k && m == i)
                                || (n == l && n == j)) || ((((double) (m - i))/(k - i))
                                == (((double)(n - j))/(l - j))))) {
                                detectable = false;
                                break;
                            }
                        }
                        if (!detectable) {
                            break;
                        }
                    }
                }
                if (detectable) {
                    if (k < i && l > j) {
                        quadrant1.add(new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i))));
                    } else if (k > i && l > j) {
                        quadrant2.add(new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i))));
                    } else if (k > i && l < j) {
                        quadrant3.add(new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i))));
                    } else if (k < i && l < j) {
                        quadrant4.add(new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i))));
                    } else if (k < i) {
                        checkpoint1 = new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i)));
                    } else if (l > j) {
                        checkpoint2 = new Asteroid(k, l, 0);
                    } else if (k > i) {
                        checkpoint3 = new Asteroid(k, l, Math.atan(((double)(l - j))/(k - i)));
                    } else if (l < j) {
                        checkpoint4 = new Asteroid(k, l, 0);
                    }
                }
            }
        }
        Collections.sort(quadrant1);
        Collections.sort(quadrant2);
        Collections.sort(quadrant3);
        Collections.sort(quadrant4);
//        System.out.println(quadrant1);
//        System.out.println(quadrant2);
//        System.out.println(quadrant3);
//        System.out.println(quadrant4);
        ArrayList<Asteroid> detectableAsteroids = new ArrayList<>();
        detectableAsteroids.add(checkpoint1);
        detectableAsteroids.addAll(quadrant1);
        detectableAsteroids.add(checkpoint2);
        detectableAsteroids.addAll(quadrant2);
        detectableAsteroids.add(checkpoint3);
        detectableAsteroids.addAll(quadrant3);
        detectableAsteroids.add(checkpoint4);
        detectableAsteroids.addAll(quadrant4);
        System.out.println("Part 2 Answer: " + (detectableAsteroids.get(199).x*100 + detectableAsteroids.get(199).y));
    }
}