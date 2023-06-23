package com.reddit.repository;

import com.reddit.entity.CommentUpvote;
import com.reddit.entity.Post;
import com.reddit.entity.PostUpVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostUpVoteRepository extends JpaRepository<PostUpVote,Long> {

    @Query("select case when (count(p) > 0) then true else false end " +
            "from PostUpVote p where " +
            "(p.userId = :userId) AND (p.postId = :postId)" )
    boolean isUpVoted(@Param("postId") long postId , @Param("userId") long userId);

    @Query("select p from PostUpVote p WHERE " +
            "(p.userId = :userId) AND (p.postId = :postId) " )
    Optional<PostUpVote> findByPostIdAndCommentIdAndUserId(@Param("postId") long postId ,
                                                     @Param("userId") long userId);
}