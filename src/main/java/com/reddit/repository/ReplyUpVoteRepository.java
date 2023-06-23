package com.reddit.repository;

import com.reddit.entity.CommentUpvote;
import com.reddit.entity.ReplyUpVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyUpVoteRepository extends JpaRepository<ReplyUpVote, Long> {

    @Query("select case when (count(p) > 0) then true else false end " +
            "from ReplyUpVote p where " +
            "(p.userId = :userId) AND (p.replyId = :replyId) AND (p.commentId = :commentId)")
    boolean isUpVoted(@Param("replyId") long replyId, @Param("commentId") long commentId, @Param("userId") long userId);

    @Query("select p from ReplyUpVote p WHERE " +
            "(p.userId = :userId) AND (p.replyId = :replyId) AND (p.commentId = :commentId)")
    Optional<ReplyUpVote> findByPostIdAndCommentIdAndUserId(@Param("replyId") long replyId,
                                                            @Param("commentId") long commentId,
                                                            @Param("userId") long userId);
}