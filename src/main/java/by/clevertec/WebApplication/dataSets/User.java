package by.clevertec.WebApplication.dataSets;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({"id", "name", "email", "password", "age"})
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String password;
    private int age;

    public User(Integer id, String name, String email, String password, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
