package com.davidosantos.webstore.carousel;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarouselService {

    @Autowired
    CarouselRespository carouselRespository;

    public List<Carousel> getDefault(){
        
        return carouselRespository.findByIsActive(true);
    }
    
    public List<Carousel> getAllCarousel(){
        
        return carouselRespository.findAll();
    }

    public Carousel save(Carousel carousel){

        return carouselRespository.save(carousel);
    }
     public Carousel getById(String id){

        return carouselRespository.findById(id).get();
    }  
    
    public Carousel deactivateById(String id){
        Carousel carousel = carouselRespository.findById(id).get();
        carousel.setIsActive(false);
        return carouselRespository.save(carousel);
    }

    public Carousel saveImage(String id, String imageId){
        Carousel carousel = carouselRespository.findById(id).get();
        carousel.setImageId(imageId);
        carouselRespository.save(carousel);
        return carousel;
    }
    
}
