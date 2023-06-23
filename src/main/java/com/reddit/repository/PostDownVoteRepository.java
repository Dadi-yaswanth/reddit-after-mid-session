package com.reddit.repository;

import com.reddit.entity.CommentDownVote;
import com.reddit.entity.PostDownVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostDownVoteRepository extends JpaRepository<PostDownVote, Long> {

    @Query("select case when (count(p) > 0) then true else false end " +
            "from PostDownVote p where " +
            "(p.userId = :userId) AND (p.postId = :postId) ")
    boolean isDownVoted(@Param("postId") long postId, @Param("userId") long userId);

    @Query("select p from PostDownVote p WHERE " +
            "(p.userId = :userId) AND (p.postId = :postId)")
    Optional<PostDownVote> findByPostIdAndCommentIdAndUserId(@Param("postId") long postId,
                                                                @Param("userId") long userId);
}