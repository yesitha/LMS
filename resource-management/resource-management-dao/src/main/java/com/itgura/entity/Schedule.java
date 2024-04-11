package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedule", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id")
    private UUID scheduleId;
    @Column(name = "schedule_name")
    private String scheduleName;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;
    @OneToMany(mappedBy = "schedule",fetch = FetchType.EAGER)
    private List<ScheduleSession> scheduleSessionList;


}
