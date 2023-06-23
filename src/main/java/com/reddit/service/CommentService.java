package com.reddit.service;


import com.reddit.dto.CommentDto;
import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.Reply;
import com.reddit.entity.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void addComment(Long postId, CommentDto commentDto, Principal principal){
        //find user using authorize later
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow(); //have to replace it with authorization user
        Post post = postRepository.findById(postId).orElseThrow();

        List<Comment> postComments = post.getComments();
        Comment comment = new Comment();
        comment.setContent(commentDto.getComment());
        comment.setUser(user);
        comment.setUpVotes(0L);
        comment.setDownVotes(0L);

        postComments.add(comment);
        post.setComments(postComments);
        postRepository.save(post);
        System.out.println("yoo");
    }

    public void addReply(Long commentId,CommentDto commentDto, Principal principal){
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        System.out.println(comment.getCommentId());
        List<Reply> commentReplies = comment.getReplyComments();

        Reply reply = new Reply();
        reply.setContent(commentDto.getComment());
        reply.setUser(user);
        reply.setUpVotes(0L);
        reply.setDownVotes(0L);
        commentReplies.add(reply);
        comment.setReplyComments(commentReplies);
        commentRepository.save(comment);
    }
}
