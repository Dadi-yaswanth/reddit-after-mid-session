package com.reddit.repository;

import com.reddit.entity.CommentDownVote;
import com.reddit.entity.ReplyDownVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyDownVoteRepository extends JpaRepository<ReplyDownVote, Long> {

    @Query("select case when (count(p) > 0) then true else false end " +
            "from ReplyDownVote p where " +
            "(p.userId = :userId) AND (p.replyId = :replyId) AND (p.commentId = :commentId)")
    boolean isDownVoted(@Param("replyId") long replyId, @Param("commentId") long commentId, @Param("userId") long userId);

    @Query("select p from ReplyDownVote p WHERE " +
            "(p.userId = :userId) AND (p.replyId = :replyId) AND (p.commentId = :commentId)")
    Optional<ReplyDownVote> findByPostIdAndCommentIdAndUserId(@Param("replyId") long replyId,
                                                              @Param("commentId") long commentId,
                                                              @Param("userId") long userId);
}