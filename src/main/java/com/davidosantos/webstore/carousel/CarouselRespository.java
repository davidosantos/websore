package com.davidosantos.webstore.carousel;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarouselRespository extends MongoRepository<Carousel, String> {
    List<Carousel> findByIsActive(boolean isActive);
}
