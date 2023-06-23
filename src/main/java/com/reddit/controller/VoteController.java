package com.reddit.controller;

import com.reddit.entity.User;
import com.reddit.repository.UserRepository;
import com.reddit.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final UserRepository userRepository;

    @PostMapping("/{accountType}/{username}/{postId}/upvote")
    public String postUpvote(@PathVariable("accountType") String accountType,
                             @PathVariable("username") String username,
                             @PathVariable("postId") Long postId,
                             Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.postUpVote(postId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }

    @PostMapping("/{accountType}/{username}/{postId}/down-vote")
    public String postDownVote(@PathVariable("accountType") String accountType,
                               @PathVariable("username") String username,
                               @PathVariable("postId") Long postId,
                               Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.postDownVote(postId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }

    @PostMapping("/{accountType}/{username}/{postId}/comment/{commentId}/upvote")
    public String commentUpvote(@PathVariable("accountType") String accountType,
                                @PathVariable("username") String username,
                                @PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId,
                                Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.commentUpVote(postId, commentId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }

    @PostMapping("/{accountType}/{username}/{postId}/comment/{commentId}/down-vote")
    public String commentDownVote(@PathVariable("accountType") String accountType,
                                  @PathVariable("username") String username,
                                  @PathVariable("postId") Long postId,
                                  @PathVariable("commentId") Long commentId,
                                  Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.commentDownVote(postId, commentId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }

    @PostMapping("/{accountType}/{username}/{postId}/comment/{commentId}/upvote-reply/{replyId}")
    public String replyUpvote(@PathVariable("accountType") String accountType,
                              @PathVariable("username") String username,
                              @PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              @PathVariable("replyId") Long replyId,
                              Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.replyUpVote(replyId, commentId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }

    @PostMapping("/{accountType}/{username}/{postId}/comment/{commentId}/down-vote-reply/{replyId}")
    public String replyDownVote(@PathVariable("accountType") String accountType,
                                @PathVariable("username") String username,
                                @PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId,
                                @PathVariable("replyId") Long replyId, Principal principal) {
        User user = userRepository.findByUsernameIgnoreCase(principal.getName()).orElseThrow();
        voteService.replyDownVote(replyId, commentId, user);
        return "redirect:/" + accountType + '/' + username + "/comments/" + postId;
    }
}