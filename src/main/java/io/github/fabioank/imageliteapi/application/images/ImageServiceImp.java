package io.github.fabioank.imageliteapi.application.images;

import io.github.fabioank.imageliteapi.domain.entity.Image;
import io.github.fabioank.imageliteapi.domain.service.ImageService;
import io.github.fabioank.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository repository;
    @Override
    @Transactional
    public Image save(Image image) {
        return repository.save(image);
    }
}
