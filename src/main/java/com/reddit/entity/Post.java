package com.reddit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;


    @Entity
    @Table(name = "post_tbl")
    @Getter
    @Setter
    @NoArgsConstructor
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "post_id")
        private Long postId;

        @Column(name = "title")
        private String title;

        @Column(name = "content")
        private String content;

        @Column(name = "url")
        private String url;

        @Column(name = "is_published")
        private Boolean isPublished;

        @Column(name = "published_at")
        @CreationTimestamp
        private Timestamp publishedAt;

        @Column(name = "Up_votes")
        private Long upVotes;

        @Column(name = "down_votes")
        private Long downVotes;

        @Column(name = "updated_at")
        @UpdateTimestamp
        private Timestamp updatedAt;

        @Column(name = "is_community")
        private Boolean isCommunity;

        @ManyToOne(targetEntity = Community.class)
        @JoinColumn(name ="community_id")
        private Community community;


        @ManyToOne(targetEntity = User.class)
        @JoinColumn(name = "user_id")
        private User user;

        @OneToMany(orphanRemoval = true)
        @JoinColumn(name = "post_id")
        private List<Media> mediaList;

        @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
        @JoinColumn(name = "post_id")
        private List<Comment> comments;

        @OneToMany(orphanRemoval = true)
        @JoinColumn(name = "post_votes")
        private List<Vote> postVotes;
    }