package easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NumberOperations {
    private List<Integer> numbers;
    public NumberOperations(List<Integer> numbers) {
        this.numbers = numbers;
    }
    public int findMinimum() {
        return Collections.min(numbers);
    }
    public int findMaximum() {
        return Collections.max(numbers);
    }
    public List<Integer> sortAscending() {
        List<Integer> sortedList = new ArrayList<>(numbers);
        Collections.sort(sortedList);
        return sortedList;
    }
    public List<Integer> sortDescending() {
        List<Integer> sortedList = new ArrayList<>(numbers);
        Collections.sort(sortedList, Collections.reverseOrder());
        return sortedList;
    }
    public boolean contains(int target) {
        return numbers.contains(target);
    }
    public List<Integer> filterGreaterThan(int threshold) {
        return numbers.stream()
                .filter(num -> num > threshold)
                .collect(Collectors.toList());
    }
    public int sum() {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }



}
