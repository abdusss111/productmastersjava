package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
                Person.builder().name("Alice").age(25).city("New York").build(),
                Person.builder().name("Bob").age(30).city("Los Angeles").build(),
                Person.builder().name("Charlie").age(35).city("Chicago").build()
        );

        people.forEach(System.out::println);
    }
}

