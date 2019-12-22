// This program is unfinished

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class SlamShuffle {
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> deck = new ArrayList<>();
        for (int i = 0; i < 10007; i++) {
            deck.add(i);
        }
        Scanner sc = new Scanner(new File("src/inputDay22.txt"));
        ArrayList<String> instructions = new ArrayList<>();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            instructions.add(str);
            if (str.length() > 18 && str.substring(0, 19).equals("deal with increment")) {
                int increment = Integer.parseInt(str.substring(20));
                List<Integer> tempDeck = new ArrayList<>();
                for (int i = 0; i < deck.size(); i++) {
                    tempDeck.add(0);
                }
                for (int i = 0; i < deck.size(); i++) {
                    tempDeck.set((increment*i)%(deck.size()), deck.get(i));
                }
                deck = tempDeck;
            } else if (str.length() > 2 && str.substring(0, 3).equals("cut")) {
                int cut = Integer.parseInt(str.substring(4));
                if (cut < 0) {
                    List<Integer> bottom = deck.subList(deck.size() + cut, deck.size());
                    List<Integer> top = deck.subList(0, deck.size() + cut);
                    bottom.addAll(top);
                    deck = bottom;
                } else {
                    List<Integer> top = deck.subList(0, cut);
                    List<Integer> bottom = deck.subList(cut, deck.size());
                    bottom.addAll(top);
                    deck = bottom;
                }
            } else if (str.length() > 18 && str.substring(0, 19).equals("deal into new stack")) {
                List<Integer> tempDeck = new ArrayList<>();
                for (int i = deck.size() - 1; i >= 0; i--) {
                    tempDeck.add(deck.get(i));
                }
                deck = tempDeck;
            } else {
                System.out.println("Error");
            }
        }
        System.out.println("Part 1 Answer: " + deck.indexOf(2019));
        long pos = 2020;
        long deckSize = 119315717514047L;
        HashSet<Long> visited = new HashSet<>();
        for (long k = 0; k < 101741582076661L; k++) {
//            if (visited.contains(pos)) {
//                break;
//            }
//            visited.add(pos);
            for (int i = instructions.size() - 1; i >= 0; i--) {
                String str = instructions.get(i);
                if (str.length() > 18 && str.substring(0, 19).equals("deal with increment")) {
                    int increment = Integer.parseInt(str.substring(20));
                    long j = 0;
                    while (true) {
                        if (((double) (pos + deckSize*j))/increment - (double) ((pos + deckSize*j)/increment) == 0) {
                            pos = (pos + deckSize*j)/increment;
                            break;
                        }
                        j++;
                    }
                } else if (str.length() > 2 && str.substring(0, 3).equals("cut")) {
                    int cut = Integer.parseInt(str.substring(4));
                    pos = (pos + cut)%deckSize;
                } else if (str.length() >18 && str.substring(0, 19).equals("deal into new stack")) {
                    pos = deckSize - 1 - pos;
                } else {
                    System.out.println("Error");
                }
//                System.out.println(pos);
            }
            if (k%1000000 == 0) {
                System.out.println((k + 1) + " " + pos);
            }
//            System.out.println((k + 1) + " " + pos);
            if (pos == 2020) {
                System.out.println((k + 1) + " " + pos);
                break;
            }
        }
        System.out.println(pos);
    }
}