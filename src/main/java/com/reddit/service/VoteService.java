package com.reddit.service;

import com.reddit.entity.*;
import com.reddit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentUpvoteRepository commentUpvoteRepository;
    private final CommentDownVoteRepository commentDownVoteRepository;
    private final ReplyUpVoteRepository replyUpVoteRepository;
    private final ReplyDownVoteRepository replyDownVoteRepository;
    private final PostUpVoteRepository postUpVoteRepository;
    private final PostDownVoteRepository postDownVoteRepository;
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    public void commentUpVote(Long postId, Long commentId, User user) {
        boolean vote = commentUpvoteRepository.isUpVoted(postId, commentId, user.getUserId());
        if (!vote) {
            Optional<CommentDownVote> downvoteOptional =
                    commentDownVoteRepository.findByPostIdAndCommentIdAndUserId(postId, commentId, user.getUserId());
            if (downvoteOptional.isPresent()) {
                commentDownVoteRepository.delete(downvoteOptional.get());
                decrementCommentDownVote(commentId);
            }

            CommentUpvote upvote = new CommentUpvote();
            upvote.setCommentId(commentId);
            upvote.setPostId(postId);
            upvote.setUserId(user.getUserId());
            commentUpvoteRepository.save(upvote);
            incrementCommentUpVote(commentId);
        }
    }

    public void commentDownVote(Long postId, Long commentId, User user) {
        boolean vote = commentDownVoteRepository.isDownVoted(postId, commentId, user.getUserId());

        if (!vote) {
            Optional<CommentUpvote> upvoteOptional =
                    commentUpvoteRepository.findByPostIdAndCommentIdAndUserId(postId, commentId, user.getUserId());

            if (upvoteOptional.isPresent()) {
                commentUpvoteRepository.delete(upvoteOptional.get());
                decrementCommentUpVote(commentId);
            }

            CommentDownVote downVote = new CommentDownVote();
            downVote.setCommentId(commentId);
            downVote.setPostId(postId);
            downVote.setUserId(user.getUserId());
            commentDownVoteRepository.save(downVote);
            incrementCommentDownVote(commentId);
        }
    }

    public void replyUpVote(Long replyId, Long commentId, User user) {
        boolean vote = replyUpVoteRepository.isUpVoted(replyId, commentId, user.getUserId());
        if (!vote) {
            Optional<ReplyDownVote> downvoteOptional =
                    replyDownVoteRepository.findByPostIdAndCommentIdAndUserId(replyId, commentId, user.getUserId());
            if (downvoteOptional.isPresent()) {
                replyDownVoteRepository.delete(downvoteOptional.get());
                decrementReplyDownVote(commentId);
            }

            ReplyUpVote upvote = new ReplyUpVote();
            upvote.setCommentId(commentId);
            upvote.setReplyId(replyId);
            upvote.setUserId(user.getUserId());
            replyUpVoteRepository.save(upvote);
            incrementReplyUpVote(replyId);
        }
    }

    public void replyDownVote(Long replyId, Long commentId, User user) {
        boolean vote = replyDownVoteRepository.isDownVoted(replyId, commentId, user.getUserId());

        if (!vote) {
            Optional<ReplyUpVote> upvoteOptional =
                    replyUpVoteRepository.findByPostIdAndCommentIdAndUserId(replyId, commentId, user.getUserId());

            if (upvoteOptional.isPresent()) {
                replyUpVoteRepository.delete(upvoteOptional.get());
                decrementReplyUpVote(commentId);
            }

            ReplyDownVote downVote = new ReplyDownVote();
            downVote.setCommentId(commentId);
            downVote.setReplyId(replyId);
            downVote.setUserId(user.getUserId());
            replyDownVoteRepository.save(downVote);
            incrementReplyDownVote(commentId);
        }
    }

    public void postUpVote(Long postId, User user) {
        boolean vote = postUpVoteRepository.isUpVoted(postId, user.getUserId());
        if (!vote) {
            Optional<PostDownVote> downvoteOptional =
                    postDownVoteRepository.findByPostIdAndCommentIdAndUserId(postId, user.getUserId());
            if (downvoteOptional.isPresent()) {
                postDownVoteRepository.delete(downvoteOptional.get());
                decrementPostDownVote(postId);
            }

            PostUpVote upvote = new PostUpVote();
            upvote.setPostId(postId);
            upvote.setUserId(user.getUserId());
            postUpVoteRepository.save(upvote);
            incrementPostUpVote(postId);
            incrementPostOwnerKarma(postId);
            notificationService.saveUpvoteNotification(postId,user);
        }
    }

    public void postDownVote(Long postId, User user) {
        boolean vote = postDownVoteRepository.isDownVoted(postId, user.getUserId());

        if (!vote) {
            Optional<PostUpVote> upvoteOptional =
                    postUpVoteRepository.findByPostIdAndCommentIdAndUserId(postId, user.getUserId());

            if (upvoteOptional.isPresent()) {
                postUpVoteRepository.delete(upvoteOptional.get());
                decrementPostUpVote(postId);
            }

            PostDownVote downVote = new PostDownVote();
            downVote.setPostId(postId);
            downVote.setUserId(user.getUserId());
            postDownVoteRepository.save(downVote);
            incrementPostDownVote(postId);
            decrementPostOwnerKarma(postId);
            notificationService.saveDownVoteNotification(postId,user);
        }
    }

    public void incrementCommentUpVote(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setUpVotes(comment.getUpVotes() + 1);
        commentRepository.save(comment);
    }

    public void decrementCommentUpVote(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setUpVotes((comment.getUpVotes() - 1));
        commentRepository.save(comment);
    }

    public void incrementCommentDownVote(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setDownVotes(comment.getDownVotes() + 1);
        commentRepository.save(comment);
    }

    public void decrementCommentDownVote(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setDownVotes((comment.getDownVotes() - 1));
        commentRepository.save(comment);
    }


    public void incrementReplyUpVote(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        reply.setUpVotes(reply.getUpVotes() + 1);
        replyRepository.save(reply);
    }

    public void decrementReplyUpVote(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        reply.setUpVotes((reply.getUpVotes() - 1));
        replyRepository.save(reply);
    }

    public void incrementReplyDownVote(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        reply.setDownVotes(reply.getDownVotes() + 1);
        replyRepository.save(reply);
    }

    public void decrementReplyDownVote(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        reply.setDownVotes((reply.getDownVotes() - 1));
        replyRepository.save(reply);
    }

    public void incrementPostUpVote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setUpVotes(post.getUpVotes() + 1);
        postRepository.save(post);
    }

    public void decrementPostUpVote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setUpVotes((post.getUpVotes() - 1));
        postRepository.save(post);
    }

    public void incrementPostDownVote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setDownVotes(post.getDownVotes() + 1);
        postRepository.save(post);
    }

    public void decrementPostDownVote(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setDownVotes((post.getDownVotes() - 1));
        postRepository.save(post);
    }

    public void incrementPostOwnerKarma(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUserId(post.getUser().getUserId());
        user.setKarma(user.getKarma() + 1);
        userRepository.save(user);
    }

    public void decrementPostOwnerKarma(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUserId(post.getUser().getUserId());
        user.setKarma(user.getKarma() - 1);
        userRepository.save(user);
    }
}
