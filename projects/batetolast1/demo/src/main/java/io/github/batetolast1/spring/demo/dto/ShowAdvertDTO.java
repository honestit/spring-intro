package io.github.batetolast1.spring.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShowAdvertDTO {

    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String username;
    private String posted;
    private boolean isCreatedByLoggedUser;
    private boolean isObserved;
}
