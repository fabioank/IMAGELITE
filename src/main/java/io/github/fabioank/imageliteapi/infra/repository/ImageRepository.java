package io.github.fabioank.imageliteapi.infra.repository;

import io.github.fabioank.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}