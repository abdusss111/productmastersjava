package org.example;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    private String city;
}
