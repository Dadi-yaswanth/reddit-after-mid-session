package com.reddit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
    @Table(name = "reply_tbl")
    @Getter
    @Setter
    @NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    private String content;

    @Column(name = "up_votes")
    private Long upVotes;

    @Column(name = "down_votes")
    private Long downVotes;

    @CreationTimestamp
    @Column(name="replied_at")
    private Timestamp repliedAt;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

}

