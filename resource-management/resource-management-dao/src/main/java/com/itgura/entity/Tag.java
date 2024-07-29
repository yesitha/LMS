package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "tag", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "forum_question_id")
public class Tag {

    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tagId;

    private String tagName;

    @OneToMany(mappedBy = "tag",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ContentTag> contentTagList;

}
