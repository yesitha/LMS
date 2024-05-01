package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "recording", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "recording_id")
public class Recording extends Content {

    @Column(name = "recording_name")
    private String recordingName;
    @Column(name = "reference" , columnDefinition = "TEXT" )
    private UUID reference;

    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
