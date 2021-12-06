package ru.pcs.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 23.11.2021 in project task-tracker
 */
@Table(name = "tasks")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    private String shortDescription;
    @Column(length = 1024)
    private String fullDescription;
    @ManyToOne
    @JoinColumn(name = "author_email")
    private User author;
    @ManyToOne
    @JoinColumn(name = "assignee_email")
    private User assignee;
    @CreationTimestamp
    private Instant createdAt;
    private Instant finishedAt;
    private BigDecimal hours;
}
