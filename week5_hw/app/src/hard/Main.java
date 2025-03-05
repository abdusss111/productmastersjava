package hard;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("\nВведите числа (для завершения введите любое нечисловое значение):");

        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextInt());
        }

        scanner.close();

        ArrayList<Integer> uniqueNumbers = removeDuplicates(numbers);

        System.out.println("\nСписок без дубликатов:");
        for (int num : uniqueNumbers) {
            System.out.print(num + " ");
        }
    }

    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
        LinkedHashSet<Integer> set = new LinkedHashSet<>(list);
        return new ArrayList<>(set);
    }
}
