import java.util.List;

class Result {
    boolean halt;
    long output;
    int newStartIndex;
    long newRelativeBase;
    boolean inputExpected;
    Result(boolean halt, long output, int newStartIndex, long newRelativeBase, boolean inputExpected) {
        this.halt = halt;
        this.output = output;
        this.newStartIndex = newStartIndex;
        this.newRelativeBase = newRelativeBase;
        this.inputExpected = inputExpected;
    }
}

/**
 * Intcode Computer used in Days 2, 5, 7, 9, 11, 13, 15, 17, 19, and 21
 * @author Rishabh Ranjan
 */
public class IntcodeComputer {
    public static int linearSearch(long[] arr, long target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    public static long getParameter(List<Long> list, int index, long mode, long relativeBase) {
        if (mode == 0L) {
            if (index >= list.size()) {
                for (int i = 0; i < index - list.size() + 1; i++) {
                    list.add(0L);
                }
            }
            return list.get(index);
        } else if (mode == 1L) {
            return index;
        } else if (mode == 2L) {
            if (index >= list.size()) {
                for (int i = 0; i < index - list.size() + 1; i++) {
                    list.add(0L);
                }
            }
            return list.get(index) + relativeBase;
        } else {
            System.out.println("Parameter Error");
            return -1;
        }
    }
    public static void method1(List<Long> list, int i, long relativeBase) {
        int parameter1 = (int) getParameter(list, i + 3, (int) (list.get(i)/10000)%10, relativeBase);
        int parameter2 = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        int parameter3 = (int) getParameter(list, i + 2, (list.get(i)/1000)%10, relativeBase);
        if (parameter1 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter1 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter2 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter2 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter3 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter3 - size + 1; j++) {
                list.add(0L);
            }
        }
        list.set(parameter1, list.get(parameter2) + list.get(parameter3));
    }
    public static void method2(List<Long> list, int i, long relativeBase) {
        int parameter1 = (int) getParameter(list, i + 3, (int) (list.get(i)/10000)%10, relativeBase);
        int parameter2 = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        int parameter3 = (int) getParameter(list, i + 2, (list.get(i)/1000)%10, relativeBase);
        if (parameter1 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter1 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter2 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter2 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter3 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter3 - size + 1; j++) {
                list.add(0L);
            }
        }
        list.set(parameter1, list.get(parameter2) * list.get(parameter3));
    }
    public static void method3(List<Long> list, int i, long input, long relativeBase) {
        int parameter = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        if (parameter >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter - size + 1; j++) {
//                System.out.println("added");
                list.add(0L);
            }
        }
        list.set(parameter, input);
    }
    public static long method4(List<Long> list, int i, long relativeBase) {
        int parameter = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        if (parameter >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter - size + 1; j++) {
                list.add(0L);
            }
        }
        return list.get(parameter);
    }
    public static boolean method5(List<Long> list, int i, long relativeBase) {
        int parameter = (int) getParameter(list, i + 1, (list.get(i) / 100) % 10, relativeBase);
        if (parameter >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter - size + 1; j++) {
                list.add(0L);
            }
        }
        return list.get(parameter) != 0;
    }
    public static boolean method6(List<Long> list, int i, long relativeBase) {
        int parameter = (int) getParameter(list, i + 1, (list.get(i) / 100) % 10, relativeBase);
        if (parameter >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter - size + 1; j++) {
                list.add(0L);
            }
        }
        return list.get(parameter) == 0;
    }
    public static void method7(List<Long> list, int i, long relativeBase) {
        int parameter1 = (int) getParameter(list, i + 3, (int) (list.get(i)/10000)%10, relativeBase);
        int parameter2 = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        int parameter3 = (int) getParameter(list, i + 2, (list.get(i)/1000)%10, relativeBase);
        if (parameter1 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter1 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter2 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter2 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter3 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter3 - size + 1; j++) {
                list.add(0L);
            }
        }
        list.set(parameter1, (list.get(parameter2) < list.get(parameter3) ? 1L : 0L));
    }
    public static void method8(List<Long> list, int i, long relativeBase) {
        int parameter1 = (int) getParameter(list, i + 3, (int) (list.get(i)/10000)%10, relativeBase);
        int parameter2 = (int) getParameter(list, i + 1, (list.get(i)/100)%10, relativeBase);
        int parameter3 = (int) getParameter(list, i + 2, (list.get(i)/1000)%10, relativeBase);
        if (parameter1 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter1 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter2 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter2 - size + 1; j++) {
                list.add(0L);
            }
        }
        if (parameter3 >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter3 - size + 1; j++) {
                list.add(0L);
            }
        }
        list.set(parameter1, list.get(parameter2).equals(list.get(parameter3)) ? 1L : 0L);
    }
    public static long method9(List<Long> list, int i, long relativeBase) {
        int parameter = (int) getParameter(list, i + 1, (list.get(i) / 100) % 10, relativeBase);
        if (parameter >= list.size()) {
            int size = list.size();
            for (int j = 0; j < parameter - size + 1; j++) {
                list.add(0L);
            }
        }
        return list.get(parameter);
    }
    public static Result computer(List<Long> list, int startIndex, long relativeBase, boolean inputReceived, int input) {
//        System.out.println(list);
        boolean inputExpected = false;
        boolean inputUsed = false;
        boolean halt = false;
        long output = 0;
        int newStartIndex = 0;
        for (int i = startIndex;;) {
            if (i >= list.size()) {
                int size = list.size();
                for (int j = 0; j < i - size + 1; j++) {
                    list.add(0L);
                }
            }
//            System.out.println(list);
//            System.out.println(relativeBase);
//            System.out.println(i);
//            System.out.println(list.get(i));
//            System.out.println(list.get(i + 1));
//            System.out.println(list.get(i + 2));
//            System.out.println(list.get(i + 3));
            if (list.get(i) == 99L) {
                halt = true;
                break;
            }
            if (list.get(i)%100 == 1) {
                method1(list, i, relativeBase);
                i += 4;
            } else if (list.get(i)%100 == 2) {
                method2(list, i, relativeBase);
                i += 4;
            } else if (list.get(i)%100 == 3) {
                if (!inputReceived || inputUsed) {
                    newStartIndex = i;
                    inputExpected = true;
                    break;
                }
                method3(list, i, input, relativeBase);
                inputUsed = true;
                i += 2;
            } else if (list.get(i)%100 == 4) {
                output = method4(list, i, relativeBase);
//                System.out.println("Outputting " + output);
                i += 2;
                newStartIndex = i;
                break;
            } else if (list.get(i)%100 == 5) {
                if (method5(list, i, relativeBase)) {
                    i = Math.toIntExact(list.get((int) getParameter(list, i + 2, (list.get(i) / 1000) % 10,
                        relativeBase)));
                } else {
                    i += 3;
                }
            } else if (list.get(i)%100 == 6) {
                if (method6(list, i, relativeBase)) {
                    i = Math.toIntExact(list.get((int) getParameter(list, i + 2, (list.get(i) / 1000) % 10,
                        relativeBase)));
                } else {
                    i += 3;
                }
            } else if (list.get(i)%100 == 7) {
                method7(list, i, relativeBase);
                i += 4;
            } else if (list.get(i)%100 == 8) {
                method8(list, i, relativeBase);
                i += 4;
            } else if (list.get(i)%100 == 9) {
                relativeBase += method9(list, i, relativeBase);
                i += 2;
            } else {
                System.out.println("Opcode Error");
                halt = true;
                break;
            }
        }
        return new Result(halt, output, newStartIndex, relativeBase, inputExpected);
    }
}