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
    private Long ownerId;
    private String ownerUsername;
    private String posted;
    private String categoryName;
    private boolean isCreatedByLoggedUser;
    private boolean isObserved;
}
