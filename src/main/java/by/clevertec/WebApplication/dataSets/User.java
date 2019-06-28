package by.clevertec.WebApplication.dataSets;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({"id", "name", "email", "password", "age"})
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private int age;

    public User() {
    }

    public User(String id, String name, String email, String password, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
