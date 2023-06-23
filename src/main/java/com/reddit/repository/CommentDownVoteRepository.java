package com.reddit.repository;

import com.reddit.entity.CommentDownVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentDownVoteRepository extends JpaRepository<CommentDownVote,Long> {

    @Query("select case when (count(p) > 0) then true else false end " +
            "from CommentDownVote p where " +
            "(p.userId = :userId) AND (p.postId = :postId) AND (p.commentId = :commentId)" )
    boolean isDownVoted(@Param("postId") long postId , @Param("commentId") long commentId, @Param("userId") long userId);

    @Query("select p from CommentDownVote p WHERE " +
            "(p.userId = :userId) AND (p.postId = :postId) AND (p.commentId = :commentId)" )
    Optional<CommentDownVote> findByPostIdAndCommentIdAndUserId(@Param("postId") long postId ,
                                                                @Param("commentId") long commentId,
                                                                @Param("userId") long userId);
}