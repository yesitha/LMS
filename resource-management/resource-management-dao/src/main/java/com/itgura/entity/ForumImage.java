package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "forum_image", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForumImage {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forum_image_id")
    private UUID forumImageId;
    @Column(name= "image")
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "forum_question_id")
    private ForumQuestion forumQuestion;
}
