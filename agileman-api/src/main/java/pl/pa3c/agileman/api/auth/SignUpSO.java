package pl.pa3c.agileman.api.auth;

import java.util.Set;


import lombok.Data;

@Data
public class SignUpSO {
    private String login;
    private String email;
    private Set<String> role;
    private String password;
}
