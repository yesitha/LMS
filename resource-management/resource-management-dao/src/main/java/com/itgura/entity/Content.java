package com.itgura.entity;

import com.itgura.enums.ContentAccessType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "content", schema = "resource_management")
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Content {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "content_id")
    private UUID contentId;
    @Column(name = "state")
    private Integer state;
    @Column(name = "created_by")
    @Lob
    private UUID createdBy;
    @Column(name = "created_on")
    private java.util.Date createdOn;
    @Column(name = "last_modified_by")
    @Lob
    private UUID lastModifiedBy;
    @Column(name = "last_modified_on")
    private java.util.Date lastModifiedOn;
    @Column(name = "price")
    private Double price;
    @Nullable
    @Column(name = "content_access_time_duration")
    private Integer contentAccessTimeDuration;
    @OneToMany(mappedBy = "content",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ContentTag> contentTagList;
    @Enumerated(EnumType.STRING)
    private ContentAccessType contentAccessType;
    @Lob
    @Column(name = "image")
    private Byte[] image;


}
