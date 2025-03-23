package medium;

import java.util.*;
import java.util.stream.Collectors;
import org.example.Person;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("Almaty").build(),
                Person.builder().name("Bob").age(17).city("Astana").build(),
                Person.builder().name("Charlie").age(30).city("Almaty").build(),
                Person.builder().name("David").age(19).city("Shymkent").build(),
                Person.builder().name("Emma").age(40).city("Almaty").build()
        );

        List<Person> adults = people.stream()
                .filter(person -> person.getAge() > 18)
                .collect(Collectors.toList());
        System.out.println("Adults: " + adults);

        double averageAge = people.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);  // Если список пустой, вернуть 0
        System.out.println("Average age: " + averageAge);

        List<String> almatyPeople = people.stream()
                .filter(person -> "Almaty".equals(person.getCity()))
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("People from Almaty: " + almatyPeople);

        Map<String, Integer> nameToAgeMap = people.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Name to Age Map: " + nameToAgeMap);
    }
}
