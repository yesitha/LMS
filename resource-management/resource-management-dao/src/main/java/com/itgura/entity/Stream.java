package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stream", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stream {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "stream_id")
    private UUID streamId;
    @Column(name = "stream")
    private String stream;
    @OneToMany(mappedBy = "stream",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentList;

}
