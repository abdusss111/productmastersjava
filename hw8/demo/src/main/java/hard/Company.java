package hard;

import lombok.*;
import org.example.Person;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Company {
    private String name;
    private List<Person> employees;
}