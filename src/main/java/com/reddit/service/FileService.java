package com.reddit.service;

import com.reddit.entity.Media;
import com.reddit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;
    public List<Media> uploadImage(String path, List<MultipartFile> images) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        for(MultipartFile image: images) {
            Media media = new Media();
            String name = image.getOriginalFilename();
            if(name!=null && name.contains(".")) {
                String randomId = UUID.randomUUID().toString();
                String fileName = randomId.concat(name.substring(name.lastIndexOf('.')));
                String filePath = path + File.separator + fileName;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                if (image.getContentType().equals("video/mp4")) {
                    media.setIsVideo(true);
                } else {
                    media.setIsVideo(false);
                }
                Files.copy(image.getInputStream(),Paths.get(filePath));
                media.setPathUrl('/'+filePath);
                mediaList.add(fileRepository.save(media));
            }
        }
        return mediaList;
    }


    public Resource load(String mediaPath,String filename) {
        String filePath = mediaPath+File.separator+filename;
        Path path = Paths.get(filePath);
        try{
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw  new RuntimeException("could not read the file");
            }
        }catch (MalformedURLException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}