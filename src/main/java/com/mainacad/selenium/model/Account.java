package com.mainacad.selenium.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
}
