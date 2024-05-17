package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "announcement", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Announcement {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "announcement_id")
    private UUID announcementId;
    @Column(name = "announcement_title")
    private String announcementTitle;
    @Column(name = "announcement_description", columnDefinition = "TEXT")
    private String announcementDescription;
    @Column(name = "announcement_on")
    private Date announcementOn;
    @Column(name = "announcement_by")
    @Lob
    private UUID announcementBy;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private AClass aClass;
}
