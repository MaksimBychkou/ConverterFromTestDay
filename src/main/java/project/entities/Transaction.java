
package project.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor*/
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Date date;
    private String operation;
    private Double usd;
    private Double eth;
    private String status;
    private Double balanceUsd;
    private Double balanceEth;
    //private String userName;
    //@ManyToOne(targetEntity = Usr.class, fetch = FetchType.LAZY)
    @Column(name="login")
    private String login;
    @Column(name = "role")
    private String role;

}

