package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.DeleteAdvertDTO;
import io.github.batetolast1.spring.demo.dto.EditAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ObserveAdvertDTO;
import io.github.batetolast1.spring.demo.dto.UnobserveAdvertDTO;

public interface UserService {

    void deleteAdvert(DeleteAdvertDTO deleteAdvertDTO);

    void editAdvert(EditAdvertDTO editAdvertDTO);

    void observeAdvert(ObserveAdvertDTO observeAdvertDTO);

    void unobserveAdvert(UnobserveAdvertDTO unobserveAdvertDTO);
}
