package com.itgura.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

}
