package com.reddit.service;

import com.reddit.entity.Notification;
import com.reddit.entity.User;
import com.reddit.repository.NotificationRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public void saveUpvoteNotification(Long postId, User user) {
        Notification notification = new Notification();
        Long ownerId = postRepository.findById(postId).get().getUser().getUserId();
        Long userId = user.getUserId();
        List<Notification> userNotifications = new ArrayList<>();
        String username = userRepository.findById(userId).get().getUsername();
        String message = username + " " + "upVoted" + "your post";
        if(userId!=ownerId) {
            notification.setNotificationMessage(message);
            notification.setUser(userRepository.findById(ownerId).get());
            userNotifications.add(notificationRepository.save(notification));
            user.setNotifications(userNotifications);
            userRepository.save(user);
        }
    }

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public void saveDownVoteNotification(Long postId,User user) {
        Notification notification = new Notification();
        Long ownerId = postRepository.findById(postId).get().getUser().getUserId();
        Long userId = user.getUserId();
        List<Notification> userNotifications = new ArrayList<>();
        String username = userRepository.findById(userId).get().getUsername();
        String message = username + " " + "downVoted" + "your post";
        if(userId!=ownerId) {
            notification.setNotificationMessage(message);
            notification.setUser(userRepository.findById(ownerId).get());
            userNotifications.add(notificationRepository.save(notification));
            user.setNotifications(userNotifications);
            userRepository.save(user);
        }
    }

    public void deleteByPostId(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}