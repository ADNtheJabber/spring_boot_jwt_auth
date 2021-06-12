package sn.adn.authtest.domaine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname", length = 70, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 70, nullable = false)
    private String lastName;

    @Column(name = "login", length = 100, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "role")
    private String role;
}
