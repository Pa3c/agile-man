package pl.pa3c.agileman.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class User {

    @Id
    private String login;
    private String password;
    private String name;
    private String surname;
    private Integer version;

}
