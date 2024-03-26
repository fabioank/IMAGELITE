package io.github.fabioank.imageliteapi.application.images;

import io.github.fabioank.imageliteapi.domain.entity.Image;
import io.github.fabioank.imageliteapi.domain.enums.ImageExtension;
import io.github.fabioank.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageMapper imageMapper;
    private final ImageService service;
    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags) throws IOException {

        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

        Image image = imageMapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);
        URI imageURI = buildImageURL(savedImage);

        return ResponseEntity.created(imageURI).build();

    }
    @GetMapping({"{id}"})
    public ResponseEntity<byte[]> getImage(@PathVariable String id){

       var possiveImage = service.getById(id);
       if(possiveImage.isEmpty()){
           return ResponseEntity.notFound().build();
       }

       var image = possiveImage.get();
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(image.getExtension().getMediaType());
       headers.setContentLength(image.getSize());
       headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\" \"", image.getFileName());

       return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);

    }

    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }
}
