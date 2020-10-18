package pl.pa3c.agileman.api.auth;

import lombok.Data;

@Data
public class SignUpSO {
    private String login;
    private String email;
    private String password;
    private String name;
    private String surname;
}
