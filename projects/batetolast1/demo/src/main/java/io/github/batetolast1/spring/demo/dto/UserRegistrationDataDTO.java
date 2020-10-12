package io.github.batetolast1.spring.demo.dto;

import io.github.batetolast1.spring.demo.validation.constraints.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRegistrationDataDTO {

    @NotBlank
    @Size(min = 3, max = 18)
    @UniqueUsername
    private String username;
    @NotBlank
    @Size(min = 8, max = 12)
    private String password;
    private String firstName;
    private String lastName;
}
