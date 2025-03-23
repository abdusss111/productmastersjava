package hard;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.Person;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("Almaty").build(),
                Person.builder().name("Bob").age(17).city("Astana").build(),
                Person.builder().name("Charlie").age(30).city("Almaty").build(),
                Person.builder().name("David").age(19).city("Shymkent").build(),
                Person.builder().name("Emma").age(40).city("Almaty").build(),
                Person.builder().name("Frank").age(35).city("Astana").build(),
                Person.builder().name("Grace").age(50).city("Shymkent").build()
        );

        List<Person> top3Oldest = people.stream()
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Top 3 oldest: " + top3Oldest);

        List<Company> companies = List.of(
                Company.builder().name("TechCorp").employees(List.of(people.get(0), people.get(2), people.get(4))).build(),
                Company.builder().name("InnovateX").employees(List.of(people.get(3), people.get(5), people.get(6))).build()
        );

        Map<String, Double> companyAverageAge = companies.stream()
                .collect(Collectors.toMap(
                        Company::getName,
                        company -> company.getEmployees().stream()
                                .mapToInt(Person::getAge)
                                .average()
                                .orElse(0)
                ));

        System.out.println("Company average age: " + companyAverageAge);
    }
}