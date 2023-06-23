package com.reddit;

import com.reddit.controller.UserController;
import com.reddit.repository.CommentUpvoteRepository;
import com.reddit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class RedditProjectApplication {
	public static void main(String[] args) {
		new File(UserController.uploadDirectory).mkdir();
		SpringApplication.run(RedditProjectApplication.class, args);
	}
}