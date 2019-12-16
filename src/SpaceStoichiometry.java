import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Day 14
 * @author Rishabh Ranjan
 */
public class SpaceStoichiometry {
    private static HashMap<String, Long> unusedProducts = new HashMap<String, Long>();
    public static long getInt(String str) {
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (!(character == '0' || character =='1' || character == '2' || character == '3' || character == '4'
                || character == '5' || character == '6' || character == '7' || character == '8' || character == '9')) {
                return Long.parseLong(str.substring(0, i));
            }
        }
        return Long.parseLong(str);
    }
    public static String getString(String str) {
        return str.substring((int) ((long) Math.log10(getInt(str)) + 1));
    }
    public static String getReactants(HashMap<String, ArrayList<String>> map, String product) {
//        System.out.println("Current Product: " + product);
        long factor = 1;
        ArrayList<String> reactants = new ArrayList<>();
        if (unusedProducts.containsKey(getString(product))) {
//            System.out.println(product);
//            System.out.println("using unused products");
            if (unusedProducts.get(getString(product)) >= getInt(product)) {
                unusedProducts.replace(getString(product), (unusedProducts.get(getString(product))
                    - getInt(product)));
                return "";
            } else {
                product = (getInt(product) - unusedProducts.get(getString(product)))
                    + getString(product);
                unusedProducts.remove(getString(product));
            }
        }
//        System.out.println(product);
        if (map.containsKey(product)) {
            reactants = new ArrayList<String>(map.get(product));
        } else if (!getString(product).equals("ORE")) {
            int i;
            for (i = 1; i <= getInt(product); i++) {
                if (map.containsKey(i + getString(product))) {
                    reactants = new ArrayList<String>(map.get(i + getString(product)));
                    factor = (getInt(product)%i == 0 ? getInt(product)/i : getInt(product)/i + 1);
                    i++;
                    break;
                }
                if (i == getInt(product)) {
//                    System.out.println("looping");
                    while(true) {
                        i++;
                        if (map.containsKey(i + getString(product))) {
                            reactants = new ArrayList<String>(map.get(i + getString(product)));
//                            System.out.println(i);
                            break;
                        }
                    }
                }
            }
            i--;
            if (factor*i != getInt(product)) {
                if (unusedProducts.containsKey(getString(product))) {
                    unusedProducts.replace(getString(product), (unusedProducts.get(getString(product))
                        + (factor*i)-getInt(product)));
                } else {
                    unusedProducts.put(getString(product), ((factor*i)-getInt(product)));
                }
            }
//            System.out.println(factor);
//            System.out.println(i);
//            System.out.println(getString(product) + " " + unusedProducts.get(getString(product)));
        } else {
            reactants.add(product);
        }
        String output = "";
        for (int i = 0; i < reactants.size(); i++) {
//            System.out.println(factor);
//            System.out.println(getInt(reactants.get(i)));
            reactants.set(i, factor * getInt(reactants.get(i)) + getString(reactants.get(i)));
        }
        for (String reactant : reactants) {
            if (getString(reactant).equals("ORE")) {
                output += reactant + " ";
            } else {
                output += getReactants(map, reactant);
            }
        }
//        System.out.println("Output: " + output);
//        System.out.println(unusedProducts);
        return output;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/inputDay14.txt"));
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            boolean isProduct = false;
            String product = "";
            ArrayList<String> reactants = new ArrayList<>();
            while(sc2.hasNext()) {
                String next = sc2.next();
                if (next.equals("=>")) {
                    isProduct = true;
                    next = sc2.next();
                }
                next += sc2.next();
                if (next.charAt(next.length() - 1) == ',') {
                    next = next.substring(0, next.length() - 1);
                }
                if (!isProduct) {
                    reactants.add(next);
                } else {
                    product = next;
                }
            }
            map.put(product, reactants);
        }
//        Iterator iterator = map.entrySet().iterator();
//        Iterator iterator2 = map.entrySet().iterator();
//        while(iterator.hasNext()) {
//            String product = (String) ((Map.Entry) iterator2.next()).getKey();
//            ArrayList<String> reactants = (ArrayList<String>) ((Map.Entry) iterator.next()).getValue();
//            System.out.println(product);
//            System.out.println(reactants);
//        }
        unusedProducts.clear();
        String reactants = getReactants(map, "1FUEL");
//        System.out.println(reactants);
        Scanner sc3 = new Scanner(reactants);
        long total = 0;
        while(sc3.hasNext()) {
            total += getInt(sc3.next());
        }
        System.out.println("Part 1 Answer: " + total);
        long l = 0;
        long r = 1000000000000L;
        long m = 0;
        while(l <= r) {
            m = (l + r)/2;
            unusedProducts.clear();
            reactants = getReactants(map, m + "FUEL");
//        System.out.println(reactants);
            Scanner sc4 = new Scanner(reactants);
            total = 0;
            while(sc4.hasNext()) {
                total += getInt(sc4.next());
            }
            if (total > 1000000000000L) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        System.out.println("Part 2 Answer: " + (m - 1));
    }
}
