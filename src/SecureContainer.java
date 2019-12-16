/**
 * Day 4
 * @author Rishabh Ranjan
 */
public class SecureContainer {
    public static void part1() {
        int count = 0;
        for (int i = 193651; i < 649730; i++) {
            if ((i/100000 == (i/10000)%10 || (i/10000)%10 == (i/1000)%10 || (i/1000)%10 == (i/100)%10
                || (i/100)%10 == (i/10)%10 || (i/10)%10 == i%10) && (i/100000 <= (i/10000)%10
                && (i/10000)%10 <= (i/1000)%10 && (i/1000)%10 <= (i/100)%10 && (i/100)%10 <= (i/10)%10
                && (i/10)%10 <= i%10)) {
                count++;
            }
        }
        System.out.println("Part 1 Answer: " + count);
    }
    public static void part2() {
        int count = 0;
        for (int i = 193651; i < 649730; i++) {
            boolean twoConsecutive = false;
            for (int j = 0; j < 10; j++) {
                int consecutive = 0;
                int l = 5;
                for (int k; l >= 0; l--) {
                    k = ((i/((int) (Math.pow(10, l))))%10);
                    if (j == k) {
                        consecutive++;
                        if (consecutive == 2 && l == 0) {
                            twoConsecutive = true;
                            break;
                        }
                    } else {
                        if (consecutive == 2) {
                            twoConsecutive = true;
                            break;
                        }
                        consecutive = 0;
                    }
                }
                if (twoConsecutive) {
                    break;
                }
            }
            if (twoConsecutive && (i/100000 <= (i/10000)%10 && (i/10000)%10 <= (i/1000)%10 && (i/1000)%10 <= (i/100)%10
                && (i/100)%10 <= (i/10)%10 && (i/10)%10 <= i%10)) {
                count++;
            }
        }
        System.out.println("Part 2 Answer: " + count);
    }
    public static void main(String[] args) {
        part1();
        part2();
    }
}
