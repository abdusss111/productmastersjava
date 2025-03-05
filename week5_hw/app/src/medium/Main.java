package medium;
import easy.DayOfWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<DayOfWeek> days = new ArrayList<>(Arrays.asList(DayOfWeek.values()));

        System.out.println("Дни недели:");
        for (DayOfWeek day : days) {
            System.out.println(day);
        }

        System.out.println("\nВыходные дни:");
        for (DayOfWeek day : days) {
            if (isWeekend(day)) {
                System.out.println(day + " - выходной");
            }
        }

    }
    public static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}

