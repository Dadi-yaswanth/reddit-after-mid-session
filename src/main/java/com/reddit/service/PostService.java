package com.reddit.service;

import com.reddit.entity.Community;
import com.reddit.entity.Media;
import com.reddit.entity.Post;
import com.reddit.entity.User;
import com.reddit.repository.CommunityRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;
    @Autowired

    UserRepository userRepository;
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    CommunityService communityService;

    @Autowired
    FileService fileService;

    public void savePostUrl(String title, String url, Principal principal, String communityName) {
        String name = splitCommunityName(communityName);
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        Community community = null;
        User user = userService.getUserByID(userId);
        Set<Post> userPosts = new HashSet<>();
        List<Post> communityPosts = new ArrayList<>();
        Post post = new Post();
        post.setTitle(title);
        if (name != null && (!name.trim().equals(","))) {
            post.setIsCommunity(true);
            community = communityService.getByName(name);
            post.setCommunity(community);
        } else {
            post.setIsCommunity(false);
        }
        post.setUrl(url);
        post.setUser(user);
        post.setIsPublished(true);
        post.setUpVotes(0L);
        post.setDownVotes(0L);
        Post savePost = postRepository.save(post);
        userPosts.add(savePost);
        user.setUserPosts(userPosts);
        userRepository.save(user);
        communityPosts.add(savePost);
        if (community != null) {
            community.setCommunityPosts(communityPosts);
            communityRepository.save(community);
        }
    }

    public void savePostContent(String title, String content, Principal principal, String communityName) {
        String name = splitCommunityName(communityName);
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        User user = userService.getUserByID(userId);
        Community community = null;
        Set<Post> userPosts = new HashSet<>();
        List<Post> communityPosts = new ArrayList<>();
        Post post = new Post();
        post.setTitle(title);
        post.setUpVotes(0L);
        post.setDownVotes(0L);
        post.setContent(content);
        post.setUser(user);
        if (name != null && (!name.trim().equals(","))) {
            post.setIsCommunity(true);
            community = communityService.getByName(name);
            post.setCommunity(community);
        } else {
            post.setIsCommunity(false);
        }
        post.setIsPublished(true);
        Post savePost = postRepository.save(post);
        userPosts.add(savePost);
        user.setUserPosts(userPosts);
        userRepository.save(user);
        communityPosts.add(savePost);
        if (community != null) {
            community.setCommunityPosts(communityPosts);
            communityRepository.save(community);
        }
        user.setUserPosts(userPosts);
        userRepository.save(user);
    }

    public void saveMedia(String title, List<Media> savedMediaList, Principal principal, String communityName) {
        String name = splitCommunityName(communityName);
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        User user = userService.getUserByID(userId);
        Community community = null;
        Set<Post> userPosts = new HashSet<>();
        List<Post> communityPosts = new ArrayList<>();
        Post post = new Post();
        post.setMediaList(savedMediaList);
        post.setTitle(title);
        if (name != null && (!name.trim().equals(","))) {
            post.setIsCommunity(true);
            community = communityService.getByName(name);
            post.setCommunity(community);
        } else {
            post.setIsCommunity(false);
        }
        post.setUser(user);
        post.setIsPublished(true);
        post.setUpVotes(0L);
        post.setDownVotes(0L);
        Post savePost = postRepository.save(post);
        userPosts.add(savePost);
        user.setUserPosts(userPosts);
        userRepository.save(user);
        communityPosts.add(savePost);
        if (community != null) {
            community.setCommunityPosts(communityPosts);
            communityRepository.save(community);
        }
        user.setUserPosts(userPosts);
        userRepository.save(user);
    }

    public void savePost(String title, List<Media> savedMediaList, String url, String content, Principal principal, String communityName) {
        String name = splitCommunityName(communityName);
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        User user = userService.getUserByID(userId);
        Community community = null;
        Set<Post> userPosts = new HashSet<>();
        List<Post> communityPosts = new ArrayList<>();
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUrl(url);
        if (name != null && (!name.trim().equals(","))) {
            post.setIsCommunity(true);
            community = communityService.getByName(name);
            post.setCommunity(community);
        } else {
            post.setIsCommunity(false);
        }
        post.setUser(user);
        post.setMediaList(savedMediaList);
        post.setIsPublished(true);
        post.setUpVotes(0L);
        post.setDownVotes(0L);
        Post savePost = postRepository.save(post);
        userPosts.add(savePost);
        user.setUserPosts(userPosts);
        userRepository.save(user);
        communityPosts.add(savePost);
        if (community != null) {
            community.setCommunityPosts(communityPosts);
            communityRepository.save(community);
        }
        user.setUserPosts(userPosts);
        userRepository.save(user);
    }

    public void saveDraftedPost(String title, String content, UUID draftId, String url, Principal principal, String communityName) {
        String name = splitCommunityName(communityName);
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        User user = userService.getUserByID(userId);
        Community community = null;
        Post post = new Post();
        Set<Post> userPosts = new HashSet<>();
        List<Post> communityPosts = new ArrayList<>();
        if ((name != null && name.trim().equals(","))) {
            post.setIsCommunity(true);
            community = communityService.getByName(name);
            System.out.println(community.getCommunityId());
            post.setCommunity(community);
        } else {
            post.setIsCommunity(false);
        }
        post.setTitle(title);
        post.setUrl(url);
        post.setUpVotes(0L);
        post.setDownVotes(0L);
        post.setContent(content);
        post.setIsPublished(true);
        post.setUser(user);
        Post savePost = postRepository.save(post);
        userPosts.add(savePost);
        user.setUserPosts(userPosts);
        userRepository.save(user);
        communityPosts.add(savePost);
        if (community != null) {
            community.setCommunityPosts(communityPosts);
            communityRepository.save(community);
        }

    }


    //yashvant
    public Post getPostByType(String typeOfAccount, String username, Long postId) {
        if (typeOfAccount.equalsIgnoreCase("r")) {
            return postRepository.findPostsByCommunityName(username, postId).orElseThrow();
        } else if (typeOfAccount.equalsIgnoreCase("u")) {
            System.out.println("user");
            return postRepository.findPostsByUsername(username, postId).orElseThrow();
        }
        return null;
    }


    public Post getPost(Long postId) {
        return postRepository.findById(postId).get();
    }


    public String splitCommunityName(String communityNames) {
        if (communityNames != null && !communityNames.isEmpty()) {
            if(communityNames.length()==1){
                return communityNames;
            }else {
                String[] name = communityNames.split(",");
                if (name.length == 1) {
                    return name[0];
                } else {
                    return name[1];
                }
            }
        }
        return null;
    }

    public void post(String title, List<MultipartFile> images, String url, String content, Principal principal, String communityName, String path) throws IOException {
        if ((images != null && (!images.isEmpty())) && (url != null && (!url.isEmpty())) && (content != null
                && (!content.isEmpty()))) {
            List<Media> savedMediaList = fileService.uploadImage(path, images);
            savePost(title, savedMediaList, url, content, principal, communityName);
        } else if (url != null && (!url.isEmpty())) {
            savePostUrl(title, url, principal, communityName);
        } else if (content != null && (!content.isEmpty())) {
            savePostContent(title, content, principal, communityName);
        } else if (images != null && (!images.isEmpty())) {
            List<Media> savedMediaList = fileService.uploadImage(path, images);
            saveMedia(title, savedMediaList, principal, communityName);
        }
    }
}