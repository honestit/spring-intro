package io.github.batetolast1.spring.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
