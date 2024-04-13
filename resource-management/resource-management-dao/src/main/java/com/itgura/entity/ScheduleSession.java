package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "schedule_session", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleSession {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_session_id")
    private UUID scheduleSessionId;
    @Column(name = "venue")
    private String venue;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "date_and_time")
    private Date dateAndTime;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
