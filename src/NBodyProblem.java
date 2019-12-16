import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Moon {
    int xPos;
    int yPos;
    int zPos;
    int xVel;
    int yVel;
    int zVel;
    Moon(int xPos, int yPos, int zPos, int xVel, int yVel, int zVel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.zVel = zVel;
    }
    @Override
    public String toString() {
        return "Position: " + xPos + ", " + yPos + ", " + zPos + "\nVelocity: " + xVel + ", " + yVel + ", " + zVel + "\n";
    }
}

/**
 * Day 12
 * @author Rishabh Ranjan
 */
public class NBodyProblem {
    public static int[] toXState(ArrayList<Moon> moons) {
        int[] arr = new int[8];
        int i = 0;
        for (Moon moon : moons) {
            arr[i] = moon.xPos;
            arr[i + 1] = moon.xVel;
            i += 2;
        }
        return arr;
    }
    public static int[] toYState(ArrayList<Moon> moons) {
        int[] arr = new int[8];
        int i = 0;
        for (Moon moon : moons) {
            arr[i] = moon.yPos;
            arr[i + 1] = moon.yVel;
            i += 2;
        }
        return arr;
    }
    public static int[] toZState(ArrayList<Moon> moons) {
        int[] arr = new int[8];
        int i = 0;
        for (Moon moon : moons) {
            arr[i] = moon.zPos;
            arr[i + 1] = moon.zVel;
            i += 2;
        }
        return arr;
    }
    public static int[] run1000(int[] state) {
        for (int i = 0; i < 1000; i++) {
//            System.out.println(i);
            for (int j = 0; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    if (!(state[2*j] == state[2*k])) {
                        if (state[2*j] < state[2*k]) {
                            state[2*j + 1] += 1;
                            state[2*k + 1] -= 1;
                        } else {
                            state[2*j + 1] -= 1;
                            state[2*k + 1] += 1;
                        }
                    }
                }
            }
//            System.out.println("Gravity Timestep " + (i + 1));
//            System.out.println(moons);
            for (int j = 0; j < 4; j++) {
                state[2*j] += state[2*j + 1];
            }
//            System.out.println("Velocity Timestep " + (i + 1));
//            System.out.println(moons);
        }
        return state;
    }
    public static int findDuplicate(int[] state) {
        int i = 0;
        int[] initialState = state.clone();
        while(true) {
//            System.out.println(i);
//            if (i%100000 == 0) {
//                System.out.println("Timestep " + i);
//            }
            if (i != 0 && Arrays.equals(state, initialState)) {
//                System.out.println(i);
//                for (int j : state) {
//                    System.out.print(j + " ");
//                }
//                System.out.println();
//                System.out.println("Cycle Found");
                return i;
            }
            for (int j = 0; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    if (!(state[2*j] == state[2*k])) {
                        if (state[2*j] < state[2*k]) {
                            state[2*j + 1] += 1;
                            state[2*k + 1] -= 1;
                        } else {
                            state[2*j + 1] -= 1;
                            state[2*k + 1] += 1;
                        }
                    }
                }
            }
//            System.out.println("Gravity Timestep " + (i + 1));
//            System.out.println(moons);
            for (int j = 0; j < 4; j++) {
                state[2*j] += state[2*j + 1];
            }
//            System.out.println("Velocity Timestep " + (i + 1));
//            System.out.println(moons);
            i++;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay12.txt"));
        ArrayList<Moon> moons = new ArrayList<>();
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            Scanner sc2 = new Scanner(nextLine);
            String xString = sc2.next();
            String yString = sc2.next();
            String zString = sc2.next();
            int x = Integer.parseInt(xString.substring(3, xString.length() - 1));
            int y = Integer.parseInt(yString.substring(2, yString.length() - 1));
            int z = Integer.parseInt(zString.substring(2, zString.length() - 1));
            Moon newMoon = new Moon(x, y, z, 0, 0, 0);
            moons.add(newMoon);
        }
        sc.close();
        int[] xState = run1000(toXState(moons));
        int[] yState = run1000(toYState(moons));
        int[] zState = run1000(toZState(moons));
        int energy = 0;
        for (int j = 0; j < 4; j++) {
            int potEnergy = Math.abs(xState[2*j]) + Math.abs(yState[2*j]) + Math.abs(zState[2*j]);
            int kinEnergy = Math.abs(xState[2*j + 1]) + Math.abs(yState[2*j + 1]) + Math.abs(zState[2*j + 1]);
            energy += potEnergy*kinEnergy;
        }
        System.out.println("Part 1 Answer: " + energy);
        long x = findDuplicate(toXState(moons));
        long y = findDuplicate(toYState(moons));
        long z = findDuplicate(toZState(moons));
        int i = 1;
        long xy = 0;
        while(true) {
            if ((i*x)%y == 0) {
                xy = i*x;
                break;
            }
            i++;
        }
        long xyz = 0;
        i = 1;
        while(true) {
            if ((i*xy)%z == 0) {
                xyz = i*xy;
                break;
            }
            i++;
        }
        System.out.println("Part 2 Answer: " + xyz);
    }
}