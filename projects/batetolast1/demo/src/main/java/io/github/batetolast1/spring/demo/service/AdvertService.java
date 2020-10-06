package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;

import java.security.Principal;
import java.util.List;

public interface AdvertService {

    void addAdvert(CreateAdvertDTO createAdvertDTO);

    List<ShowAdvertDTO> getAdverts();
}
