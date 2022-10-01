package ru.crspet.recipes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "usr")

@Getter
@Setter
@NoArgsConstructor
public class User {


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (! (obj instanceof User)) { return false; }
        User user = (User) obj;
        return user.id == id && user.password.equals(password) && user.email.equals(email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email);
    }

}
