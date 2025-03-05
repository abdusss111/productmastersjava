package easy;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> nums = List.of(10, 20, 5, 30, 15, 43, 52, 74);

        NumberOperations numberOperations = new NumberOperations(nums);
        System.out.println(numberOperations.sum());
        System.out.println(numberOperations.contains(21));
        System.out.println(numberOperations.filterGreaterThan(35));
        System.out.println(numberOperations.findMaximum());
        System.out.println(numberOperations.findMinimum());
        System.out.println(numberOperations.sortAscending());
        System.out.println(numberOperations.sortDescending());
    }
}
