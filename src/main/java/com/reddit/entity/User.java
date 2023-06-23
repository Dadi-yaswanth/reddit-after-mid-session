package com.reddit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name="gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "karma_value")
    private Long karma;

    @Column(name = "roles")
    private String roles;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "ownerId",cascade = CascadeType.ALL)
    private Set<Community> ownedCommunities;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Post> userPosts;

    @ManyToMany(mappedBy = "communityMembers")
    private Set<Community> communityMembers;

    @ManyToMany(mappedBy = "communityModerators")
    private Set<Community> communityModerators;

    @OneToMany(mappedBy = "user")
    private Set<Vote> userVotes;

    @OneToMany(mappedBy = "user")
    private List<Comment> userComments;

    @OneToMany(mappedBy = "user")
    private List<Reply> userReplies;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfilePhoto profilePhoto;

    @OneToMany(mappedBy = "user")
    private Set<Draft> userDrafts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

}