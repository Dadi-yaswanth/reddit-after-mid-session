package com.reddit.controller;


import com.reddit.dto.CommentDto;
import com.reddit.service.CommentService;
import com.reddit.service.FileService;
import com.reddit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("{accountType}/{username}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final FileService fileService;
    private final PostService postsService;
    private final CommentService commentService;

    // r/{communityName}/comments/{postId}/add
    @PostMapping("/{postId}/add")
    public String addComment(@PathVariable Long postId,
                             @ModelAttribute CommentDto commentDto,
                             Model model, @PathVariable String accountType,
                             @PathVariable String username, Principal principal) {
        commentService.addComment(postId, commentDto,principal);
        return "redirect:/"+accountType+'/'+username+"/comments/"+postId;
    }

    @PostMapping("/{postId}/reply/{commentId}")
    public String addReply(@PathVariable("commentId") Long commentId,
                           @ModelAttribute CommentDto commentDto,
                           @PathVariable("postId") Long postId, Model model, @PathVariable String accountType, @PathVariable String username,Principal principal) {

        commentService.addReply(commentId, commentDto,principal);
        return "redirect:/"+accountType+'/'+username+"/comments/"+postId;
    }
}
