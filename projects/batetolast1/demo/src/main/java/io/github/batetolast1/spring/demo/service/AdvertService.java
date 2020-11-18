package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.dto.EditAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowUserDTO;

import java.util.List;

public interface AdvertService {

    void addAdvert(CreateAdvertDTO createAdvertDTO);

    List<ShowAdvertDTO> getHomePageAdverts();

    List<ShowAdvertDTO> getUserAdverts(Long id);

    ShowUserDTO getAdvertsOwnerUsername(Long id);

    List<ShowAdvertDTO> getObservedAdverts();

    EditAdvertDTO getEditedAdvert(Long id);
}
