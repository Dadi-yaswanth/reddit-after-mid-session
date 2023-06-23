package com.reddit.service;


import com.reddit.entity.ProfilePhoto;
import com.reddit.entity.User;
import com.reddit.repository.ProfilePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfilePhotoService {
    @Autowired
    private ProfilePhotoRepository profilePhotoRepository;
    @Autowired
    private UserService userService;


    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name= file.getOriginalFilename();
        System.out.println("file.getOriginalFilename() ="+file.getOriginalFilename());
        String randomId= UUID.randomUUID().toString();
        System.out.println("UUID.randomUUID().toString() ="+UUID.randomUUID().toString());
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));
        System.out.println("randomId.concat(name.substring(name.lastIndexOf('.'))"+fileName1);
        String filePath=path+ File.separator + fileName1;
        System.out.println("path+ File.separator + fileName1 ="+filePath);
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1;
    }

    public void saveProfilePhoto(String filename, Long userId) {
        User user=userService.getUserByID(userId);
        if(user.getProfilePhoto() !=null){
            user.getProfilePhoto().setFileName(filename);
            profilePhotoRepository.save(user.getProfilePhoto());
        }
        else {
            ProfilePhoto profilePhoto = new ProfilePhoto();
            profilePhoto.setFileName(filename);
            profilePhoto.setUser(user);
            profilePhotoRepository.save(profilePhoto);
            user.setProfilePhoto(profilePhoto);
            userService.saveUser(user);
        }
    }
}