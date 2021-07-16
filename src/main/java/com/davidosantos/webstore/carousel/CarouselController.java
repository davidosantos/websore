
package com.davidosantos.webstore.carousel;

import java.io.IOException;
import java.util.List;

import com.davidosantos.webstore.images.Image;
import com.davidosantos.webstore.images.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller()
@RequestMapping("/backoffice/carousel")
class CarouselController {

    @Autowired
    CarouselService carouselService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "")
    public String firstPage(Model model) {

        return "redirect:backoffice/carousel/edit";
    }

    @RequestMapping("/edit")
    public String corouselStart(@RequestParam(defaultValue = "") String id, Model model) {

        List<Carousel> allCarousel = carouselService.getDefault();

        Carousel carousel;
        if (id.equals("")) {
            carousel = new Carousel();
        } else {
            carousel = carouselService.getById(id);
        }

        Carousel newCarousel = new Carousel();
        newCarousel.setIsActive(true);
        model.addAttribute("allCarousel", allCarousel);
        model.addAttribute("carousel", carousel);
        model.addAttribute("newCarousel", newCarousel);

        return "backoffice/carousel";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String corouselSave(Carousel carousel, Model model) {
        if (carousel.getId() != null && carousel.getId().equals("")) {
            carousel.setId(null);
        }
        Carousel carouselSaved = carouselService.save(carousel);

        return "redirect:/backoffice/carousel/edit?id=" + carouselSaved.getId();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String corouselSave(String id, Model model) {

        carouselService.deactivateById(id);

        return "redirect:/backoffice/carousel/edit?id=" + id;
    }

    @PostMapping("/images")
    public String uploadImage(@RequestParam("id") String id, @RequestParam("title") String title,
            @RequestParam("redirectTo") String redirectTo, @RequestParam("image") MultipartFile image)
            throws IOException {
        Image uploadedImage = imageService.uploadImage(title, image);
                carouselService.saveImage(id, uploadedImage.getId());
        return "redirect:" + redirectTo;
    }

}
