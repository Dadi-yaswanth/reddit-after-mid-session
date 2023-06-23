package com.reddit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="draft_tbl")
@Setter
@Getter
@NoArgsConstructor
public class Draft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID draftId;

    private String title;

    private String content;

    private String draftUrl;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}