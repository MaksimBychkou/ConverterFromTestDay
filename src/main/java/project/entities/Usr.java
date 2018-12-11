package project.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
@Data
@Entity
@Table(name = "users")
public class Usr{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "user_name")
    private String userName;
    @NotNull
    @JoinColumn(name = "login")
    @Column(name = "login", unique = true)
    private String login;
    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    @Column(name = "active")
    private boolean active;
}
