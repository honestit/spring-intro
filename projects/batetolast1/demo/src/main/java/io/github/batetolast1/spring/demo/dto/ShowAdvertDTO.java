package io.github.batetolast1.spring.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ShowAdvertDTO {

    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String username;
    private LocalDateTime posted;
    private boolean isCreatedByLoggedUser;
    private boolean isObserved;
}
